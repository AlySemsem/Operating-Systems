public class PCB extends MemoryData{
    int processId;
    int processState;
    int programCounter;
    int[] memoryBoundries;
    public PCB(int processId, int processState, int programCounter, int[] memoryBoundries){
        this.processId = processId;
        this.processState = processState;
        this.programCounter = programCounter;
        this.memoryBoundries = memoryBoundries;
    }
}
