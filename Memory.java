public class Memory {
    String[] m;
    public Memory(){
        m = new String[40];
    }

    public static Boolean setMemory(Memory m, Program p, int lowerBoundry){
        int upperBoundry = lowerBoundry + p.instructions.size() + 3 + 4;
        if(upperBoundry > 40 || m.getM()[upperBoundry] != null){
            return false;
        }
        int[] memoryBoundries = {lowerBoundry, upperBoundry};
        PCB pcb = new PCB(p.getId(), 0, p.instructions.size(), memoryBoundries);
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
        m.getM()[lowerBoundry + 4 + x] = "Variable 1: ";
        m.getM()[lowerBoundry + 4 + x + 1] = "Variable 2: ";
        m.getM()[lowerBoundry + 4 + x + 2] = "Variable 3: ";

        return true;
    }

    public String[] getM() {
        return m;
    }

    public void setM(String[] m) {
        this.m = m;
    }

}
