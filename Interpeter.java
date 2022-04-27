import java.io.*;

public class Interpeter{

    //static Program program = new Program();
    
    public static void readProgram(String fileName, Mutex m, Scheduler sc, int t) throws IOException{
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String currentLine = br.readLine();
        Program program = new Program(fileName);

        while(currentLine != null){
            Instruction instruction = new Instruction();
            String[] content = currentLine.split(" ");
            for(String s : content){
                instruction.parameters.add(s);
            }
            program.instructions.add(instruction);

            currentLine = br.readLine();
        }
        br.close();
        program.setTimeAdded(t);
        sc.getPrograms().add(program);
    }
}