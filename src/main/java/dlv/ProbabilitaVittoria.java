package dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("probabilitaVittoria")
public class ProbabilitaVittoria {

    @Param(0)
    private int value;

    public ProbabilitaVittoria(){}

    public ProbabilitaVittoria(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
