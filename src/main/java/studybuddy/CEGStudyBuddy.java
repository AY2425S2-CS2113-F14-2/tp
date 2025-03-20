package studybuddy;

import java.util.ArrayList;
import java.util.Scanner;

import studybuddy.commands.Command;
import studybuddy.commands.HelpCommand;
import studybuddy.commands.InvalidCommand;
import studybuddy.course.Course;

public class CEGStudyBuddy {
    public static ArrayList<Course> courses = new ArrayList<>();
    public static boolean isRunning = true;
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (isRunning) {
            String[] userInput = readInput();
            try {
                Command c = parseCommand(userInput);
                System.out.println(c.execute());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Missing Parameters");
            }
        }
    }

    static void exitProgram() {
        System.out.println("Bye");
        isRunning = false;
        System.exit(0);
    }

    static String[] readInput() {
        String userInput = in.nextLine();
        return userInput.split(" ", 2);
    }

    static Command parseCommand(String[] inputParts) throws IndexOutOfBoundsException {
        Command c = null;
        switch (inputParts[0]) {
        case "help" -> c = new HelpCommand(inputParts[0]);
        case "exit" -> exitProgram();
        default -> c = new InvalidCommand(inputParts[0]);
        }
        return c;
    }
}
