
import java.util.*;

public class Scheduler {
    private ArrayList<Program> programs;
    private ArrayList<Program> sortedPrograms;
    private LinkedList<Program> readyQueue;
    private LinkedList<Program> blockedQueue;
    private int timeSlice = 2;
    private int clock = 0;
    private Scanner sc;
    Mutex m;
    public Scheduler(Mutex m){
        readyQueue = new LinkedList<Program>();
        blockedQueue = new LinkedList<Program>();
        programs = new ArrayList<Program>();
        sortedPrograms = new ArrayList<Program>();
        this.m = m;
        sc = new Scanner(System.in);
    }
    public void run() throws Exception{
        for(int i = 0; i < 1000; i++){
            for(Program p : programs){
                if(p.getTimeAdded() == i){
                    sortedPrograms.add(p);
                }
            }
        }
        while(!programs.isEmpty()){
            for(Program p : sortedPrograms){
                if(p.getTimeAdded() == clock){
                    readyQueue.add(p);
                }
            }
            Program e;
            while(!readyQueue.isEmpty()){
                for(Program p : sortedPrograms){
                    if(readyQueue.contains(p)){
                        printQueues();
                        e = readyQueue.removeFirst();
                        runReadyQueue(e);
                    }
                }
            }
            clock++;
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
        clock++;
        sc.nextLine();
        for(Program q : sortedPrograms){
            if(q.getTimeAdded() == clock){
                readyQueue.add(q);
            }
        }
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
        System.out.println("semwait " + s);
    }
    public void semSignal(String s, Program p){
        if(s.equals("userInput")){
            m.setUserInput(false);
            p.setInput(false);
            for(Program x : m.getUserInputQueue()){
                blockedQueue.remove(x);
                readyQueue.add(x);
            }
            m.getUserInputQueue().clear();
        }
        else if(s.equals("userOutput")){
            m.setUserOutput(false);
            p.setOutput(false);
            for(Program x : m.getUserOutputQueue()){
                blockedQueue.remove(x);
                readyQueue.add(x);
            }
            m.getUserOutputQueue().clear();
        }
        else{
            m.setAccessingFile(false);
            p.setFile(false);
            for(Program x : m.getAccessingFileQueue()){
                blockedQueue.remove(x);
                readyQueue.add(x);
            }
            m.getAccessingFileQueue().clear();
        }
        System.out.println("semsignal " + s);
    }

    public void printQueues(){
        System.out.println("********");
        System.out.println("At time " + clock);
        System.out.println("#Ready Queue:");
        for(Program p : readyQueue){
            System.out.println(p);
        }
        System.out.println("#Blocked Queue:");
        for(Program p : blockedQueue){
            System.out.println(p);
        }
        System.out.println("********");
    }
    public void runReadyQueue(Program p){
        System.out.println("___________________");
        System.out.println(p + " is running.");
        for(int i = 0; i < timeSlice; i++){
            System.out.println("-time " + clock);
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
            sc.nextLine();
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
            printQueues();
        }
        else{
            System.out.println("Program Finished Execution.");
            programs.remove(p);
            printQueues();
        }
    }


    public void setPrograms(ArrayList<Program> programs) {
        this.programs = programs;
    }
    public ArrayList<Program> getVariables() {
        return programs;
    }
    
}
