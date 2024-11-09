import java.util.List;

public class SJF_Scheduler {
    public void schedule(List<Process> processes) {
        int currentTime = 0;
        int completed = 0;
        int n = processes.size();
        boolean[] isCompleted = new boolean[n];

        // Loop until all processes are completed
        while (completed < n) {
            // Find the process with the shortest burst time that has arrived
            int idx = -1;
            int minBurstTime = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                Process process = processes.get(i);
                if (!isCompleted[i] && process.getArrivalTime() <= currentTime) {
                    if (process.getBurstTime() < minBurstTime) {
                        minBurstTime = process.getBurstTime();
                        idx = i;
                    } else if (process.getBurstTime() == minBurstTime
                            && process.getArrivalTime() < processes.get(idx).getArrivalTime()) {
                        // If burst times are equal, pick the one that arrived first
                        idx = i;
                    }
                }
            }

            // If no process is ready to execute, move time forward
            if (idx == -1) {
                currentTime++;
            } else {
                Process process = processes.get(idx);
                process.setWaitingTime(currentTime - process.getArrivalTime());
                process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());

                // Mark the process as completed
                isCompleted[idx] = true;
                completed++;

                // Update current time by adding the burst time of the current process
                currentTime += process.getBurstTime();
            }
        }
    }
}
