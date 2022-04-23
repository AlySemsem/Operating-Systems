import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Instruction {
    ArrayList<String> parameters;
    Mutex m;
    public Instruction(Mutex m){
        this.m = m;
        parameters = new ArrayList<String>();
    }

    public void semWait(String s){
        if(s.equals("userInput"))
            m.setUserInput(true);
        else if(s.equals("userOutput"))
            m.setUserOutput(true);
        else
            m.setAccessingFile(true);
        System.out.println("semwait");
    }
    public void semSignal(String s){
        if(s.equals("userInput"))
            m.setUserInput(false);
        else if(s.equals("userOutput"))
            m.setUserOutput(false);
        else
            m.setAccessingFile(false);
        System.out.println("semsignal");
    }
    public void print(String s){
        System.out.println(s);
    }
    public void writeFile(String fileName ,String x) throws IOException{
        File file = new File(fileName);
        file.createNewFile();
        FileWriter w = new FileWriter(file);
        w.write(x);
        w.close();
    }
    public void printFromTo(String x, String y, Program p){
        int a,b;
        boolean flagx = false;
        for(Variable v : p.getVariables()){
            if(v.name.equals(x) || flagx){
                a = v.value;
                System.out.println(a);
                flagx = true;
                if(v.name.equals(y)){
                    b = v.value;
                    //System.out.println(b);
                    for(int i = a+1; i < b; i++){
                        System.out.println("hi");
                    }
                    break;
                }
            }
        }
    }
    public void assign(String s, Object o, Program p){
        int x;
        if(o instanceof Integer){
            x = (int)o;
        }
        else{
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter a value: ");

            x = sc.nextInt();
        }
        Variable v = new Variable(s, x);
            p.getVariables().add(v);
    }
    public void sysCall(OpSystem os){
        Scheduler sch = new Scheduler();
        os.programs.get(0).instructions.get(0).parameters.get(0);
    }
}
