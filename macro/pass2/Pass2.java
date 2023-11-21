import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Pass2 {
    public static void main(String[] args) {
        try {
            // Input and Output file names
            String mntFile = "mnt.txt";
            String mdtFile = "mdt.txt";
            String alaFile = "ala.txt";
            String output2File = "output2.txt";
            String inputCodeFile = "output.txt";
            BufferedReader mntReader = new BufferedReader(new FileReader(mntFile));
            BufferedReader mdtReader = new BufferedReader(new FileReader(mdtFile));
            BufferedReader alaReader = new BufferedReader(new FileReader(alaFile));
            BufferedWriter output2Writer = new BufferedWriter(new FileWriter(output2File));
            BufferedReader inputCodeReader = new BufferedReader(new FileReader(inputCodeFile));
            Map<String, List<String>> alaTable = new HashMap<>();
            Map<String, String> macroNameMap = new HashMap<>();
            String line;
            // Read ALA (Argument List Array)
            while ((line = alaReader.readLine()) != null) {
                String parts[] = line.split("\\s+");
                if (parts.length >= 2) {
                    String parameterName = parts[1];
                    String parameterValue = parts[0];
                    alaTable.computeIfAbsent(parameterName, k -> new ArrayList<>()).add(parameterValue);
                }
            }
            // Read MNT (Macro Name Table)
            while ((line = mntReader.readLine()) != null) {
                String parts[] = line.split("\\t");
                if (parts.length >= 3) {
                    String macroName = parts[1];
                    int mdtStartIndex = Integer.parseInt(parts[2]);
                    StringBuilder macroDefinition = new StringBuilder();
                    while (true) {
                        line = mdtReader.readLine();
                        if (line == null) {
                            break; // End of file
                        }
                        List<String> macroLine = Arrays.asList(line.split("\\s+"));
                        if (macroLine.get(0).equals("MEND")) {
                            break; // End of macro definition
                        }
                        macroDefinition.append(line).append(" ");
                    }
                    macroNameMap.put(macroName, macroDefinition.toString());
                }
            }
            // Read and process the input code
            while ((line = inputCodeReader.readLine()) != null) {
                String parts[] = line.split("\\s+");
                if (macroNameMap.containsKey(parts[0])) {
                    // This line contains a macro call
                    String macroName = parts[0];
                    String macroCall = line.substring(macroName.length()).trim();
                    // Replace macro parameters with actual arguments
                    String macroDefinition = macroNameMap.get(macroName);
                    for (Map.Entry<String, List<String>> entry : alaTable.entrySet()) {
                        String parameter = entry.getKey();
                        List<String> arguments = entry.getValue();
                        for (String argument : arguments) {
                            macroDefinition = macroDefinition.replaceAll(Pattern.quote(parameter), argument);
                        }
                    }
                    // Write the expanded macro to output2.txt
                    String[] expandedLines = macroDefinition.split("\\s+");
                    for (String expandedLine : expandedLines) {
                        output2Writer.write(expandedLine);
                        output2Writer.newLine();
                    }
                } else {
                    // This line is not a macro call, write it as is
                    output2Writer.write(line);
                    output2Writer.newLine();
                }
            }
            // Close the file readers and writer
            mntReader.close();
            alaReader.close();
            inputCodeReader.close();
            output2Writer.close();
            // Output a message to indicate the operation is complete
            System.out.println("Pass 2 completed successfully. Check output2.txt for the expanded code.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
