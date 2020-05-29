package dlv;

import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;

public class DlvProfiling extends DlvHandler{

    public void setAverage(AverageRaise averageRaise) {
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(averageRaise);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }

    public void setAverage(AverageFold averageFold) {
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(averageFold);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }

    public void setAverage(AverageCall averageCall) {
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(averageCall);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }


}
