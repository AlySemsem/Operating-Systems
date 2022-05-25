public class Main {
    public static void main(String[] args) throws Exception {
        Mutex m = new Mutex();
        Scheduler sc = new Scheduler(m, 3);

        Interpeter.readProgram("Program_1.txt", m, sc, 4);        
        Interpeter.readProgram("Program_2.txt", m, sc, 0);
        Interpeter.readProgram("Program_3.txt", m, sc, 10);
        sc.run();



    }
}
