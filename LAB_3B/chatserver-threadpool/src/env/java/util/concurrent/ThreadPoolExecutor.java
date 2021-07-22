package env.java.util.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.net.Socket;

/** ThreadPoolExecutor stub that either launches a thread or rejects it right
    away. Does not manage a thread pool or use a thread of its own, to be
    lightweight enough for JPF. */

public class ThreadPoolExecutor {

    int corePoolSize;
    int maximumPoolSize;
    long keepAliveTime;
    TimeUnit unit;
    int launchedThreads;
    BlockingQueue<Runnable> workQueue;
    RejectedExecutionHandler handler;
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
                              long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              RejectedExecutionHandler handler) {
        /** TODO: save relevant parameters in instance fields */
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.workQueue = (SynchronousQueue) workQueue;
        this.handler = handler;
        this.launchedThreads = 0;
        
    }
    public void shutdown() { } // stub, not used

    public void execute(Runnable r) {
        /** TODO: run the "Runnable" in a thread if maxPoolSize not reached,
          * otherwise, reject */
        if (this.launchedThreads < this.maximumPoolSize) {
            this.launchedThreads++;
            new Thread(r).start();
        } else {
            this.handler.rejectedExecution(r, this);
        }
    }
}
