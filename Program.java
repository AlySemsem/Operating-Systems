import java.util.ArrayList;

public class Program {
    ArrayList<Instruction> instructions;
    private ArrayList<Variable> variables;
    private Boolean input = false;
    private Boolean output = false;
    private Boolean file = false;
    private int timeAdded;
    public Program(){
        variables = new ArrayList<Variable>();
        instructions = new ArrayList<Instruction>();
    }


    public int getTimeAdded() {
        return timeAdded;
    }
    public void setTimeAdded(int timeAdded) {
        this.timeAdded = timeAdded;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }
    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public Boolean getInput() {
        return input;
    }

    public void setInput(Boolean input) {
        this.input = input;
    }

    public Boolean getOutput() {
        return output;
    }

    public void setOutput(Boolean output) {
        this.output = output;
    }

    public Boolean getFile() {
        return file;
    }

    public void setFile(Boolean file) {
        this.file = file;
    }
    
}
