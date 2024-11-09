import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;

public class PriorityScheduling_Scheduler {

    public static void schedule(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator
            .comparingInt(Process::getPriority)  
            .thenComparingInt(Process::getArrivalTime)); 

        int currentTime = 0;
        int index = 0;

        while (index < processes.size() || !readyQueue.isEmpty()) {
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                readyQueue.offer(processes.get(index));
                index++;
            }

            if (readyQueue.isEmpty()) {
                currentTime = processes.get(index).getArrivalTime();
            } else {
                Process currentProcess = readyQueue.poll();
                currentProcess.setStarted(true);
                currentProcess.setWaitingTime(currentTime - currentProcess.getArrivalTime());

                currentTime += currentProcess.getBurstTime();
                currentProcess.setTurnaroundTime(currentProcess.getWaitingTime() + currentProcess.getBurstTime());
            }
        }}}
