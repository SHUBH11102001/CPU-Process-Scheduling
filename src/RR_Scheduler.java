import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RR_Scheduler {
    public void schedule(List<Process> processes, int quantum) {
        int currentTime = 0;
        Queue<Process> queue = new LinkedList<>();
        int[] remainingBurstTime = new int[processes.size()];
        boolean[] isInQueue = new boolean[processes.size()];

    
        for (int i = 0; i < processes.size(); i++) {
            remainingBurstTime[i] = processes.get(i).getBurstTime();
            isInQueue[i] = false;
        }

        int index = 0;
        while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
            queue.add(processes.get(index));
            isInQueue[index] = true;
            index++;
        }

        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();
            int i = processes.indexOf(currentProcess);

            if (currentTime == currentProcess.getArrivalTime()) {
                currentProcess.setWaitingTime(0);
            }

            int executeTime = Math.min(remainingBurstTime[i], quantum);
            currentTime += executeTime;
            remainingBurstTime[i] -= executeTime;

            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                if (!isInQueue[index]) {
                    queue.add(processes.get(index));  
                    isInQueue[index] = true;
                }
                index++;
            }

            if (remainingBurstTime[i] == 0) {
                currentProcess.setFinishTime(currentTime);

                currentProcess.setTurnaroundTime(currentProcess.getFinishTime() - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
            } else {
                queue.add(currentProcess);
            }

            // If the queue is empty and there are remaining processes, fast-forward the time to the next process
            if (queue.isEmpty() && index < processes.size()) {
                currentTime = processes.get(index).getArrivalTime();
                queue.add(processes.get(index));
                isInQueue[index] = true;
                index++;
            }
        }
    }
}



