package studybuddy;

import java.util.Scanner;
import studybuddy.course.CourseList;
import studybuddy.commands.CEGStudyBuddyException;
import studybuddy.commands.Command;
import studybuddy.commands.AddCommand;
import studybuddy.commands.EditCommand;
import studybuddy.commands.HelpCommand;
import studybuddy.commands.DeleteCourse;
import studybuddy.commands.InvalidCommand;
import studybuddy.commands.WorkloadForCommand;
import studybuddy.commands.ListCommand;
import studybuddy.commands.WorkloadBalanceCommand;
import studybuddy.commands.WorkloadSummaryCommand;
import studybuddy.commands.FindCommand;
import studybuddy.commands.GradRequirementCommand;
import studybuddy.commands.SwitchPlanCommand;
import studybuddy.commands.SavePlanCommand;
import studybuddy.commands.ExitCommand;

public class CEGStudyBuddy {
    public static CourseList courses;// Global course list
    public static boolean isRunning = true;
    public static Scanner in = new Scanner(System.in);
    public static StorageManager storage = new StorageManager("./PlanData");

    public static void main(String[] args) {
        System.out.println("Welcome to CEGStudyBuddy!");
        storage.initializePlan();
        while (isRunning) {
            System.out.print("Enter command: ");
            String[] userInput = readInput();
            try {
                Command c = parseCommand(userInput);
                System.out.println(c.execute());
                isRunning = c.isRunning();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Reads the input and parses it into a String array.
     *
     * @return The String array containing the command and parameters.
     */
    static String[] readInput() {
        String userInput = in.nextLine();
        return userInput.split(" ", 2);
    }

    /**
     * Parses the input into a command and returns the Command object for the command.
     *
     * @param inputParts Input String array containing the command and parameters.
     * @return The Command object corresponding to the input.
     * @throws IndexOutOfBoundsException If parameters are not included (inputParts.len() = 1).
     */
    static Command parseCommand(String[] inputParts) throws CEGStudyBuddyException {
        Command c = null;
        try {
            switch (inputParts[0]) {
            case CommandNames.SAVE -> c = new SavePlanCommand();
            case CommandNames.SWITCH_PLAN -> c = new SwitchPlanCommand();
            case CommandNames.ADD -> c = new AddCommand(inputParts[1]);
            case CommandNames.EDIT -> c = new EditCommand(inputParts[1]);
            case CommandNames.WORKLOAD_SUMMARY -> c = new WorkloadSummaryCommand("", courses.getCourses());
            case CommandNames.HELP -> c = new HelpCommand();
            case CommandNames.EXIT -> c = new ExitCommand();
            case CommandNames.WORKLOAD_FOR -> c = new WorkloadForCommand(inputParts[1]);
            case CommandNames.WORKLOAD_BALANCE -> c = new WorkloadBalanceCommand();
            case CommandNames.DELETE -> c = new DeleteCourse(inputParts[1]);
            case CommandNames.LIST -> c = new ListCommand();
            case CommandNames.FIND -> c = new FindCommand(inputParts[1]);
            case CommandNames.GRADREQ -> c = new GradRequirementCommand();
            default -> c = new InvalidCommand();
            }
        } catch (Exception e) {
            throw new CEGStudyBuddyException(e.getMessage());
        }
        return c;
    }
}
