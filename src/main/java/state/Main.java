package state;

import dlv.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Main {

    public static void main(String args[]) {
        String chromeDriverPath = "src/main/resources/chromedriver.exe" ;
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to("https://js-css-poker.sourceforge.io/poker.html");
        driver.findElement(By.xpath("/html[1]/body[1]/div[9]/table[1]/tbody[1]/tr[1]/td[1]/font[1]/a[1]")).click();
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("call-button")));
        driver.findElement(By.id("speed-options")).click();
        driver.findElement(By.xpath("//option[contains(text(),'1')]")).click();

        Budget budget = new Budget(500);
        ProbabilityCalculator probabilityCalculator = new ProbabilityCalculator();
        State phase = new Phase1(driver, budget, probabilityCalculator);

        try {
            while (true) {
                State nextPhase = phase.handle();
                if (nextPhase instanceof Phase0) {
                    State.numRound++;
                }
                phase = nextPhase;
            }
        }
        catch(Exception e)
        {
            //System.out.println("Game over");
        }
        finally {
            probabilityCalculator.closeDriver();
            driver.quit();
        }

    }


}
