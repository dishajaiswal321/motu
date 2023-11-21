import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
class Process {
 int processId;
 int burstTime;
 int priority;
 public Process(int processId, int burstTime, int priority) {
 this.processId = processId;
 this.burstTime = burstTime;
 this.priority = priority;
 }
}
public class priority {
 public static void main(String[] args) {
 Scanner scanner = new Scanner(System.in);
 ArrayList<Process> processes = new ArrayList<>();
 System.out.print("Enter the number of processes: ");
 int n = scanner.nextInt();
 for (int i = 1; i <= n; i++) {
 System.out.print("Enter burst time for Process " + i + ": ");
 int burstTime = scanner.nextInt();
 System.out.print("Enter priority for Process " + i + ": ");
 int priority = scanner.nextInt();
 processes.add(new Process(i, burstTime, priority));
 }
 // Sort processes based on priority (higher priority comes first)
 Collections.sort(processes, (p1, p2) -> p2.priority - p1.priority);
 int waitingTime = 0;
 int turnaroundTime;
 System.out.println("\nProcess ID\tBurst Time\tPriority\tWaiting Time\tTurnaround Time");
 for (Process process : processes) {
 turnaroundTime = waitingTime + process.burstTime;
 System.out.println(process.processId + "\t\t" + process.burstTime + "\t\t" + process.priority +
"\t\t"
 + waitingTime + "\t\t" + turnaroundTime);
 waitingTime += process.burstTime;
 }
 scanner.close();
 }
}