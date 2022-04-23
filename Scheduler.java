import java.util.*;

public class Scheduler {
    private ArrayList<Program> programs;
    private LinkedList readyQueue;
    private LinkedList blockedQueue;
    public Scheduler(){
        readyQueue = new LinkedList<Instruction>();
        blockedQueue = new LinkedList<Instruction>();
    }
    public void run(){
        
    }

    public ArrayList<Program> getPrograms() {
        return programs;
    }
    public LinkedList getReadyQueue() {
        return readyQueue;
    }
    public LinkedList getBlockedQueue() {
        return blockedQueue;
    }
}
