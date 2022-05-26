import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Disk {
    static File disk = new File("disk.txt");
    static Hashtable<Integer, ArrayList<String>> diskProcesses = new Hashtable<Integer, ArrayList<String>>();
    public Disk(){

    }
    public static void writeToDisk() throws IOException{
        FileWriter w = new FileWriter(disk);
        for(int i : diskProcesses.keySet()){
            for(String s : diskProcesses.get(i)){
                w.write(s + "\n");
            }
        }
        w.close();
    }

    public static Boolean checkIdInDisk(String id) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(disk));
        String currentLine = br.readLine();
        while(currentLine != null){
            String[] content = currentLine.split(" ");
            if(content[1].equals("ID:")){
                if(content[2].equals(id)){
                    br.close();
                    return true;
                }
            }
            currentLine = br.readLine();
        }
        br.close();
        return false;
    }
}
