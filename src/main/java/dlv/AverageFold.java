package dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("averageFold")
public class AverageFold {
    @Param(0)
    private int value;

    public AverageFold(){}

    public AverageFold(int value)
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
