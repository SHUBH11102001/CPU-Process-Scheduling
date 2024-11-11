import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class RR_Scheduler {
    private double throughput;
    private int cpuIdleTime;
    private double averageWaitingTime;
    private double averageTurnaroundTime;

    public void schedule(List<Process> processes, int quantum) {
        int currentTime = 0;
        int initialTime = processes.get(0).getArrivalTime();
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        cpuIdleTime = 0;

        Queue<Process> queue = new LinkedList<>();
        int[] remainingBurstTime = new int[processes.size()];
        boolean[] isInQueue = new boolean[processes.size()];

        // Initialize remaining burst time for each process
        for (int i = 0; i < processes.size(); i++) {
            remainingBurstTime[i] = processes.get(i).getBurstTime();
            isInQueue[i] = false;
        }

        int index = 0;
        // Enqueue processes that have arrived by the initial time
        while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
            queue.add(processes.get(index));
            isInQueue[index] = true;
            index++;
        }

        // Start the round-robin scheduling
        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();
            int i = processes.indexOf(currentProcess);

            // If it's the first time the process is being executed, set its waiting time
            if (currentTime == currentProcess.getArrivalTime()) {
                currentProcess.setWaitingTime(0);
            }

            // Calculate the time to execute the current process
            int executeTime = Math.min(remainingBurstTime[i], quantum);
            currentTime += executeTime;
            remainingBurstTime[i] -= executeTime;

            // Add any new arrivals to the queue before re-adding the current process
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                if (!isInQueue[index]) {
                    queue.add(processes.get(index));  // Add newly arrived processes
                    isInQueue[index] = true;
                }
                index++;
            }

            // If the process has completed
            if (remainingBurstTime[i] == 0) {
                // Set finish time for the process
                currentProcess.setFinishTime(currentTime);

                // Calculate turnaround and waiting times after completion
                currentProcess.setTurnaroundTime(currentProcess.getFinishTime() - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());

                totalWaitingTime += currentProcess.getWaitingTime();
                totalTurnaroundTime += currentProcess.getTurnaroundTime();
            } else {
                // Re-add process to the queue if it has remaining time
                queue.add(currentProcess);
            }

            // If the queue is empty and there are remaining processes, fast-forward the time to the next process
            if (queue.isEmpty() && index < processes.size()) {
                cpuIdleTime += processes.get(index).getArrivalTime() - currentTime;
                currentTime = processes.get(index).getArrivalTime();
                queue.add(processes.get(index));
                isInQueue[index] = true;
                index++;
            }
        }

        // Calculate averages and throughput
        averageWaitingTime = (double) totalWaitingTime / processes.size();
        averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        throughput = (double) processes.size() / (currentTime - initialTime);
    }

    public double getThroughput() {
        return throughput;
    }

    public int getCpuIdleTime() {
        return cpuIdleTime;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public double getAverageTurnaroundTime() {
        return averageTurnaroundTime;
    }
}



