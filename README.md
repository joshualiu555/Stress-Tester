# Stress Tester

This Java application is designed to facilitate stress testing for coding solutions by comparing the output of a user-written solution against a brute-force solution using generated test cases. The application leverages JavaFX for a graphical user interface, allowing users to input their code directly into text areas.

## Features

- **Test Case Generation:** Automatically generates test cases using user-provided code.
- **Solution Testing:** Runs both the user's solution and a brute-force solution on the generated test cases.
- **Output Comparison:** Compares the outputs of the two solutions to verify correctness.
- **User Interface:** Simple and interactive UI to input code and view results.

## How It Works

### Components

- **Text Areas:** Users input their test case generator, solution, and brute-force solution code into the corresponding `TextArea` fields.
- **Compilation:** The provided code is compiled at runtime using the Java Compiler API.
- **Execution:** The test case generator code is executed to produce input data, which is then fed into both the solution and brute-force solution for comparison.
- **Output:** The application displays whether the solution passed or failed the test case. In the event of a failure, it shows the test case input, the user's solution output, and the expected output.

### Detailed Flow

1. **Input Handling:** 
   - The application captures the code from the `TextArea` fields for the test case generator, solution, and brute-force solution.

2. **File Writing:**
   - The input code is written to corresponding `.java` files: `Generator.java`, `Solution.java`, and `BruteForce.java`.

3. **Compilation:**
   - The Java files are compiled using the `JavaCompiler` class.

4. **Execution:**
   - A separate thread is created to handle the execution and comparison process.
   - The test case generator (`Generator.java`) is executed, and its output is captured.
   - This output is then fed into both the solution (`Solution.java`) and brute-force solution (`BruteForce.java`).
   - The outputs of both solutions are compared:
     - If they match, the test case is marked as passed.
     - If they differ, the application stops and displays the failed test case details, including the input and both outputs.

5. **Output Display:**
   - The result of each test case is displayed in the `outputTextArea` field.

### Thread Management

- The application uses a separate thread (`runningThread`) to manage the execution of test cases, allowing it to handle multiple cases in sequence without freezing the UI.
- The thread is set as a daemon, ensuring it doesn't prevent the application from shutting down.

## How to Use

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/stress-tester.git
   ```
2. **Open the Project:**
   - Open the project in your preferred Java IDE.
3. **Run the Application:**
   - Run the main application and input your test case generator, solution, and brute-force solution in the respective text areas.
4. **Start Testing:**
   - Click the "Start" button to begin the stress testing process.
   - View the results in the output area.

## Requirements

- Java 8 or higher
- JavaFX library

## Contributing

Feel free to submit issues or pull requests to contribute to this project.

## License

This project is licensed under the MIT License.

<img width="757" alt="Screenshot 2024-08-11 at 11 55 29 AM" src="https://github.com/user-attachments/assets/c6551fa4-771e-4551-aafc-fb42d67403f5">


