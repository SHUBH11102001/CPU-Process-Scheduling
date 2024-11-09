import java.util.List;

public class SRTF_Scheduler {
    public void schedule(List<Process> processes) {
        int n = processes.size();
        int[] remainingTime = new int[n]; 
        int complete = 0, time = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finishTime;
        boolean foundProcess = false;

  
        for (int i = 0; i < n; i++) {
            remainingTime[i] = processes.get(i).getBurstTime();
        }

        while (complete != n) {

            for (int i = 0; i < n; i++) {
                if ((processes.get(i).getArrivalTime() <= time) &&
                    (remainingTime[i] < minm) && remainingTime[i] > 0) {
                    minm = remainingTime[i];
                    shortest = i;
                    foundProcess = true;
                }
            }

            if (!foundProcess) {
                time++; 
                continue;
            }

         
            remainingTime[shortest]--;
            minm = remainingTime[shortest];

            if (minm == 0) {
                minm = Integer.MAX_VALUE;
            }

            if (remainingTime[shortest] == 0) {
                complete++;
                foundProcess = false;

                finishTime = time + 1;
                processes.get(shortest).setFinishTime(finishTime);

                int waitingTime = finishTime - processes.get(shortest).getBurstTime() -
                                  processes.get(shortest).getArrivalTime();
                if (waitingTime < 0) {
                    waitingTime = 0;
                }
                processes.get(shortest).setWaitingTime(waitingTime);

                int turnaroundTime = processes.get(shortest).getBurstTime() + waitingTime;
                processes.get(shortest).setTurnaroundTime(turnaroundTime);
            }
            time++;
        }
    }
}
