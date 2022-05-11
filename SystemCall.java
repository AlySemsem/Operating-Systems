import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class SystemCall {
    public SystemCall(){
        
    }

    static public void print(String s, Program p){
        System.out.println("Print:  " + getVariable(s, p).value.toString());
    }

    static public void writeFile(String fileName ,String data, Program p) throws IOException{
        System.out.println("Writing a new file.");
        if(getVariable(fileName, p).name.equals(fileName)){
            File file = new File((String) getVariable(fileName, p).value);
            file.createNewFile();
            FileWriter w = new FileWriter(file);
            w.write(getVariable(data, p).value.toString());
            w.close();
        }
    }
    
    static public void printFromTo(String x, String y, Program p){
        System.out.println("Printing from " + x + " to " + y);
        if(getVariable(x, p).name.equals(x)){
            int a = (int) getVariable(x, p).value;
                if(getVariable(y, p).name.equals(y)){
                    int b = (int) getVariable(y, p).value;
                    for(int i = a+1; i < b; i++){
                        System.out.println(i);
                }    
            }
        }
    }

    static public String readFile(String fileName)throws Exception{
        System.out.println("Reading a file.");
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    static public void input(String s, Program p){
        Object x;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a value: ");
        
        x = sc.nextLine();
        if(((String) x).matches("\\d+")){
            x = Integer.parseInt((String) x);
        }else{
            x = x.toString();
        }
        p.setTemp(x);
    }

    static public void assignRead(String s, Program p) throws Exception{
        Variable v = getVariable(s, p);
        p.setTemp(readFile((String) v.value));
    }

    static public void setVariable(String s, Program p){
        System.out.println("Saving variable in memory.");
        Variable v = new Variable(s, p.getTemp());
        p.getVariables().add(v);
    }

    static public Variable getVariable(String s, Program p){
        for(Variable v : p.getVariables()){
            if(v.name.equals(s)){
                return v;
            }
        }
        return null;
    }
}

