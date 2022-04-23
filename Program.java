import java.util.ArrayList;

public class Program {
    ArrayList<Instruction> instructions;
    private ArrayList<Variable> variables;
    public Program(){
        variables = new ArrayList<Variable>();
        instructions = new ArrayList<Instruction>();
    }
    
    public ArrayList<Variable> getVariables() {
        return variables;
    }
    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }
    
}
