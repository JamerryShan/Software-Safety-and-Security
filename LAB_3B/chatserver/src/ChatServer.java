import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import gov.nasa.jpf.vm.Verify;

class Worker implements Runnable {
    Socket sock;
    PrintWriter out;
    BufferedReader in;
    ChatServer chatServer;
    int idx;

    public Worker(Socket s, ChatServer cs) throws IOException {
        chatServer = cs;
        sock = s;
        idx = chatServer.n;
        if (Verify.getBoolean()) {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        }
        if (Verify.getBoolean()) {
            out = new PrintWriter(sock.getOutputStream(), true);
        }
        if (in == null || out == null) {
            sock.close();
            throw new IOException("Simulated exception when init worker.");
        }
    }

    public void run() {
        System.out.println("Thread running: " + Thread.currentThread());
        try {
            assert(out != null);
            assert(chatServer.workers[idx] == null);
            chatServer.workers[idx] = this;
            System.out.println("Registered worker " + idx + ".");
            String s = null;
            while ((s = in.readLine()) != null) {
                chatServer.sendAll("[" + idx + "]" + s);
            }
            sock.close();
        } catch(IOException ioe) {
            System.out.println("Worker thread " + idx + ": " + ioe);
        } finally {
            chatServer.remove(idx);
        }
    }

    public void send(String s) {
        out.println(s);
    }
}

public class ChatServer {
    Worker workers[];
    int n = 0;

    public ChatServer(int maxServ) {
        int port = 4444;
        workers = new Worker[maxServ];
        Socket sock;
        ServerSocket servsock = null;
        try {
            servsock = new ServerSocket(port);
            while (maxServ-- != 0) {
                sock = servsock.accept();
                Worker worker;
                try {
                    worker = new Worker(sock, this);
                    new Thread(worker).start();
                    n++;
                } catch(IOException ioe) {
                    System.err.println("Server (init worker): " + ioe);
                    assert(false);
                    continue;
                }
            }
            servsock.close();
        } catch(IOException ioe) {
            System.err.println("Server: " + ioe);
        }
        System.out.println("Server shutting down.");
    }

    public static void main(String args[]) throws IOException {
        if (args.length == 0) {
            new ChatServer(-1);
        } else {
            new ChatServer(Integer.parseInt(args[0]));
        }
    }

    public synchronized void sendAll(String s) {
        int i;
        for (i = 0; i < n; i++) {
            if (workers[i] != null) {
                workers[i].send(s);
            }
        }
    }

    public synchronized void remove(int i) {
        workers[i] = null;
        sendAll("Client " + i + " quit.");
    }
}
