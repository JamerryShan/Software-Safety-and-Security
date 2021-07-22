import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import gov.nasa.jpf.vm.Verify;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import env.java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.RejectedExecutionException;

class RejectedExecutionHandlerClass implements RejectedExecutionHandler {
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) throws RejectedExecutionException {
        System.out.println("Rejected execution handler called");
        Worker w = (Worker) r;
        w.send("Server busy");
        w.closeSocket();
        assert(false);
        throw new RejectedExecutionException("");
    }
}

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
        // if (Verify.getBoolean()) {
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        // }
        // if (Verify.getBoolean()) {
        out = new PrintWriter(sock.getOutputStream(), true);
        // }
        // if (in == null || out == null) {
        //     sock.close();
        //     throw new IOException("Simulated exception when init worker.");
        // }
    }

    public void run() {
        System.out.println("Thread running: " + Thread.currentThread());
        try {
            assert(out != null);
            assert(chatServer.workers[idx] == null);
            synchronized(chatServer) {
                chatServer.workers[idx] = this;
                System.out.println("Registered worker " + idx + ".");
            }
            String s = null;
            while ((s = in.readLine()) != null) {
                boolean fromClient = Pattern.compile("[0-9]+:.*$").matcher(s).find();
                assert (fromClient); // -- 3.5.1 Additional property
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

    public void closeSocket() {
        try {
            sock.close();
        } catch (IOException e) {
            System.out.println("Closing socket..");
        }
    }
}

public class ChatServer {
    Worker workers[];
    Socket sock[];
    int n = 0;

    public ChatServer(int maxServ) {
        int port = 4444;
        workers = new Worker[maxServ];
        ServerSocket servsock = null;


        int corePoolSize = maxServ;
        int maxPoolSize = 1;
        long keepAliveTime = 15;
        BlockingQueue<Runnable> workQueue = new SynchronousQueue();
        RejectedExecutionHandler handler = new RejectedExecutionHandlerClass();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            workQueue,
            handler
        );

        try {
            servsock = new ServerSocket(port);
            while (maxServ-- != 0) {
                Socket sock = servsock.accept();
                Worker worker = new Worker(sock, this);
                n++;
                try {
                    executor.execute(worker);
                    // new Thread(worker).start(); -- moved to ThreadPoolExecutor.execute
                } catch(Exception e) {
                    System.out.println(e);
                    // assert(false);
                    // continue;
                    // sock.close(); // Given that the worker is rejected, we close the socket. 
                }
            }
            servsock.close();
        } catch(IOException ioe) {
            System.err.println("Server: " + ioe);
        }
        executor.shutdown();
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
        int c = 0;
        for (i = 0; i < n; i++) {
            if (workers[i] != null) {
                workers[i].send(s);
                c++;
            }
        }
    }

    public synchronized void remove(int i) {
        workers[i] = null;
        sendAll("Client " + i + " quit.");
    }
}
