import java.util.List;
import java.util.Comparator;

public class FCFS_Scheduler {
    public void schedule(List<Process> processes) {
        // Sort the processes by arrival time to ensure FCFS order
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        int currentTime = 0;
        for (Process process : processes) {
            // If the current time is less than the arrival time, move to the arrival time
            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }
            process.setWaitingTime(currentTime - process.getArrivalTime());
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
            currentTime += process.getBurstTime();
        }
    }
}


