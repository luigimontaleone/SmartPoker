package dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("averageRaise")
public class AverageRaise {
    @Param(0)
    private int value;

    public AverageRaise(){}

    public AverageRaise(int value)
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
