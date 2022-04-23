public class Mutex {
    private boolean accessingFile;
    private boolean userInput;
    private boolean userOutput;
    public Mutex(){
        accessingFile = false;
        userInput = false;
        userOutput = false;
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
