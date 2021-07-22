# JPF

## 2.2 Diagnosis

### 2.2.1 Error trace table
<!-- Diagnose the error trace shown by JPF. Use a table like Table 1. If you have many transitions with “ins[truction]s w[ith]o[ut] sources”, you can summarize these as “[no source]”, perhaps to-gether with more interesting actions of other threads, such as shown in column 6 of the partial trace in Table 1. -->

| trans \ Thread     |   0 (Server main)  |    1 (Client 1)    |    2 (Client 2)    |  3-5 (Finalizers)  |    6 (Worker 1)    |    7 (Worker 2)    |
|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |
|                    |                    |                    |                    |                    |                    |                    |


### 2.2.2 Error trace explanation
<!-- Give an explanation of the error trace. Show how the server and clients interact, and how the failure happens. This can be done as an enumeration of about 5–12 key actions, with a shortphrase explaining their meaning and impact on the program’s behavior. Note: a first summary as shown above may still include a lot of detail that is not needed to understand the root cause of the trace. Once you have finished summarizing the trace, look again at the exception itself and where it occurs. Then, focus on field accesses that (presumably)caused the exception. -->

### 2.2.3 Patch
<!-- Fix the defect; provide a patch. After the fix, JPF won’t terminate for a long time, so you canstop it after a couple of seconds. -->

```java

```

## 3.1  Validation, documentation

### 3.1.1 Solution explanation

<!-- 1. Explain why your solution is likely correct. Are workers added (registered) and removed in a thread-safe way? Is the access to sockets of other workers always safe (no null pointer dereference;possible exceptions handled)? -->

### 3.1.2 System correctness
<!-- 2. What can you say about the resulting system after it has been verified with JPF? Is it proven to be correct? Why (not)? -->

## 3.2 Resource initialization

### 3.2.2 Partial model checking execution

## 3.3 Thread handling

### 3.3.3 partial JPF execution trace

## 3.4  Client-side verification

<!-- Write properties inChatClientto ensure that received message is always one of:
1. One of the messages sent by clients.
2. “Server busy”.

Show the resulting property violation (just the property, not the full trace); amend the property bytaking into account all cases. -->

## 3.5 Additional properties

### 3.5.1 Custom properties
<!-- 1. Can you come up with an additional meaningful (non-trivial) property? Write that property asan assertion. -->

### 3.5.2 Analysis
<!-- 2. How do you check if the different combinations of clients, worker threads serving clients, and messages between clients actually occur? Can you check this as an assertion? Hint: look at thelog/console output. -->