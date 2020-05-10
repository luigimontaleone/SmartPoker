package dlv;

import dlv.Card;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProbabilityCalculator {
    private String chromeDriverPath;
    private String site;
    private WebDriver driver;

    public ProbabilityCalculator()
    {
        chromeDriverPath = "src/main/resources/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--headless", "--disable-infobars", "--disable-extensions");
        driver = new ChromeDriver(options);
        site = "https://en.cardmates.net/online_poker_calculator";
        driver.navigate().to(site);
    }

    public void setPlayerCards(Card card1, Card card2) {
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.xpath("//body/div[@id='cmwrapper']/div[@id='pokerschool_container']/div[@class='main-container']/div[@class='field-item']/div[@id='poker-calculator-wrapper']/section[@id='table-view']/div[@id='player-listing']/div[1]/div[3]/div[1]/div[1]")));
        clickAndCheckPopup(By.xpath("//body/div[@id='cmwrapper']/div[@id='pokerschool_container']/div[@class='main-container']/div[@class='field-item']/div[@id='poker-calculator-wrapper']/section[@id='table-view']/div[@id='player-listing']/div[1]/div[3]/div[1]/div[1]"));
        putCard(card1);
        putCard(card2);
        clickAndCheckPopup(By.id("card-selection-complete"));
    }

    public void setFlopCards(Card card1, Card card2, Card card3)
    {
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='field-item']//li[1]")));
        clickAndCheckPopup(By.xpath("//div[@class='field-item']//li[1]"));
        putCard(card1);
        putCard(card2);
        putCard(card3);
        clickAndCheckPopup(By.id("card-selection-complete"));
    }

    public void setTurnRiverCard(Card card, int indexLi) {
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='field-item']//li["+indexLi+"]")));
        clickAndCheckPopup(By.xpath("//div[@class='field-item']//li["+indexLi+"]"));
        putCard(card);
        clickAndCheckPopup(By.id("card-selection-complete"));
    }

    private void putCard(Card card)
    {
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[3]/div[2]/div[1]/section[2]/div[2]/div[@class='card-selection-table']/div[@data-table-suit='"+card.getSeed()+"']/a[@rel='"+(card.getNumber()-1)+"']")));
        clickAndCheckPopup(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[3]/div[2]/div[1]/section[2]/div[2]/div[@class='card-selection-table']/div[@data-table-suit='"+card.getSeed()+"']/a[@rel='"+(card.getNumber()-1)+"']"));
    }

    public int getWinningProbability()
    {
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@id='player-win-0']")));
        boolean exit = false;
        while (!exit) {
            try {
                return Math.round(Float.valueOf(driver.findElement(By.className("stat")).getAttribute("data-win-perc")));
            } catch (Exception e) {
                closePopup();
                exit = false;
            }
        }
        return 0;
    }


    public void resetTable() throws ElementClickInterceptedException {
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.id("reset-poker-table-desktop")));
        clickAndCheckPopup(By.id("reset-poker-table-desktop"));
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(By.id("reset-confirm-btn")));
        clickAndCheckPopup(By.id("reset-confirm-btn"));
    }

    private void clickAndCheckPopup(By by)
    {
        boolean exit = false;
        while (!exit) {
            try {
                driver.findElement(by).click();
                exit = true;
            } catch (Exception e) {
                closePopup();
                exit = false;
            }
        }
    }

    private void closePopup()
    {
        try {
            driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[6]/div[1]/div[1]/div[1]/button[1]/div[1]")).click();
        }catch (Exception e){}
    }

    public void closeDriver()
    {
        driver.quit();
    }
}