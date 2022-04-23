import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OpSystem {
    ArrayList<Program> programs;
    public OpSystem(){
        programs = new ArrayList<Program>();
    }

    public static void main(String[] args) throws IOException {
        OpSystem os = new OpSystem();
        File file = new File("Program_1.txt");
        File file2 = new File("Program_2.txt");
        File file3 = new File("Program_3.txt");
        Interpeter.readFile(file, os);
        Interpeter.readFile(file2, os);
        Interpeter.readFile(file3, os);

        int j = 1;
        for(Program p : os.programs){
            System.out.println("Program " + j);
            for(Instruction i : p.instructions){
                System.out.println("-------------");
                for(String s : i.parameters){
                    System.out.println(s);
                }                    
            }
            System.out.println("_________________");
            j++;
        }
    }
    
}
