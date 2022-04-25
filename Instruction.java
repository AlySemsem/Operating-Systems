import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Instruction {
    ArrayList<String> parameters;
    public Instruction(){
        parameters = new ArrayList<String>();
    }

    
    public void print(String s, Program p){
        for(Variable v : p.getVariables()){
            if(v.name.equals(s)){
                System.out.println(v.value);
            }
        }
    }
    public void writeFile(String fileName ,String data, Program p) throws IOException{
        for(Variable v : p.getVariables()){
            if(v.name.equals(fileName)){
                File file = new File((String) v.value);
                file.createNewFile();
                for(Variable z : p.getVariables()){
                    FileWriter w = new FileWriter(file);
                    w.write(z.value.toString());
                    w.close();
                }
            }
        }
    }
    public void printFromTo(String x, String y, Program p){
        for(Variable v : p.getVariables()){
            if(v.name.equals(x)){
                int a = (int) v.value;
                for(Variable z : p.getVariables()){
                    if(z.name.equals(y)){
                        int b = (int) z.value;
                        for(int i = a+1; i < b; i++){
                            System.out.println(i);
                        }
                        break;
                    }
                }
            }
        }
    }
    public void assign(String s, Object o, Program p) throws Exception{
        Object x;
        if(o instanceof Integer){
            x = (int)o;
            Variable v = new Variable(s, x);
            p.getVariables().add(v);
        }
        else if(o.toString().equals("input")){
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter a value: ");

            x = sc.nextLine();
            if(((String) x).matches("\\d+")){
                x = Integer.parseInt((String) x);
            }else{
                x = x.toString();
            }
            Variable v = new Variable(s, x);
            p.getVariables().add(v);
        }
        else if(o.toString().equals("readFile")){
            Boolean flag = false;
            x = "error";
            for(Variable z : p.getVariables()){
                if(z.name.equals(parameters.get(3))){
                    x = readFile(z.value.toString());
                    flag = true;
                    
                }
            }
            if(flag){
                Variable v = new Variable(s, x);
                p.getVariables().add(v);
            }
        }
        else{
            x = o.toString();
            Variable v = new Variable(s, x);
            p.getVariables().add(v);
        }
    }
    public static String readFile(String fileName)throws Exception{
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}
