package dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("avversarioRaise")
public class SceltaAvversario {

    @Param(0)
    private int raise;

    public SceltaAvversario() {
    }

    public SceltaAvversario(int raise) {
        this.raise = raise;
    }

    public int getRaise() {
        return raise;
    }

    public void setRaise(int raise) {
        this.raise = raise;
    }
}
