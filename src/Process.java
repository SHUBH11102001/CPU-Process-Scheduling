public class Process {
    private int processID;
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int turnaroundTime;
    private int finishTime;  // Add finishTime to store the finish time of each process
    private int priority;
    private int responseTime = -1;  // New field for storing response time when process starts executing
    private boolean isStarted;

    



    public Process(int processID, int arrivalTime, int burstTime, int priority) {
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.isStarted = false;


    }

    public int getProcessID() { return processID; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
    public int getWaitingTime() { return waitingTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public int getFinishTime() { return finishTime; }  // Getter for finishTime
    public int getPriority() {
        return priority;
    }
    public int getResponseTime() { return responseTime; }  // Getter for responseTime
    public boolean isStarted() { return isStarted; }




    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }
    public void setFinishTime(int finishTime) { this.finishTime = finishTime; }  // Setter for finishTime
    public void setResponseTime(int responseTime) { this.responseTime = responseTime; }  // Setter for responseTime
    public void setStarted(boolean started) { isStarted = started; }



    
    // Add a setter for burstTime to update the remaining burst time
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    @Override
    public String toString() {
        return "Process [ID=" + processID + ", Arrival Time=" + arrivalTime + ", Burst Time=" + burstTime +
                ", Waiting Time=" + waitingTime + ", Turnaround Time=" + turnaroundTime + "]";
    }
}

