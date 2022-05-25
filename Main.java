public class Main {
    public static void main(String[] args) throws Exception {
        Mutex m = new Mutex();
        Scheduler sc = new Scheduler(m, 2);

        Interpeter.readProgram("Program_1.txt", m, sc, 0);        
        Interpeter.readProgram("Program_2.txt", m, sc, 1);
        Interpeter.readProgram("Program_3.txt", m, sc, 4);
        sc.run();

    }
}
