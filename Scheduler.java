
import java.util.*;

public class Scheduler {
    private ArrayList<Program> programs;
    private ArrayList<Program> sortedPrograms;
    private LinkedList<Program> readyQueue;
    private LinkedList<Program> blockedQueue;
    private int timeSlice = 2;
    private int clock = 0;
    Mutex m;
    public Scheduler(Mutex m){
        readyQueue = new LinkedList<Program>();
        blockedQueue = new LinkedList<Program>();
        programs = new ArrayList<Program>();
        sortedPrograms = new ArrayList<Program>();
        this.m = m;
    }
    public void run() throws Exception{
        for(int i = 0; i < 10; i++){
            for(Program p : programs){
                if(p.getTimeAdded() == i){
                    sortedPrograms.add(p);
                }
            }
        }
        while(true){
            for(Program p : sortedPrograms){
                if(p.getTimeAdded() == clock){
                    readyQueue.add(p);
                }
            }
            Program e;
            for(Program p : sortedPrograms){
                if(readyQueue.contains(p)){
                    printQueues();
                    e = readyQueue.removeFirst();
                    runReadyQueue(e);
                }
                else if(blockedQueue.contains(p)){
                    printQueues();
                    e = blockedQueue.removeFirst();
                    runBlockedQueue(e);
                }
            }
        }
    }
    public Boolean executeInstruction(Program p) throws Exception{
        Instruction i = p.instructions.get(0);
        switch(i.parameters.get(0)){
            case "semWait": semWait(i.parameters.get(1), p);break;
            case "semSignal": semSignal(i.parameters.get(1), p);break;
            case "assign": i.assign(i.parameters.get(1), i.parameters.get(2), p);break;
            case "print": i.print(i.parameters.get(1), p);break;
            case "printFromTo": i.printFromTo(i.parameters.get(1), i.parameters.get(2), p);break;                        
            case "writeFile": i.writeFile(i.parameters.get(1), i.parameters.get(2), p);break;
            case "readFile": Instruction.readFile(i.parameters.get(1));break;
        }
        if(!blockedQueue.contains(p)){
            p.instructions.remove(0);
            return true;
        }
        System.out.println("Failed to aquire mutex. Process entered blocked queue.");
        return false;
    }

    public ArrayList<Program> getPrograms() {
        return programs;
    }
    public LinkedList<Program> getReadyQueue() {
        return readyQueue;
    }
    public LinkedList<Program> getBlockedQueue() {
        return blockedQueue;
    }
    public int getClock() {
        return clock;
    }

    public void semWait(String s, Program p){
        if(s.equals("userInput")){
            if(!m.isUserInput()){
                m.setUserInput(true);
                p.setInput(true);
                if(m.getUserInputQueue().contains(p)){
                    m.getUserInputQueue().remove(p);
                }
            }
            else{
                m.getUserInputQueue().add(p);
                blockedQueue.add(p);
            }
        }
        else if(s.equals("userOutput")){
            if(!m.isUserOutput()){
                m.setUserOutput(true);
                p.setOutput(true);
                if(m.getUserOutputQueue().contains(p)){
                    m.getUserOutputQueue().remove(p);
                }
            }
            else{
                m.getUserOutputQueue().add(p);
                blockedQueue.add(p);
            }
        }
        else{
            if(!m.isAccessingFile()){
                m.setAccessingFile(true);
                p.setFile(true);
                if(m.getAccessingFileQueue().contains(p)){
                    m.getAccessingFileQueue().remove(p);
                }
            }
            else{
                m.getAccessingFileQueue().add(p);
                blockedQueue.add(p);
            }
        }
        System.out.println("semwait");
    }
    public void semSignal(String s, Program p){
        if(s.equals("userInput")){
            m.setUserInput(false);
            p.setInput(false);
        }
        else if(s.equals("userOutput")){
            m.setUserOutput(false);
            p.setOutput(false);
        }
        else{
            m.setAccessingFile(false);
            p.setFile(false);
        }
        System.out.println("semsignal");
    }

    public void printQueues(){
        System.out.println("********");
        System.out.println("At time " + clock);
        System.out.println("Ready Queue:");
        for(Program p : readyQueue){
            System.out.println(p);
        }
        System.out.println("Blocked Queue:");
        for(Program p : blockedQueue){
            System.out.println(p);
        }
        System.out.println("********");
    }
    public void runReadyQueue(Program p){
        System.out.println("___________________");
        System.out.println(p + " is running.");
        for(int i = 0; i < timeSlice; i++){
            System.out.println("time " + clock);
            if(p.instructions.isEmpty()){
                continue;
            }
            try {
                if(!executeInstruction(p)){
                    printQueues();
                    break;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            clock++;
            for(Program q : sortedPrograms){
                if(q.getTimeAdded() == clock){
                    readyQueue.add(q);
                    printQueues();
                }
            }
        }
        if(blockedQueue.contains(p)){
            return;
        }
        if(!p.instructions.isEmpty()){
            readyQueue.add(p);
        }
        else{
            System.out.println("Program Finished Execution.");
            printQueues();
        }
    }
    public void runBlockedQueue(Program e){
        System.out.println("___________________");
        System.out.println("Program that arrived at " + e.getTimeAdded() + " is running.");
        for(int i = 0; i < timeSlice; i++){
            System.out.println("time " + clock);
            if(e.instructions.isEmpty()){
                continue;
            }
            try {
                executeInstruction(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            clock++;
            for(Program q : sortedPrograms){
                if(q.getTimeAdded() == clock){
                    readyQueue.add(q);
                    printQueues();
                }
            }
        }
        if(!e.instructions.isEmpty()){
            readyQueue.add(e);
        }
        else{
            System.out.println("Program Finished Execution.");
        }
    }

    public void setPrograms(ArrayList<Program> programs) {
        this.programs = programs;
    }
    public ArrayList<Program> getVariables() {
        return programs;
    }
    
}
