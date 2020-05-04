package state;

import dlv.Budget;
import dlv.Card;
import dlv.ProbabilityCalculator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Phase4 extends State {
    public Phase4(WebDriver driver, Budget budget, ProbabilityCalculator probabilityCalculator) {
        super(driver, budget, "src/main/resources/IA/TwoPlayers/ia4.txt", probabilityCalculator);
    }

    @Override
    public void handleCards() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("river")));
        while(generateCard(driver.findElement(By.id("river")).getCssValue("background-image")).getNumber() == -1);
        Card riverCard = generateCard(driver.findElement(By.id("river")).getCssValue("background-image"));
        probabilityCalculator.setTurnRiverCard(riverCard, 5);
    }

    @Override
    public State nextState()
    {
        resetTable();
        return new Phase0(driver, budget, probabilityCalculator);
    }
}
