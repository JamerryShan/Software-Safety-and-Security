JavaPathfinder core system v8.0 (rev ed601659c05efa232caf2862d9d9e6730a38a734) - (C) 2005-2014 United States Government. All rights reserved.


====================================================== system under test
QueueTest.main()

====================================================== search started: 2021-05-10 13:10

====================================================== error 1
gov.nasa.jpf.vm.NotDeadlockedProperty
deadlock encountered:
  thread QueueTest$Worker:{id:1,name:Thread-1,status:WAITING,priority:5,isDaemon:false,lockCount:1,suspendCount:0}
  thread QueueTest$Worker:{id:2,name:Thread-2,status:WAITING,priority:5,isDaemon:false,lockCount:1,suspendCount:0}


====================================================== trace #1
------------------------------------------------------ transition #0 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"ROOT" ,1/1,isCascaded:false}
      [3168 insn w/o sources]
  QueueTest.java:29              : q = new Queue(Q_SIZE);
      [1 insn w/o sources]
  Queue.java:2                   : public class Queue {
      [2 insn w/o sources]
  Queue.java:2                   : public class Queue {
  Queue.java:1                   : /** Fixed-size, blocking queue with atomic multi-element put/remove. */
  QueueTest.java:29              : q = new Queue(Q_SIZE);
  Queue.java:8                   : public Queue(int size) {
      [1 insn w/o sources]
  Queue.java:9                   : assert (size <= Integer.MAX_VALUE / 2);
  Queue.java:10                  : data = new byte[size];
  Queue.java:11                  : this.size = size;
  Queue.java:12                  : p = 0;
  Queue.java:13                  : c = 0;
  Queue.java:14                  : }
  QueueTest.java:29              : q = new Queue(Q_SIZE);
  QueueTest.java:30              : for (int i = 0; i < N; i++) {
  QueueTest.java:31              : (new Worker(new byte[]{(byte)i, (byte)(i + DELTA)})).start();
      [1 insn w/o sources]
  QueueTest.java:10              : static class Worker extends Thread {
      [2 insn w/o sources]
  QueueTest.java:10              : static class Worker extends Thread {
  QueueTest.java:1               : /** Class to test queue. */
  QueueTest.java:31              : (new Worker(new byte[]{(byte)i, (byte)(i + DELTA)})).start();
  QueueTest.java:13              : Worker(byte[] data) {
      [145 insn w/o sources]
  QueueTest.java:14              : this.data = data;
  QueueTest.java:15              : }
  QueueTest.java:31              : (new Worker(new byte[]{(byte)i, (byte)(i + DELTA)})).start();
      [1 insn w/o sources]
------------------------------------------------------ transition #1 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"START" ,1/2,isCascaded:false}
      [2 insn w/o sources]
  QueueTest.java:30              : for (int i = 0; i < N; i++) {
  QueueTest.java:31              : (new Worker(new byte[]{(byte)i, (byte)(i + DELTA)})).start();
  QueueTest.java:13              : Worker(byte[] data) {
      [27 insn w/o sources]
------------------------------------------------------ transition #2 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,1/2,isCascaded:false}
      [119 insn w/o sources]
  QueueTest.java:14              : this.data = data;
  QueueTest.java:15              : }
  QueueTest.java:31              : (new Worker(new byte[]{(byte)i, (byte)(i + DELTA)})).start();
      [1 insn w/o sources]
------------------------------------------------------ transition #3 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"START" ,1/3,isCascaded:false}
      [2 insn w/o sources]
  QueueTest.java:30              : for (int i = 0; i < N; i++) {
  QueueTest.java:33              : }
  QueueTest.java:3               : public class QueueTest {
------------------------------------------------------ transition #4 thread: 1
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"TERMINATE" ,1/2,isCascaded:false}
      [1 insn w/o sources]
  QueueTest.java:1               : /** Class to test queue. */
  QueueTest.java:18              : byte[] result = new byte[2];
  QueueTest.java:19              : q.put(data);
------------------------------------------------------ transition #5 thread: 1
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,1/2,isCascaded:false}
  QueueTest.java:19              : q.put(data);
  Queue.java:18                  : int len = items.length;
  Queue.java:19                  : assert (len <= size);
  Queue.java:21                  : while (p + len > c + size) {
  Queue.java:27                  : assert (p + len <= c + size) :
  Queue.java:29                  : for (int i = 0; i < len; i++) {
  Queue.java:30                  : data[p++ % size] = items[i];
  Queue.java:29                  : for (int i = 0; i < len; i++) {
  Queue.java:30                  : data[p++ % size] = items[i];
  Queue.java:29                  : for (int i = 0; i < len; i++) {
  Queue.java:32                  : assert (p <= c + size) :
  Queue.java:34                  : notifyAll(); // wake up waiting consumers
      [2 insn w/o sources]
  Queue.java:35                  : }
  QueueTest.java:20              : q.remove(result);
------------------------------------------------------ transition #6 thread: 1
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,1/2,isCascaded:false}
  QueueTest.java:20              : q.remove(result);
  Queue.java:54                  : int len = storage.length;
  Queue.java:55                  : waitForData(len);
  Queue.java:39                  : assert (len <= size);
  Queue.java:42                  : wait();
      [1 insn w/o sources]
------------------------------------------------------ transition #7 thread: 2
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"WAIT" ,1/1,isCascaded:false}
      [1 insn w/o sources]
  QueueTest.java:1               : /** Class to test queue. */
  QueueTest.java:18              : byte[] result = new byte[2];
  QueueTest.java:19              : q.put(data);
  Queue.java:18                  : int len = items.length;
  Queue.java:19                  : assert (len <= size);
  Queue.java:21                  : while (p + len > c + size) {
  Queue.java:23                  : wait();
      [1 insn w/o sources]

====================================================== snapshot #1
thread QueueTest$Worker:{id:1,name:Thread-1,status:WAITING,priority:5,isDaemon:false,lockCount:1,suspendCount:0}
  waiting on: Queue@162
  call stack:
        at java.lang.Object.wait(Object.java)
        at Queue.waitForData(Queue.java:42)
        at Queue.remove(Queue.java:55)
        at QueueTest$Worker.run(QueueTest.java:20)

thread QueueTest$Worker:{id:2,name:Thread-2,status:WAITING,priority:5,isDaemon:false,lockCount:1,suspendCount:0}
  waiting on: Queue@162
  call stack:
        at java.lang.Object.wait(Object.java)
        at Queue.put(Queue.java:23)
        at QueueTest$Worker.run(QueueTest.java:19)


====================================================== results
error #1: gov.nasa.jpf.vm.NotDeadlockedProperty "deadlock encountered:   thread QueueTest$Worker:{i..."

====================================================== statistics
elapsed time:       00:00:00
states:             new=8,visited=0,backtracked=0,end=1
search:             maxDepth=8,constraints=0
choice generators:  thread=8 (signal=1,lock=4,sharedRef=0,threadApi=2,reschedule=1), data=0
heap:               new=384,released=13,maxLive=374,gcCycles=8
instructions:       3732
max memory:         123MB
loaded code:        classes=65,methods=1489

====================================================== search finished: 2021-05-10 13:10