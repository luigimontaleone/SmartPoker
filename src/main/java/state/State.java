package state;

import dlv.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class State {

    protected DlvChoice dlv;
    protected DlvProfiling dlvProfiling;
    protected ProbabilityCalculator probabilityCalculator;
    protected WebDriver driver;
    protected Budget budget;
    protected String pathIA;
    protected String sceltaAvversario;
    protected static HashMap<String, Double> behaviour;
    protected static int numRound = 0;
    protected static int numPhases = 0;
    protected static Profiling profiling = new Profiling("unknown");

    static{
        behaviour = new HashMap<>();
        behaviour.put("raise", 0.0);
        behaviour.put("fold", 0.0);
        behaviour.put("call", 0.0);
    }

    public State(WebDriver driver, Budget budget, String pathIA, ProbabilityCalculator probabilityCalculator)
    {
        this.pathIA = pathIA;
        this.budget = budget;
        this.driver = driver;
        dlv = new DlvChoice();
        dlvProfiling = new DlvProfiling();
        this.probabilityCalculator = probabilityCalculator;
        sceltaAvversario = "";
    }

    //template method
    public State handle(){
        if(this.getClass() == Phase0.class)
        {
            while(!driver.findElement(By.id("call-button")).getText().equals("Go on"));
            driver.findElement(By.id("call-button")).click();
            return nextState();
        }
        else{
            new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.id("button")));
            String dealer = driver.findElement(By.id("button")).getAttribute("class");

            handleCards();

            if(commonAlgorithm(dealer))
                return nextState();
            return new Phase0(driver, budget, probabilityCalculator);
        }
    }

    //primitive method
    protected abstract State nextState();

    //primitive method
    protected abstract void handleCards();

    protected boolean commonAlgorithm(String dealer)
    {
        Integer prezzoCall;
        boolean buioPagato = false;

        if(numRound % 2 == 0 && numRound > 0) //facciamo il profiling solo delle ultime 5 mani
        {
            numPhases = (int) (behaviour.get("raise") + behaviour.get("fold") + behaviour.get("call"));
            AverageRaise averageRaise = new AverageRaise((int)Math.round((behaviour.get("raise") / numPhases) * 100)) ;
            AverageCall averageCall = new AverageCall((int)Math.round((behaviour.get("call") / numPhases) * 100)) ;
            AverageFold averageFold = new AverageFold((int)Math.round((behaviour.get("fold") / numPhases) * 100)) ;
            dlvProfiling.setProgram("src/main/resources/IA/TwoPlayers/profiling.txt");
            dlvProfiling.setAverage(averageRaise);
            dlvProfiling.setAverage(averageCall);
            dlvProfiling.setAverage(averageFold);
            String sceltaProfiling = dlvProfiling.runProgram();
            profiling.setValue(sceltaProfiling);

            behaviour.put("raise", 0.0);
            behaviour.put("fold", 0.0);
            behaviour.put("call", 0.0);
            numPhases = 0;
            numRound = 0;
        }

        do
        {

            boolean raisePrecedente = false;
            if(sceltaAvversario.equals("raise"))
                raisePrecedente = true;
            sceltaAvversario = "";
            dlv.setProgram(pathIA);

            if(dealer.equals("seat0-button"))
            {
                if(handleFold())
                    return false;
            }
            else
            {
                if(raisePrecedente)
                    dlv.setSceltaAvversario(new SceltaAvversario(1));
                else
                    dlv.setSceltaAvversario(new SceltaAvversario(0));
            }

            //budget
            budget.updateBudget(driver);
            dlv.setBudget(budget);

            //probabilità vittoria
            dlv.setProbabilitaVittoria(new ProbabilitaVittoria(probabilityCalculator.getWinningProbability()));

            //prezzo call
            waitForPrezzoCall(buioPagato);
            prezzoCall = getPrezzoCall();
            if(prezzoCall == null)
                break;
            if(prezzoCall == 25)
                buioPagato = true;
            dlv.setPrezzoCall(new PrezzoCall(prezzoCall));

            //send profiling to dlv
            dlv.setProfiling(profiling);

            //execute dlv
            String risultato = dlv.runProgram();

            if(risultato.equals("raise"))
            {
                new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[6]/table[1]/tbody[1]/tr[1]/td[1]/a[1]")));
                driver.findElement(By.xpath("/html[1]/body[1]/div[6]/table[1]/tbody[1]/tr[1]/td[1]/a[1]")).click();
            }
            else
            {
                new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.id(risultato+"-button")));
                driver.findElement(By.id(risultato+"-button")).click();
            }
            if(!checkAllIn())
                return false;
            if(risultato.equals("fold"))
            {
                resetTable();
                return false;
            }

            try {
                new WebDriverWait(driver, 3).until(ExpectedConditions.textMatches(By.xpath("/html[1]/body[1]/div[7]/font[1]/b[1]"), Pattern.compile("Dealing.*")));
            }catch(Exception e)
            {
                if(!dealer.equals("seat0-button"))
                {
                    if(handleFold())
                        return false;
                }
            }
        }while(sceltaAvversario.equals("raise"));

        return true;
    }

    protected boolean handleFold() {
        sceltaAvversario = checkSceltaAvversario();
        if(sceltaAvversario.equals("fold"))
        {
            resetTable();
            driver.findElement(By.id("call-button")).click();
            return true;
        }
        return false;
    }

    protected Card generateCard(String match) {
        Pattern pattern = Pattern.compile(".*images\\/(.*)_of_(.*)\\..*");
        Matcher matcher = pattern.matcher(match);
        int number = -1;
        String seed = "";
        if (matcher.find()) {
            if (matcher.group(1).equals("jack"))
                number = 11;
            else if (matcher.group(1).equals("queen"))
                number = 12;
            else if (matcher.group(1).equals("king"))
                number = 13;
            else if (matcher.group(1).equals("ace"))
                number = 1;
            else
                number = Integer.valueOf(matcher.group(1));
            seed = matcher.group(2);
        }
        Card card = new Card(number, seed);
        return card;
    }

    protected boolean checkAllIn()
    {
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/div[3]")));
        String s = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]")).getText();
        //check se abbiamo fatto all-in
        boolean v = Pattern.matches("ALL.*", s);
        if(Pattern.matches("ALL.*", s)){
            return false;
        }
        return true;
    }
    protected String checkSceltaAvversario()
    {
        Integer dump = getPrezzoCall();
        if(dump == null)
            return "";
        Integer prezzoCall = getPrezzoCall();
        String text = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[3]")).getText();
        if(Pattern.matches("FOLDED.*", text)) {
            behaviour.put("fold", behaviour.get("fold") + 1);
            return "fold";
        }
        else if(Pattern.matches("ALL.*", text)){
            behaviour.put("raise", behaviour.get("raise") + 1);
            //ritorniamo fold perchè deve tornare alla fase 0, falso positivo
            return "fold";
        }
        else if(prezzoCall > 25)
        {
            dlv.setSceltaAvversario(new SceltaAvversario(1));
            behaviour.put("raise", behaviour.get("raise") + 1);
            return "raise";
        }
        dlv.setSceltaAvversario(new SceltaAvversario(0));
        behaviour.put("call", behaviour.get("call") + 1);
        return "";
    }

    protected Integer getPrezzoCall()
    {
        Integer value = null;
        while(value == null)
        {
            try
            {
                value = Integer.valueOf(driver.findElement(By.xpath("/html[1]/body[1]/div[7]/font[1]/font[1]")).getText());
            }catch(Exception e){
                if(driver.findElement(By.id("call-button")).getText().equals("Go on"))
                    return null;
            }
        }

        return value;
    }

    protected void waitForPrezzoCall(boolean buioPagato)
    {
        while(true)
        {
            try{
                Integer prezzo = getPrezzoCall();
                if(!buioPagato || prezzo == null || prezzo != 25)
                    return;
            }catch (Exception e){}
        }
    }

    protected void resetTable()
    {
        boolean resetOk = true;
        while(resetOk)
        {
            try {
                probabilityCalculator.resetTable();
                resetOk = false;
            }catch (ElementClickInterceptedException e){ resetOk = true;}
        }
    }

}
