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
    }
    public void semSignal(String s){
        if(s.equals("userInput"))
            m.setUserInput(false);
        else if(s.equals("userOutput"))
            m.setUserOutput(false);
        else
            m.setAccessingFile(false);
    }
    public void print(String s){
        System.out.println(s);
    }
    public void writeFile(File file, String x) throws IOException{
        FileWriter w = new FileWriter(file);
        w.write(x);
        w.close();
    }
    public void printFromTo(int x, int y){
        for(int i = x; i > y; i++){
            System.out.println(i);
        }
    }
    public int assign(Object o){
        if(o instanceof Integer){
            int x = (int)o;
            return x;
        }
        else{
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter a value: ");

            int value = sc.nextInt();
            sc.close();
            return value;
        }
    }
}
