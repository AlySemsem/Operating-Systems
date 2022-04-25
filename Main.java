public class Main {
    public static void main(String[] args) throws Exception {
        OpSystem os = new OpSystem();
        Mutex m = new Mutex();
        Scheduler sc = new Scheduler(m);

        Interpeter.readProgram("Program_1.txt", os, m, sc, 0);        
        Interpeter.readProgram("Program_2.txt", os, m, sc, 1);
        Interpeter.readProgram("Program_3.txt", os, m, sc, 4);
        sc.run();



    }
}
