import java.lang.*;

public class Main implements Runnable {

	    public void run() {
	        System.out.println("Hello from a thread!");
	    }

	    public static void main(String args[]) {
	        (new Main()).run();
	    }

	}
