import java.util.List;

public class SRTF_Scheduler {
    private double throughput;
    private int cpuIdleTime;
    private double averageWaitingTime;
    private double averageTurnaroundTime;

    public void schedule(List<Process> processes) {
        int n = processes.size();
        int[] remainingTime = new int[n]; // Array to hold remaining burst times
        int complete = 0, time = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finishTime;
        boolean foundProcess = false;
        int initialTime = processes.get(0).getArrivalTime();
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        cpuIdleTime = 0;

        // Initialize remaining times from burst times
        for (int i = 0; i < n; i++) {
            remainingTime[i] = processes.get(i).getBurstTime();
        }

        while (complete != n) {
            // Find process with minimum remaining time among the processes
            // that have arrived and are not yet completed
            for (int i = 0; i < n; i++) {
                if ((processes.get(i).getArrivalTime() <= time) &&
                    (remainingTime[i] < minm) && remainingTime[i] > 0) {
                    minm = remainingTime[i];
                    shortest = i;
                    foundProcess = true;
                }
            }

            if (!foundProcess) {
                cpuIdleTime++; // Increment CPU idle time if no process is ready
                time++; // Increment time if no process is ready to execute
                continue;
            }

            // Decrease the remaining time of the shortest process
            remainingTime[shortest]--;
            minm = remainingTime[shortest];

            // Reset minm if the remaining time becomes zero
            if (minm == 0) {
                minm = Integer.MAX_VALUE;
            }

            // If a process finishes executing
            if (remainingTime[shortest] == 0) {
                complete++;
                foundProcess = false;

                finishTime = time + 1;
                processes.get(shortest).setFinishTime(finishTime);

                // Calculate waiting time for the finished process
                int waitingTime = finishTime - processes.get(shortest).getBurstTime() -
                                  processes.get(shortest).getArrivalTime();
                if (waitingTime < 0) {
                    waitingTime = 0;
                }
                processes.get(shortest).setWaitingTime(waitingTime);

                // Calculate turnaround time
                int turnaroundTime = processes.get(shortest).getBurstTime() + waitingTime;
                processes.get(shortest).setTurnaroundTime(turnaroundTime);

                // Accumulate total waiting and turnaround times
                totalWaitingTime += waitingTime;
                totalTurnaroundTime += turnaroundTime;
            }
            time++;
        }

        // Calculate averages and throughput
        averageWaitingTime = (double) totalWaitingTime / n;
        averageTurnaroundTime = (double) totalTurnaroundTime / n;
        throughput = (double) n / (time - initialTime);
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
