import java.util.List;
import java.util.Comparator;

public class PriorityScheduling_Scheduler {

    public void schedule(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getPriority).reversed());

        int currentTime = 0;

        for (Process process : processes) {
            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }

            process.setWaitingTime(currentTime - process.getArrivalTime());

            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
            currentTime += process.getBurstTime();
        }
    }
}
