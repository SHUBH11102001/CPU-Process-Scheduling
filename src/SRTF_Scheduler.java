import java.util.List;

public class SRTF_Scheduler {
    public void schedule(List<Process> processes) {
        int n = processes.size();
        int[] remainingTime = new int[n]; // Array to hold remaining burst times
        int complete = 0, time = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finishTime;
        boolean foundProcess = false;

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
            }
            time++;
        }
    }
}
