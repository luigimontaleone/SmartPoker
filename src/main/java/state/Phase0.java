package state;

import dlv.Budget;
import dlv.ProbabilityCalculator;
import org.openqa.selenium.WebDriver;

public class Phase0 extends State {
    public Phase0(WebDriver driver, Budget budget, ProbabilityCalculator probabilityCalculator) {
        super(driver, budget, "", probabilityCalculator);
    }

    @Override
    public void handleCards() {
    }

    @Override
    public State nextState()
    {
        return new Phase1(driver, budget, probabilityCalculator);
    }
}
