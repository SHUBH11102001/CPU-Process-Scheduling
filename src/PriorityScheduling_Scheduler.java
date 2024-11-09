import java.util.List;
import java.util.Comparator;

public class PriorityScheduling_Scheduler {

    public void schedule(List<Process> processes) {
        // Sort the processes by priority (higher priority first)
        processes.sort(Comparator.comparingInt(Process::getPriority).reversed());

        int currentTime = 0;

        for (Process process : processes) {
            // If the current time is less than the arrival time, move to the arrival time
            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }

            // Calculate waiting time as the difference between current time and arrival time
            process.setWaitingTime(currentTime - process.getArrivalTime());

            // Calculate turnaround time as waiting time plus burst time
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());

            // Update current time by adding the burst time of the current process
            currentTime += process.getBurstTime();
        }
    }
}
