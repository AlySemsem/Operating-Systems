import java.io.IOException;
import java.util.*;

public class Scheduler {
    private ArrayList<Program> programs;
    private LinkedList readyQueue;
    private LinkedList blockedQueue;
    public Scheduler(){
        readyQueue = new LinkedList<Instruction>();
        blockedQueue = new LinkedList<Instruction>();
    }
    public void run(OpSystem os) throws IOException{
        ArrayList<Program> tmp = new ArrayList<>();
        tmp = os.programs;
        while(true){
            for(Program p : tmp){
                for(Instruction i : p.instructions){
                    switch(i.parameters.get(0)){
                        case "semWait": i.semWait(i.parameters.get(1));break;
                        case "semSignal": i.semSignal(i.parameters.get(1));break;
                        case "assign": i.assign(i.parameters.get(1), i.parameters.get(2), p);break;
                        case "print": i.print(i.parameters.get(1));break;
                        case "printFromTo": i.printFromTo(i.parameters.get(1), i.parameters.get(2), p);break;
                        case "writeFile": i.writeFile(i.parameters.get(1), i.parameters.get(2), p);break;
                        case "readFile": Interpeter.readFile(i.parameters.get(1), os);break;
                        default: break;
                    }
                }
            }
        }
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
