package state;

import dlv.Budget;
import dlv.Card;
import dlv.ProbabilityCalculator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Phase2 extends State {
    public Phase2(WebDriver driver, Budget budget, ProbabilityCalculator probabilityCalculator) {
        super(driver, budget, "src/main/resources/IA/TwoPlayers/ia2.txt", probabilityCalculator);
    }

    @Override
    public void handleCards() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("flop3")));
        while(generateCard(driver.findElement(By.id("flop3")).getCssValue("background-image")).getNumber() == -1);
        Card flop1 = generateCard(driver.findElement(By.id("flop1")).getCssValue("background-image"));
        Card flop2 = generateCard(driver.findElement(By.id("flop2")).getCssValue("background-image"));
        Card flop3 = generateCard(driver.findElement(By.id("flop3")).getCssValue("background-image"));
        probabilityCalculator.setFlopCards(flop1,flop2,flop3);
    }

    @Override
    public State nextState()
    {
        return new Phase3(driver, budget, probabilityCalculator);
    }
}
