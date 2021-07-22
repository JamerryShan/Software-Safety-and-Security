package env.java.util.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/** ThreadPoolExecutor stub that either launches a thread or rejects it right
    away. Does not manage a thread pool or use a thread of its own, to be
    lightweight enough for JPF. */

public class ThreadPoolExecutor {
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
                              long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              RejectedExecutionHandler handler) {
        /** TODO: save relevant parameters in instance fields */
    }

    public void shutdown() { } // stub, not used

    public void execute(Runnable r) {
        /** TODO: run the "Runnable" in a thread if maxPoolSize not reached,
          * otherwise, reject */
    }
}
