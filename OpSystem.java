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
        Interpeter.readFile("Program_1.txt", os);
        //Interpeter.readFile("Program_2.txt", os);
        //Interpeter.readFile("Program_3.txt", os);
        Scheduler sch = new Scheduler();
        sch.run(os);

        // int j = 1;
        // for(Program p : os.programs){
        //     System.out.println("Program " + j);
        //     for(Instruction i : p.instructions){
        //         System.out.println("-------------");
        //         for(String s : i.parameters){
        //             System.out.println(s);
        //         }                    
        //     }
        //     System.out.println("_________________");
        //     j++;
        // }
    }
    
}
