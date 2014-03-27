import java.net.Socket;
import java.util.concurrent.Callable;


/**
 * 
 * @author swhitehead
 */
public class SocketCallable implements Callable {

	private Socket socket;

	public SocketCallable(Socket socket) {
		this.socket = socket;
	}

	@Override
	public Object call() throws Exception {
		// Process socket requests...
		return null;
	}

}
