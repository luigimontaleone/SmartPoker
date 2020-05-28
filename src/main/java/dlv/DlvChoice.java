package dlv;

import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;

public class DlvChoice extends DlvHandler {

    public void setBudget(Budget budget) {
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(budget);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }

    public void setProbabilitaVittoria(ProbabilitaVittoria probabilita) {
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(probabilita);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }

    public void setPrezzoCall(PrezzoCall prezzoCall) {
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(prezzoCall);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }

    public void setSceltaAvversario(SceltaAvversario scelta) {
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(scelta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }

    public void setProfiling(Profiling profiling) {
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(profiling);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }
}
