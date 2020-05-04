package dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Id("profiling")
public class Profiling {
    @Param(0)
    private String value;

    public Profiling(){}

    public Profiling(String value)
    {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
