package com.example.stresstester;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;

public class StressTesterController {
    @FXML
    private TextArea solutionTextArea, bruteForceTextArea, testCaseGeneratorTextArea, outputTextArea;

    String testCaseGeneratorCode, solutionCode, bruteForceCode;

    private volatile Boolean running = false;
    public void start() throws IOException {
        testCaseGeneratorCode = testCaseGeneratorTextArea.getText();
        solutionCode = solutionTextArea.getText();
        bruteForceCode = bruteForceTextArea.getText();

        // Write to files
        writeToFile("src/Generator.java", testCaseGeneratorCode);
        writeToFile("src/Solution.java", solutionCode);
        writeToFile("src/BruteForce.java", bruteForceCode);

        // Compile files
        compileFile("src/Generator.java");
        compileFile("src/Solution.java");
        compileFile("src/BruteForce.java");

        running = true;
        Thread runningThread = getRunningThread();
        runningThread.start();
    }

    private Thread getRunningThread() {
        Thread runningThread = new Thread(() -> {
            int testCaseNumber = 1;
            while (running) {
                // Get output from Generator.java
                ProcessBuilder generatorProcessBuilder = new ProcessBuilder("java", "-cp", "src", "Generator");
                try {
                    Process generatorProcess = generatorProcessBuilder.start();
                    StringBuilder generatorOutput = createOutput(generatorProcess);
                    // Run Solution.java and BruteForce.java with Generator.java output as input
                    StringBuilder solutionOutput = runTestCase("Solution", generatorOutput);
                    StringBuilder bruteForceOutput = runTestCase("BruteForce", generatorOutput);

                    if (solutionOutput.toString().contentEquals(bruteForceOutput)) {
                        outputTextArea.setText("Test Case " + testCaseNumber + " passed");
                    } else {
                        outputTextArea.setText("Failed\n" +
                                                "Test Case:\n" + generatorOutput + "\n" +
                                                "Your Solution:\n" + solutionOutput + "\n" +
                                                "Correct Solution:\n" + bruteForceOutput + "\n");
                        stop();
                    }

                    testCaseNumber++;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        runningThread.setDaemon(true);
        return runningThread;
    }

    public void stop() {
        running = false;
    }

    private static StringBuilder createOutput(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return output;
    }

    private static StringBuilder runTestCase(String className, StringBuilder generatorOutput) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", "src", className);
        Process process = processBuilder.start();
        OutputStream outputStream = process.getOutputStream();
        outputStream.write(generatorOutput.toString().getBytes());
        outputStream.close();
        return createOutput(process);
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(content);
        writer.close();
    }

    private static void compileFile(String fileName) {
        JavaCompiler generatorCompiler = ToolProvider.getSystemJavaCompiler();
        File javaFile = new File(fileName);
        generatorCompiler.run(null, null, null, javaFile.getPath());
    }
}