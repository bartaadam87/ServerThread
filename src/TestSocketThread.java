import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestSocketThread {

	// Need away to stop the program...
	private static boolean acceptMore = true;
	public String incomingMsg;

	public static void main(String[] args) {
		new TestSocketThread();
	}

	public TestSocketThread() {
		ServerSocket serverSocket = null;
		try {
			System.out.println("Starting socket thread...");
			serverSocket = new ServerSocket(3344, 100);

			while (acceptMore) {
				System.out
						.println("ServerSocket created, waiting for incomming connections...");
				Socket socket = serverSocket.accept();
				// new Thread(new SocketThread(socket)).start();
				new Thread().start();

				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				System.out.println("Connection accepted, reading...");

				while ((incomingMsg = in.readLine()) != null
						&& socket.isConnected()) {
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(incomingMsg);
					JSONObject cat = (JSONObject) obj;
					

//
//					Object obj = parser.parse(incomingMsg);
//					JSONArray array = (JSONArray) obj;
//					System.out.println(array.get(0));
//					System.out.println(array.get(1));
//					System.out.println(array.get(2));
//					System.out.println(array.get(3));
//					System.out.println(array.get(4));
//					System.out.println(array.get(5));
//					System.out.println();

					System.out.print(cat.get("no") + System.getProperty("line.separator"));
					
					
					System.out.println("Message recieved: " + incomingMsg
							+ ". Answering...");

					// send a message
					String outgoingMsg = "Message \"" + incomingMsg
							+ "\" recieved on server."
							+ System.getProperty("line.separator");

					out.write(outgoingMsg);
					out.flush();

					System.out.println("Message sent: " + outgoingMsg);
				}

				if (socket.isConnected())
					System.out.println("Socket still connected");
				else
					System.out.println("Socket not connected");
			}
		} catch (IOException | ParseException exp) {
			exp.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (Exception e) {
			}
		}
	}

	public class SocketThread implements Runnable {

		private Socket socket;

		public SocketThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			// Process socket...
		}
	}
}