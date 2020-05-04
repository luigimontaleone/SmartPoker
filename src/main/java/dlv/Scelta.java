package dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("scelta")
public class Scelta {

    @Param(0)
    private String scelta;

    public Scelta(){}

    public Scelta(String scelta) {
        this.scelta = scelta;
    }

    public String getScelta() {
        return scelta;
    }

    public void setScelta(String scelta) {
        this.scelta = scelta;
    }
}
