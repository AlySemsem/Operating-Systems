import java.io.*;

public class Interpeter{

    //static Program program = new Program();
    
    public static void readFile(String fileName, OpSystem os) throws IOException{
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String currentLine = br.readLine();
        Program program = new Program();

        while(currentLine != null){
            Instruction instruction = new Instruction(new Mutex());
            String[] content = currentLine.split(" ");
            for(String s : content){
                instruction.parameters.add(s);
            }
            program.instructions.add(instruction);

            currentLine = br.readLine();
        }
        br.close();
        os.programs.add(program);
    }
    // public static void main(String[] args) throws IOException {
    //     File file = new File("Program_2.txt");
    //     readFile(file);
    //     for(Instruction i : program.instructions){
    //         System.out.println("___________________");
    //         for(String s : i.parameters){
    //             System.out.println(s);
    //         }
    //     }
    // }
}