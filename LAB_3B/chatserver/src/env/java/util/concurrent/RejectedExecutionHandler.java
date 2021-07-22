package env.java.util.concurrent;

/** Interface for RejectedExecutionHandler. */

public interface RejectedExecutionHandler {
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor);
}
