package dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("averageCall")
public class AverageCall {
    @Param(0)
    private int value;

    public AverageCall(){}

    public AverageCall(int value)
    {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
