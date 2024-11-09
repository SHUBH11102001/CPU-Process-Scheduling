import java.util.List;

public class SJF_Scheduler {
    public void schedule(List<Process> processes) {
        int currentTime = 0;
        int completed = 0;
        int n = processes.size();
        boolean[] isCompleted = new boolean[n];        
        while (completed < n) {
 
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
                   
                        idx = i;
                    }
                }
            }

   
            if (idx == -1) {
                currentTime++;
            } else {
                Process process = processes.get(idx);
                process.setWaitingTime(currentTime - process.getArrivalTime());
                process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());

                isCompleted[idx] = true;
                completed++;

                currentTime += process.getBurstTime();
            }
        }
    }
}
