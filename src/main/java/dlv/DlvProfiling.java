package dlv;

import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;

public class DlvProfiling extends DlvHandler{

    public void setAverage(AverageRaise averageRaise) {
        System.out.println("AVERAGE RAISE "+averageRaise.getValue());
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(averageRaise);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }

    public void setAverage(AverageFold averageFold) {
        System.out.println("AVERAGE FOLD "+averageFold.getValue());
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(averageFold);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }

    public void setAverage(AverageCall averageCall) {
        System.out.println("AVERAGE CALL "+averageCall.getValue());
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(averageCall);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
    }


}
