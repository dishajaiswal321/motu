import java.util.ArrayList;
import java.util.Scanner;
public class OptimalPageReplacement {
 public static void main(String[] args) {
 Scanner scanner = new Scanner(System.in);
 System.out.print("Enter the number of frames: ");
 int numFrames = scanner.nextInt();
 System.out.print("Enter the number of page references: ");
 int numReferences = scanner.nextInt();
 System.out.print("Enter the page reference string, e.g., 1 2 3 4: ");
 scanner.nextLine(); // Consume the newline
 String input = scanner.nextLine();
 String[] referenceString = input.split(" ");
 if (referenceString.length != numReferences) {
 System.out.println("Number of references does not match the input string length.");
 return;
 }
 ArrayList<Integer> frames = new ArrayList<>();
 int pageFaults = 0;
 for (int i = 0; i < numReferences; i++) {
 int page = Integer.parseInt(referenceString[i]);
 if (!frames.contains(page)) {
 pageFaults++;
 if (frames.size() < numFrames) {
 frames.add(page);
 } else {
 int index = predictOptimal(frames, referenceString, i);
 frames.set(index, page);
 }
 }
 }
 System.out.println("Number of page faults: " + pageFaults);
 scanner.close();
 }
 private static int predictOptimal(ArrayList<Integer> frames, String[] referenceString, int
currentIndex) {
 int farthest = -1;
 int index = -1;
 for (int i = 0; i < frames.size(); i++) {
 int frame = frames.get(i);
 int j;
 for (j = currentIndex + 1; j < referenceString.length; j++) {
 if (frame == Integer.parseInt(referenceString[j])) {
 if (j > farthest) {
 farthest = j;
 index = i;
 }
 break;
 }
 }
 if (j == referenceString.length) {
 return i;
 }
 }
 return (index == -1) ? 0 : index;
 }
}
