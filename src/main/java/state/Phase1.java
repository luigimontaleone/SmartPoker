package state;

import dlv.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Pattern;

public class Phase1 extends State {

    public Phase1(WebDriver driver, Budget budget, ProbabilityCalculator probabilityCalculator) {
        super(driver, budget, "src/main/resources/IA/TwoPlayers/ia1.txt", probabilityCalculator);
    }


    @Override
    public void handleCards() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='seat0']//div[@class='card holecard2']")));
        while(generateCard(driver.findElement(By.xpath("//div[@id='seat0']//div[@class='card holecard2']")).getCssValue("background-image")).getNumber() == -1);
        Card card1 = generateCard(driver.findElement(By.xpath("//div[@id='seat0']//div[@class='card holecard1']")).getCssValue("background-image"));
        Card card2 = generateCard(driver.findElement(By.xpath("//div[@id='seat0']//div[@class='card holecard2']")).getCssValue("background-image"));
        probabilityCalculator.setPlayerCards(card1, card2);
    }

    @Override
    public State nextState()
    {
        return new Phase2(driver, budget, probabilityCalculator);
    }

}
