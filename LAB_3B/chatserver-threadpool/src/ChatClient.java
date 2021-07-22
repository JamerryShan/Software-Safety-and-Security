import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ChatClient {
    int id;
    static int currID = 0;

    public final static void main(String args[]) {
        new ChatClient().exec();
    }

    public ChatClient() {
        synchronized(getClass()) {
            id = currID++;
        }
    }

    public void exec() {
        try {
            Socket socket = new Socket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 4444);
            socket.connect(addr);
            System.out.println("Client " + id + " connected.");
            InputStreamReader istr =
                new InputStreamReader(socket.getInputStream());
            BufferedReader in = new BufferedReader(istr);
            OutputStreamWriter out =
                new OutputStreamWriter(socket.getOutputStream());
            
            out.write(id + ": Hello, world!\n");
            out.flush();

            String line = in.readLine();
            System.out.println(id + ": Received " + line + "\n");
            if (line != null) {
                boolean fromClient = Pattern.compile("\\[[0-9]+\\].*$").matcher(line).find(); // -- 3.4 initial
                boolean clientQuit = Pattern.compile("Client [0-9]+ quit.$").matcher(line).find();  // -- 3.4 additional
                assert (fromClient || line.equals("Server busy") || clientQuit); // -- 3.4 inital property + additional property
            }
            out.close();
        } catch(IOException e) {
            System.err.println("Client " + id + ": " + e);
        }
    }
}
