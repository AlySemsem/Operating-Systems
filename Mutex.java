import java.util.LinkedList;

public class Mutex {
    private boolean accessingFile;
    private boolean userInput;
    private boolean userOutput;
    private LinkedList<Instruction> accessingFileQueue;
    private LinkedList<Instruction> userInputQueue;
    private LinkedList<Instruction> userOutputQueue;
    public Mutex(){
        accessingFile = false;
        userInput = false;
        userOutput = false;
    }
    public LinkedList<Instruction> getAccessingFileQueue() {
        return accessingFileQueue;
    }
    public LinkedList<Instruction> getUserInputQueue() {
        return userInputQueue;
    }
    public LinkedList<Instruction> getUserOutputQueue() {
        return userOutputQueue;
    }
    public boolean isAccessingFile() {
        return accessingFile;
    }
    public void setAccessingFile(boolean accessingFile) {
        this.accessingFile = accessingFile;
    }
    public boolean isUserInput() {
        return userInput;
    }
    public void setUserInput(boolean userInput) {
        this.userInput = userInput;
    }
    public boolean isUserOutput() {
        return userOutput;
    }
    public void setUserOutput(boolean userOutput) {
        this.userOutput = userOutput;
    }
}
