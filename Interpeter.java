import java.io.*;

public class Interpeter{

    static Program program = new Program();
    
    public static void readFile(File file) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        String currentLine = br.readLine();
        

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
    }
    public static void main(String[] args) throws IOException {
        File file = new File("Program_1.txt");
        readFile(file);
        for(Instruction i : program.instructions){
            System.out.println("___________________");
            for(String s : i.parameters){
                System.out.println(s);
            }
        }
    }
}