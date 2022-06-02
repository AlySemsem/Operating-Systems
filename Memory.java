import java.io.*;
import java.util.ArrayList;

public class Memory {
    String[] m;
    public Memory(){
        m = new String[40];
    }

    public static Boolean setMemory(Memory m, Program p, int lowerBoundry){
        int upperBoundry = lowerBoundry + p.instructions.size() + 3 + 4 -1;
        int[] memoryBoundries = {lowerBoundry, upperBoundry};
        PCB pcb = new PCB(p.getId(), p.state, p.instructions.size(), memoryBoundries);
        if(upperBoundry >= 40 || m.getM()[upperBoundry] != null){
            removeProcessFromMemory(m);
            setMemory(m, p, 0);
            return false;
        }
        m.getM()[lowerBoundry] = "Process ID: " + Integer.toString(pcb.processId);
        m.getM()[lowerBoundry +1] = "Process State: " + Integer.toString(pcb.processState);
        m.getM()[lowerBoundry +2] = "Instruction size: " + Integer.toString(pcb.programCounter);
        m.getM()[lowerBoundry +3] = "Memory Boundries: " + Integer.toString(lowerBoundry) + " to " + Integer.toString(upperBoundry);
        int x = 0;
        for(Instruction i : p.instructions){
            String instruction = "";
            for(String s : i.parameters){
                instruction = instruction + s + " ";
            }
            m.getM()[lowerBoundry + 4 + x] = "Instruction "+ x + ": " + instruction;
            x++;
        }
        int variableLocations = lowerBoundry + 4 + x;
        m.getM()[variableLocations] = "Variable 1: ";
        m.getM()[variableLocations + 1] = "Variable 2: ";
        m.getM()[variableLocations + 2] = "Variable 3: ";
        if(!p.getVariables().isEmpty()){
            updateVariablesInMemory(m, p);
        }

        return true;
    }

    public ArrayList<String> setDataForDisk(PCB pcb, Program p){
        ArrayList<String> data = new ArrayList<>();
        data.add("Process ID: " + Integer.toString(pcb.processId));
        data.add("Process State: " + Integer.toString(pcb.processState));
        data.add("Instruction size: " + Integer.toString(pcb.programCounter));
        data.add("Memory Boundries: " + Integer.toString(pcb.memoryBoundries[0]) + " to " + Integer.toString(pcb.memoryBoundries[1]));

        for(Instruction i : p.instructions){
            String instruction = "";
            for(String s : i.parameters){
                instruction = instruction + s + " ";
            }
            data.add(instruction);
        }
        data.add("Variable 1: ");
        data.add("Variable 2: ");
        data.add("Variable 3: ");
        
        return data;
    }

    public static int getProcessIdFromMemory(Memory m){
        int x = 0;
        for(String s : m.getM()){
            if(s == null) {
                x++;
                continue;
            }
            String[] splitted = s.split(" ");
            if(splitted[1].equals("ID:")){
                break;
            }
            x++;
        }
        return x;
    }

    public static int getProcessIdFromMemory(Memory m, int id){
        int x = 0;
        for(String s : m.getM()){
            if(s == null) {
                x++;
                continue;
            }
            String[] splitted = s.split(" ");
            if(splitted[1].equals("ID:")){
                if(splitted[2].equals(Integer.toString(id)))
                    break;
            }
            x++;
        }
        return x;
    }

    public static void removeProcessFromMemory(Memory m){
        int processIdLocation = getProcessIdFromMemory(m);
        String id = m.getM()[processIdLocation].split(" ")[2];
        String boundries = m.getM()[processIdLocation+3];
        String[] memoryBoundries = boundries.split(" ");
        int lowerBoundry = Integer.parseInt(memoryBoundries[2]);
        int upperBoundry = Integer.parseInt(memoryBoundries[4]);
        ArrayList<String> processToDisk = new ArrayList<String>();
        for(int i = lowerBoundry; i <= upperBoundry; i++){
            processToDisk.add(m.getM()[i]);
            m.getM()[i] = null;
        }
        Disk.diskProcesses.put(Integer.parseInt(processToDisk.get(0).split(" ")[2]), processToDisk);
        try {
            Disk.writeToDisk();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Process with ID " + id + " has been placed in disk.");
    }

    public static void updateVariablesInMemory(Memory m, Program p){
        int processIdLocation = getProcessIdFromMemory(m, p.id);
        String boundries = m.getM()[processIdLocation+3];
        String[] memoryBoundries = boundries.split(" ");
        int variableLocations = Integer.parseInt(memoryBoundries[4]) - 2;
        int y = 0;
        for(Variable v : p.getVariables()){
            if(m.getM()[variableLocations].split(" ").length < 3){
                m.getM()[variableLocations + y] = m.getM()[variableLocations + y] + v.name.toString() + " = " + v.value.toString();
                y++;
            }
            variableLocations += 1;
        }
    }
    public static void updateProcessState(Memory m, Program p, String state){
        int processIdLocation = getProcessIdFromMemory(m, p.id);
        m.getM()[processIdLocation+1] = "Process State: " + state;
    }
    public static void swap(Memory m){
        for(int i = 0; i < 40; i++){
            if(m.getM()[i] == null) continue; 
            String[] content = m.getM()[i].split(" ");
            if(content.length == 3 && content[1].equals("State:")){
                if(content[2].equals("0")){
                    String id = m.getM()[i - 1].split(" ")[2];
                    System.out.println("Process with ID " + id + " has been placed in disk.");
                    String[] boundries = m.getM()[i + 2].split(" ");
                    int lowerBoundry = Integer.parseInt(boundries[2]);
                    int upperBoundry = Integer.parseInt(boundries[4]);
                    ArrayList<String> processToDisk = new ArrayList<String>();
                    for(int j = lowerBoundry; j <= upperBoundry; j++){
                        processToDisk.add(m.getM()[j]);
                        m.getM()[j] = null;
                    }
                    Disk.diskProcesses.put(Integer.parseInt(processToDisk.get(0).split(" ")[2]), processToDisk);
                    try {
                        Disk.writeToDisk();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
    }

    public String[] getM() {
        return m;
    }

    public void setM(String[] m) {
        this.m = m;
    }

}
