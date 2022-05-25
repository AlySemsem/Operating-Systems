
import java.util.*;

public class Scheduler {
    private ArrayList<Program> programs;
    private ArrayList<Program> sortedPrograms;
    private LinkedList<Program> readyQueue;
    private LinkedList<Program> blockedQueue;
    private int timeSlice;
    private int clock = 0;
    private Scanner sc;
    Memory mem;
    Mutex m;
    public Scheduler(Mutex m, int timeSlice){
        readyQueue = new LinkedList<Program>();
        blockedQueue = new LinkedList<Program>();
        programs = new ArrayList<Program>();
        sortedPrograms = new ArrayList<Program>();
        this.m = m;
        this.timeSlice = timeSlice;
        sc = new Scanner(System.in);
        mem = new Memory();
    }
    public void run() throws Exception{
        int x = 1;
        for(int i = 0; i < 5; i++){
            for(Program p : programs){
                if(p.getTimeAdded() == i){
                    p.setId(x);
                    sortedPrograms.add(p);
                    x++;
                }
            }
        }
        while(!programs.isEmpty()){
            for(Program p : sortedPrograms){
                if(p.getTimeAdded() == clock){
                    readyQueue.add(p);
                    enterProcessToMemory(p);
                }
            }
            Program e;
            printQueues();
            while(!readyQueue.isEmpty()){
                for(Program p : sortedPrograms){
                    if(readyQueue.contains(p)){
                        e = readyQueue.removeFirst();
                        runReadyQueue(e);
                    }
                }
            }
            clock++;
        }
    }
    public Boolean executeInstruction(Program p, int x) throws Exception{
        Instruction i = p.instructions.get(0);
        switch(i.parameters.get(0)){
            case "semWait": semWait(i.parameters.get(1), p);break;
            case "semSignal": semSignal(i.parameters.get(1), p);break;
            case "assign":
                if(i.parameters.size() < 4){
                    SystemCall.input(i.parameters.get(1), p);
                    p.instructions.get(0).parameters.set(0, "saveVariable");
                    return true;
                }
                else {
                    SystemCall.assignRead(i.parameters.get(3), p);
                    p.instructions.get(0).parameters.set(0, "saveVariable");
                    return true;
                }
            case "saveVariable": SystemCall.setVariable(i.parameters.get(1), p);break;
            case "print": SystemCall.print(i.parameters.get(1), p);break;
            case "printFromTo": SystemCall.printFromTo(i.parameters.get(1), i.parameters.get(2), p);break;                        
            case "writeFile": SystemCall.writeFile(i.parameters.get(1), i.parameters.get(2), p);break;
            case "readFile": SystemCall.readFile(i.parameters.get(1));break;
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
                enterProcessToMemory(q);
            }
        }
        p.instructions.remove(0);
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
            if(!m.getUserInputQueue().isEmpty()){
                readyQueue.add(blockedQueue.removeFirst());
                m.getUserInputQueue().removeFirst();
            }
            
        }
        else if(s.equals("userOutput")){
            m.setUserOutput(false);
            p.setOutput(false);
            if(!m.getUserOutputQueue().isEmpty()){
                readyQueue.add(blockedQueue.removeFirst());
                m.getUserOutputQueue().removeFirst();
            }
            
        }
        else{
            m.setAccessingFile(false);
            p.setFile(false);
            if(!m.getAccessingFileQueue().isEmpty()){
                readyQueue.add(blockedQueue.removeFirst());
                m.getAccessingFileQueue().removeFirst();
            }
            
        }
        System.out.println("semsignal " + s);
    }

    public void printQueues(){
        System.out.println("********");
        System.out.println("At time " + clock);
        System.out.println(" ");
        System.out.println("    #Ready Queue:");
        for(Program p : readyQueue){
            System.out.println(" " + p);
        }
        System.out.println(" ");
        System.out.println("    #Blocked Queue:");
        for(Program p : blockedQueue){
            System.out.println(" " + p);
        }
        System.out.println(" ");
        System.out.println("    #User Input Queue:");
        for(Program p : m.getUserInputQueue()){
            System.out.println(" " + p);
        }
        System.out.println(" ");
        System.out.println("    #User Output Queue:");
        for(Program p : m.getUserOutputQueue()){
            System.out.println(" " + p);
        }
        System.out.println(" ");
        System.out.println("    #Accessing File Queue:");
        for(Program p : m.getAccessingFileQueue()){
            System.out.println(" " + p);
        }
        System.out.println(" ");
        System.out.println("********");
    }
    
    public void runReadyQueue(Program p){
        System.out.println("__________________________");
        System.out.println(p + " is executing.\n");
        printQueues();
        for(int i = 0; i < timeSlice; i++){
            System.out.println("-time " + clock);
            if(p.instructions.isEmpty()){
                continue;
            }
            try {
                if(!executeInstruction(p, i)){
                    printQueues();
                    break;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if(p.instructions.isEmpty()){
                System.out.println("\nProgram Finished Execution.");
                programs.remove(p);
                printQueues();
            }
            clock++;
            sc.nextLine();
            for(Program q : sortedPrograms){
                if(q.getTimeAdded() == clock){
                    readyQueue.add(q);
                    enterProcessToMemory(q);
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
    }
    public void enterProcessToMemory(Program p){
        String[] memoryData = mem.getM();
        int c = 0;
        for(String s : memoryData){
            if(s == null){
                break;
            }
            c++;
        }
        Memory.setMemory(mem, p, c);
        mem.setM(memoryData);
        for(String s : mem.getM()){
            System.out.println(s);
        }
    }


    public void setPrograms(ArrayList<Program> programs) {
        this.programs = programs;
    }
    public ArrayList<Program> getVariables() {
        return programs;
    }
    
}
