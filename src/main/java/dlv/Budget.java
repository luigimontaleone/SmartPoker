package dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Id("budget")
public class Budget {
    @Param(0)
    private int value;

    public Budget(){}

    public Budget(int value)
    {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void updateBudget(WebDriver driver) {
        String budget = driver.findElement(By.xpath("//div[@id='seat0']//div[@class='chips']")).getText();
        budget = budget.substring(1,budget.length());
        try {
            setValue(Integer.valueOf(budget));
        }catch (NumberFormatException e)
        {
            setValue(0); //vuol dire che abbiamo fatto all-in
        }
    }
}
