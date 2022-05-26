import java.util.ArrayList;

public class Program {
    ArrayList<Instruction> instructions;
    private ArrayList<Variable> variables;
    private Boolean input = false;
    private Boolean output = false;
    private Boolean file = false;
    private int timeAdded;
    private String name;
    private Object temp;
    int id;
    int state;

    public Program(String name){
        variables = new ArrayList<Variable>();
        instructions = new ArrayList<Instruction>();
        this.name = name;
        state = 0;
    }

    
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public Object getTemp() {
        return temp;
    }
    public void setTemp(Object temp) {
        this.temp = temp;
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
    @Override
    public String toString() {
        return name;
    }
}
