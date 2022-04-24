
import java.util.*;

public class Scheduler {
    private ArrayList<Program> programs;
    private LinkedList<Program> readyQueue;
    private LinkedList<Program> blockedQueue;
    private int timeSlice = 2;
    private int clock = 0;
    public Scheduler(){
        readyQueue = new LinkedList<Program>();
        blockedQueue = new LinkedList<Program>();
    }
    public void run2() throws Exception{
        while(!readyQueue.isEmpty()){
            for(int i = 0; i < timeSlice; i++){
                executeInstruction(readyQueue.getFirst(), i);
                clock++;
            }
            Program p = readyQueue.removeFirst();
            readyQueue.addLast(p);
        }
    }
    public void executeInstruction(Program p, int n) throws Exception{
        Instruction i = p.instructions.get(n);
        switch(i.parameters.get(0)){
            case "semWait": i.semWait(i.parameters.get(1));break;
            case "semSignal": i.semSignal(i.parameters.get(1));break;
            case "assign": i.assign(i.parameters.get(1), i.parameters.get(2), p);break;
            case "print": i.print(i.parameters.get(1), p);break;
            case "printFromTo": i.printFromTo(i.parameters.get(1), i.parameters.get(2), p);break;                        
            case "writeFile": i.writeFile(i.parameters.get(1), i.parameters.get(2), p);break;
            case "readFile": Instruction.readFile(i.parameters.get(1));break;
        }
        p.instructions.remove(n);
    }
    public void run(OpSystem os) throws Exception{
        ArrayList<Program> tmp = new ArrayList<>();
        tmp = os.programs;
        for(Program p : tmp){
            for(Instruction i : p.instructions){
                switch(i.parameters.get(0)){
                    case "semWait": i.semWait(i.parameters.get(1));break;
                    case "semSignal": i.semSignal(i.parameters.get(1));break;
                    case "assign": i.assign(i.parameters.get(1), i.parameters.get(2), p);break;
                    case "print": i.print(i.parameters.get(1), p);break;
                    case "printFromTo": i.printFromTo(i.parameters.get(1), i.parameters.get(2), p);break;                        
                    case "writeFile": i.writeFile(i.parameters.get(1), i.parameters.get(2), p);break;
                    case "readFile": Instruction.readFile(i.parameters.get(1));break;
                    default: break;
                }
            }
        }
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
}
