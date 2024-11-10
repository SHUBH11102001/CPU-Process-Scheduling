# CPU-Process-Scheduling

This Java project implements several popular **CPU scheduling algorithms** to simulate and compare the performance of different scheduling strategies. The algorithms included are **First-Come, First-Served (FCFS)**, **Shortest Job First (SJF)**, **Shortest Remaining Time First (SRTF)**, **Round Robin (RR)**, and **Non preemptive Priority Scheduling**. Each algorithm handles the scheduling of processes based on different principles, providing insight into their behavior and efficiency.

The application includes an intuitive **Graphical User Interface (GUI)** built using **Java Swing**. The interface is designed to allow easy input and interaction with scheduling processes, making the tool easily accessible.


## Algorithms Implemented

- **First-Come, First-Served (FCFS)**: Processes are executed in the order they arrive, with no preemption.
- **Shortest Job First (SJF)**: The process with the shortest burst time is selected for execution next, minimizing waiting time.
- **Shortest Remaining Time First (SRTF)**: A preemptive version of SJF, where the process with the shortest remaining burst time is executed next.
- **Round Robin (RR)**: Each process is assigned a fixed time slice or quantum and is executed in a cyclic order.
- **Priority Scheduling**: Processes are executed based on priority. The process with the highest priority is selected for execution first. If two processes have the same priority, the one that arrived first is executed first.
  
  
## How to Use

1. **Clone the repository**:
   ```bash
   git clone https://github.com/SHUBH11102001/CPU-Process-Scheduling.git
  
2.  **Navigate to the project directory:**:
    ```bash
    cd CPU-Process-Scheduling/src
     
3.  **Compile the Java files:**:
    ```bash
    javac *.java
    
4.   **Run the program**:
     ```bash
     java CPU_Scheduler
## License
This project is licensed under the MIT License - see the LICENSE.md file for details. 
## Contributions
Feel free to fork this repository and submit pull requests. You can also open issues if you encounter any problems or have suggestions for improvements.
      
## Contact
Created by `Shubh Nema`. Feel free to contact me at shubhnema617@gmail.com for any questions or feedback.
    
     

