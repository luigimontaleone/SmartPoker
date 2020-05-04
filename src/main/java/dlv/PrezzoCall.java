package dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("prezzoCall")
public class PrezzoCall {

    @Param(0)
    private int value;

    public PrezzoCall(){}

    public PrezzoCall(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

