import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CPU_Scheduler extends JFrame implements ActionListener {
    private JTextField numProcessesField, quantumField;
    private JTable inputTable, outputTable;
    private JComboBox<String> algorithmSelect;
    private JButton generateInputButton, calculateButton;
    private DefaultTableModel inputTableModel, outputTableModel;

    public CPU_Scheduler() {
        setTitle("CPU Scheduling Algorithm Simulator");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(new JLabel("Number of Processes:"));
        numProcessesField = new JTextField(5);
        topPanel.add(numProcessesField);

        topPanel.add(new JLabel("Time Quantum (RR):"));
        quantumField = new JTextField(5);
        quantumField.setEnabled(false);  // Only enabled for Round Robin
        topPanel.add(quantumField);

        topPanel.add(new JLabel("Select Algorithm:"));
        String[] algorithms = {"FCFS", "SJF", "Round Robin", "Priority Scheduling", "SRTF"};
        algorithmSelect = new JComboBox<>(algorithms);
        algorithmSelect.addActionListener(e -> {
            boolean isRoundRobin = algorithmSelect.getSelectedItem().equals("Round Robin");
            quantumField.setEnabled(isRoundRobin);
        });
        topPanel.add(algorithmSelect);

        // Button to generate rows in the input table based on the number of processes
        generateInputButton = new JButton("Generate Input Fields");
        generateInputButton.addActionListener(e -> generateInputFields());
        topPanel.add(generateInputButton);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        topPanel.add(calculateButton);

        add(topPanel, BorderLayout.NORTH);

        // Input table for Arrival and Burst times
        String[] inputColumns = {"Process ID", "Arrival Time", "Burst Time", "Priority"};
        inputTableModel = new DefaultTableModel(inputColumns, 0);
        inputTable = new JTable(inputTableModel);
        inputTable.setFillsViewportHeight(true);
        add(new JScrollPane(inputTable), BorderLayout.CENTER);

        // Output table for displaying results
        String[] outputColumns = {"Process ID", "Arrival Time", "Burst Time", "Priority", "Waiting Time", "Turnaround Time"};
        outputTableModel = new DefaultTableModel(outputColumns, 0);
        outputTable = new JTable(outputTableModel);
        outputTable.setFillsViewportHeight(true);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outputPanel.add(new JScrollPane(outputTable), BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);
    }

    // Method to generate input fields based on the number of processes
    private void generateInputFields() {
        try {
            int numProcesses = Integer.parseInt(numProcessesField.getText().trim());
            inputTableModel.setRowCount(0);  // Clear existing rows
            for (int i = 1; i <= numProcesses; i++) {
                inputTableModel.addRow(new Object[]{i, 0, 0, 0});
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of processes.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int numProcesses = Integer.parseInt(numProcessesField.getText().trim());

            List<Process> processes = new ArrayList<>();
            for (int i = 0; i < numProcesses; i++) {
                int processID = i + 1;
                int arrivalTime = Integer.parseInt(inputTableModel.getValueAt(i, 1).toString());
                int burstTime = Integer.parseInt(inputTableModel.getValueAt(i, 2).toString());
                int priority = Integer.parseInt(inputTableModel.getValueAt(i, 3).toString());
                processes.add(new Process(processID, arrivalTime, burstTime, priority));
            }

            String algorithm = (String) algorithmSelect.getSelectedItem();
            outputTableModel.setRowCount(0);  // Clear previous output

            switch (algorithm) {
                case "FCFS" -> {
                    FCFS_Scheduler fcfs = new FCFS_Scheduler();
                    fcfs.schedule(processes);
                }
                case "SJF" -> {
                    SJF_Scheduler sjf = new SJF_Scheduler();
                    sjf.schedule(processes);
                }
                case "Round Robin" -> {
                    int quantum = Integer.parseInt(quantumField.getText().trim());
                    RR_Scheduler rr = new RR_Scheduler();
                    rr.schedule(processes, quantum);
                }
                case "Priority Scheduling" -> {
                    PriorityScheduling_Scheduler priorityScheduler = new PriorityScheduling_Scheduler();
                    priorityScheduler.schedule(processes);
                }
                case "SRTF"  ->{
                    SRTF_Scheduler srtf = new SRTF_Scheduler();
                    srtf.schedule(processes);
                }
            }

            // Display results in output table
            for (Process process : processes) {
                outputTableModel.addRow(new Object[]{
                        process.getProcessID(),
                        process.getArrivalTime(),
                        process.getBurstTime(),
                        process.getPriority(),
                        process.getWaitingTime(),
                        process.getTurnaroundTime()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CPU_Scheduler gui = new CPU_Scheduler();
            gui.setVisible(true);
        });
    }
}
