package studybuddy;

import java.util.Scanner;

import studybuddy.data.course.Course;
import studybuddy.data.course.CourseList;
import studybuddy.commands.Command;
import studybuddy.data.io.Parser;
import studybuddy.data.io.Ui;
import studybuddy.data.storage.CommandHistoryManager;
import studybuddy.data.storage.StorageManager;

public class CEGStudyBuddy {
    public static CourseList courses;
    private static boolean isRunning = true;
    private static final Scanner scanner = new Scanner(System.in);
    private static final StorageManager storage = new StorageManager("./PlanData", scanner);
    private static final Ui ui = new Ui(scanner);

    public static void main(String[] args) {
        ui.showWelcome();
        storage.initializePlan();
        Course.dummyInitialiseCheck(courses);
        ui.printLine();
        ui.showEnterCommand();

        while (isRunning && scanner.hasNextLine()) {
            String[] userInput = ui.readInput();
            ui.printLine();
            // full input line
            String fullCommand = String.join(" ", userInput).trim();

            // Skip logging if it's the summary command
            boolean isSummary = fullCommand.toLowerCase().startsWith("summary");

            try {
                Command c = Parser.parseCommand(userInput);
                String output = c.execute(courses, storage);
                ui.showMessage(output);
                storage.autoSave();

                if (!isSummary) {
                    CommandHistoryManager.record(fullCommand);
                }

                isRunning = c.isRunning();
                if (isRunning) {
                    ui.printLine();
                    ui.showEnterCommand();
                }

            } catch (Exception e) {
                if (!isSummary) {
                    CommandHistoryManager.record(fullCommand);
                }
                ui.showMessage(e.getMessage());
                ui.printLine();
                ui.showEnterCommand();
            }
        }
        scanner.close();
    }
}
