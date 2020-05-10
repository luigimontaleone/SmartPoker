package state;

import dlv.Budget;
import dlv.Card;
import dlv.ProbabilityCalculator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Phase3 extends State {
    public Phase3(WebDriver driver, Budget budget, ProbabilityCalculator probabilityCalculator) {
        super(driver, budget, "src/main/resources/IA/TwoPlayers/"+State.profiling.getValue()+"/ia3.txt", probabilityCalculator);
    }

    @Override
    public void handleCards() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("turn")));
        while(generateCard(driver.findElement(By.id("turn")).getCssValue("background-image")).getNumber() == -1);
        Card turnCard = generateCard(driver.findElement(By.id("turn")).getCssValue("background-image"));
        probabilityCalculator.setTurnRiverCard(turnCard, 4);
    }

    @Override
    public State nextState()
    {
        return new Phase4(driver, budget, probabilityCalculator);
    }
}
