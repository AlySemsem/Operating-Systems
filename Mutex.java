import java.util.LinkedList;

public class Mutex {
    private boolean accessingFile;
    private boolean userInput;
    private boolean userOutput;
    private LinkedList<Program> accessingFileQueue;
    private LinkedList<Program> userInputQueue;
    private LinkedList<Program> userOutputQueue;
    public Mutex(){
        accessingFile = false;
        userInput = false;
        userOutput = false;
        accessingFileQueue = new LinkedList<Program>();
        userInputQueue  = new LinkedList<Program>();
        userOutputQueue  = new LinkedList<Program>();
    }
    public LinkedList<Program> getAccessingFileQueue() {
        return accessingFileQueue;
    }
    public LinkedList<Program> getUserInputQueue() {
        return userInputQueue;
    }
    public LinkedList<Program> getUserOutputQueue() {
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
