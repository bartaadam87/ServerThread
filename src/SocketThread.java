import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SocketThread {

	private static int port = 3344, maxConnections = 10;
	private static ServerSocket listener;
	
	// Listen for incoming connections and handle them
	public static void main(String[] args) throws IOException {

//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        System.out.print("Enter String: ");
//        String s = br.readLine();
//        System.out.print("Enter Integer: ");
//        try{
//            int i = Integer.parseInt(br.readLine());
//        }catch(NumberFormatException nfe){
//            System.err.println("Invalid Format!");
//        }
		
		int i = 0;
		
		try {		
			listener = new ServerSocket(port);
			Socket server;

			System.out.println("Connection established, reading...");
			while ((i++ < maxConnections) || (maxConnections == 0)) {
				System.out.println("Socket opened...");
				doComms connection;

				server = listener.accept();
				doComms conn_c = new doComms(server);
				Thread t = new Thread(conn_c);
				t.start();
			}
		} catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
	}

}

class doComms implements Runnable {
	private Socket server;
	private String line, input;

	doComms(Socket server) {
		this.server = server;
	}

	public void run() {
		input = "";

		try {
			// Get input from the client
			// DataInputStream in = new
			// DataInputStream(server.getInputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					server.getInputStream()));
			PrintStream out = new PrintStream(server.getOutputStream());

			while ((line = in.readLine()) != null && !line.equals(".")) {
//				JSONParser parser = new JSONParser();
				//
				// Object obj = parser.parse(line);
				// JSONObject cat = (JSONObject) obj;
				// System.out.println(array.get(0));
				// System.out.println(array.get(1));
				// System.out.println(array.get(2));
				// System.out.println(array.get(3));
				// System.out.println(array.get(4));
				// System.out.println(array.get(5));
				// System.out.println();

				input = input + line;
				// out.println("Message " + "'" + line + "'" + " delivered!");
				out.println(writeJSON().toJSONString());
				
				//MUSI PRIJIT ODDELENE - tzn. jako reakce na prijeti
//				out.println(writeMsg().toJSONString());
				out.println();
				System.out.println("Recieved: " + "'" + line + "'");
			}

			System.out.println();

			System.out.println("Disconnected.");
			out.println("Overall message is:" + input);

			server.close();

		} catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
	}

	public JSONObject writeJSON() {
		JSONObject object = new JSONObject();
		object.put("value", "sql");
		object.put("no", "1");
		object.put("breed", "MCO");
		object.put("ems", "f(22)");
		object.put("class", "1");
		object.put("sex", "01");
		object.put("born", "23.1.2011");
		return object;
	}
	
	public JSONObject writeMsg() {
		JSONObject object = new JSONObject();
		object.put("value", "msg");
		object.put("msg", "Recieved!");
		return object;
	}
}