package studybuddy.data.io;

import java.util.ArrayList;
import java.util.Scanner;

import studybuddy.commands.AddCommand;
import studybuddy.commands.DeleteCourse;
import studybuddy.commands.EditCommand;
import studybuddy.commands.ExitCommand;
import studybuddy.commands.FindCommand;
import studybuddy.commands.GradRequirementCommand;
import studybuddy.commands.HelpCommand;
import studybuddy.commands.ListCommand;
import studybuddy.commands.SavePlanCommand;
import studybuddy.commands.SwitchPlanCommand;
import studybuddy.commands.WorkloadBalanceCommand;
import studybuddy.commands.WorkloadForCommand;
import studybuddy.commands.WorkloadSummaryCommand;
import studybuddy.common.Utils;
import studybuddy.data.course.Course;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public Ui(Scanner scanner) {
        this.scanner = scanner;
    }

    public String showCourseReplacedMessage(String oldCode, String newCode) {
        return "Course \"" + oldCode + "\" has been successfully replaced with \"" + newCode + "\".";
    }

    public String showCourseNotFoundInReplaceMessage(String code) {
        return "Could not find a course with the code \"" + code + "\" in your plan.";
    }

    public String printFindCourse(ArrayList<Course> courseList, String targetCode) {
        for (Course course : courseList) {
            if (course.getCode().equalsIgnoreCase(targetCode)) {
                return "Course Code: " + course.getCode() + "\n"
                        + "Course Title: " + course.getTitle() + "\n"
                        + "Number of MCs: " + course.getMc() + "\n"
                        + "Year and Sem: Y" + course.getTakeInYear() + "S" + course.getTakeInSem();
            }
        }

        return "Course " + targetCode + " not found in your course list.";
    }

    public String printGradReq(int completedMCs) {
        StringBuilder sb = new StringBuilder();
        int remaining = Utils.GRADUATION_MCS - completedMCs;

        sb.append("Current MCs Completed: ").append(completedMCs).append(" MCs\n");
        sb.append("Graduation Requirement: ").append(Utils.GRADUATION_MCS).append(" MCs\n");
        sb.append("Remaining MCs: ").append(remaining).append(" MCs\n\n");

        if (remaining > 0) {
            sb.append("Oh no! You don't meet graduation requirement yet, you need to finish ")
                    .append(remaining).append(" more units of courses in order to graduate\n");
            sb.append("Keep on going Champ! You got this!\n");
            // Thumbs up made of hashtags
            sb.append("      ####\n");
            sb.append("     ######\n");
            sb.append("     ######\n");
            sb.append("     ######\n");
            sb.append("     ######\n");
            sb.append("     ######\n");
            sb.append("   ################\n");
            sb.append("   ################\n");
            sb.append("   ################\n");
            sb.append("   ################\n");
            sb.append("   ################\n");
            sb.append("   ################\n");
            sb.append("    ##############\n");
        } else {
            sb.append("Congratulations! You have met the graduation requirement!");
            // Your uploaded graduation ASCII art
            sb.append("     ##                             \n");
            sb.append("                           #######                          \n");
            sb.append("                        #############                       \n");
            sb.append("                         ###########                        \n");
            sb.append("                           #######                          \n");
            sb.append("                           ####### #                        \n");
            sb.append("                           ######  #                        \n");
            sb.append("                           ######  #                        \n");
            sb.append("                           ######  #                        \n");
            sb.append("                           ######  #                        \n");
            sb.append("                            ####                             \n");
            sb.append("                            ####                             \n");
            sb.append("                            ####                             \n");
            sb.append("                         ### ##  ###                         \n");
            sb.append("                       ####  # ######                       \n");
            sb.append("                      ####### ########                      \n");
            sb.append("                     ##################                     \n");
            sb.append("                     ###################                    \n");
            sb.append("                     ###################                    \n");
            sb.append("                    ####################                    \n");
            sb.append("                    ####################                    \n");
            sb.append("                    #####################                   \n");
            sb.append("                    #####################                   \n");
            sb.append("                    #####################                   \n");
            sb.append("                    #####################                   \n");
        }
        return sb.toString();
    }

    public String printCommandList() {
        return "List of Commands:" + System.lineSeparator() +
                AddCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                DeleteCourse.COMMAND_DESCRIPTION + System.lineSeparator() +
                ListCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                EditCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                FindCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                WorkloadSummaryCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                WorkloadForCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                WorkloadBalanceCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                GradRequirementCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                SavePlanCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                SwitchPlanCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                HelpCommand.COMMAND_DESCRIPTION + System.lineSeparator() +
                ExitCommand.COMMAND_DESCRIPTION;
    }

    /**
     * Checks through conditions to make sure there is next line from scanner, and next line is not null.
     * This method should be called when ever using scanner.nextLine() to read an input, to prevent
     * crashing from Ctrl+Z input.
     *
     * @return The String from next line input of scanner.
     */
    public String robustNextLine() {
        if (!scanner.hasNextLine()) {
            System.out.println("Input stream is closed. CEGStudyBuddy has to exit, sorry!");
            System.exit(0);
        }
        String str = scanner.nextLine();
        if (str == null) {
            System.out.println("Null input is detected. CEGStudyBuddy has to exit, sorry!");
            System.exit(0);
        }
        return str.trim();
    }

    /**
     * Reads the input and parses it into a String array. Make sure there is a next line before calling.
     *
     * @return The String array containing the command and parameters.
     */
    public String[] readInput() {
        String userInput = scanner.nextLine().trim();
        return userInput.split(" ", 2);
    }

    // Displays the welcome logo and greeting
    public void showWelcome() {
        String banner =
                """             
                        __________  ___________ ________
                        \\_   ___ \\\\_   _____//  _____/
                        /    \\  \\/ |    __)_/   \\  ___
                        \\     \\____|        \\    \\_\\  \\
                         \\______  /_______  /\\______  /
                                \\/        \\/        \\/
                
                          ________________________ ___________ _____.___. 
                         /   _____/\\__    ___/    |   \\______ \\\\__  |   | 
                         \\_____  \\   |    |  |    |   /|    |  \\/   |   | 
                         /        \\  |    |  |    |  / |    `   \\____   | 
                        /_______  /  |____|  |______/ /_______  / ______| 
                                \\/                            \\/\\/        
                
                        __________ ____ ___________  ________ _____.___.  
                        \\______   \\    |   \\______ \\ \\______ \\\\__  |   |  
                         |    |  _/    |   /|    |  \\ |    |  \\/   |   |  
                         |    |   \\    |  / |    `   \\|    `   \\____   |  
                         |______  /______/ /_______  /_______  / ______|  
                                \\/                 \\/        \\/\\/        
                
                                      Welcome to CEGStudyBuddy!
                            Type 'help' to see a list of available commands.
                """;

        System.out.println(banner);
    }


    // Prints help hint
    public void showHelpHint() {
        System.out.println("Type 'help' to see the list of commands.");
    }

    // Displays generic message
    public void showMessage(String message) {
        System.out.println(message);
    }

    // Displays error message with prefix
    public void showError(String error) {
        System.out.println(error);
    }
    public void showEnterCommand() {
        System.out.print("Enter command: ");
    }

    public void showSaveMessage(String message) {
        System.out.println(message);
    }

    public String newPlanInput() {
        System.out.print("Please enter a plan name \nNo special characters are allowed, only alphanumeric input: ");
        return robustNextLine();
    }

    /**
     * Method to print plan creation message
     */
    public void createNewPlanMessage() {
        System.out.println("New plan has been created");
    }

    /**
     * Method to print no previous plans
     */

    public void noPreviousPlansMessage() {
        System.out.println("You have no previous plans");
    }

    /**
     *  Method to help user choose a plan or choose creating a new one
     * @param plans list of string of plan names
     * @return
     */
    public String chooseOrCreateNewPlans(String[] plans) {
        for (int i = 0; i < plans.length; i++) {
            System.out.println((i + 1) + ". " + plans[i]);
        }
        System.out.print("Please enter a plan number between 1 and " + plans.length + " or 0 to create a new plan: ");
        return robustNextLine();
    }

    public void planSuccessfullyLoadedMessage() {
        System.out.println("Plan loaded successfully");
    }

    public void printCourse(Course c) {
        System.out.println(c);
    }

    /**
     * Method to allow user to choose a plan to delete
     * @param plans list of string of plan names
     * @return
     */
    public String chooseDeletePlan(String[] plans) {
        for (int i = 0; i < plans.length; i++) {
            System.out.println((i + 1) + ". " + plans[i]);
        }
        System.out.print("Please enter a plan number between 1 and " + plans.length + " : ");
        return robustNextLine();
    }

    /**
     * Method to print plan deletion message
     */
    public void displaySuccessfullyDeletedMessage() {
        System.out.println("Plan deleted successfully");
    }

    public String editSuccessMessage() {
        return "Course is successfully edited";
    }

    public String courseNotInPlannerMessage() {
        return "This course cannot be found inside the planner";
    }

    public String indexOutOfBoundErrorMessage() {
        return "Input is not a valid index, pleasae check your input";
    }

    public String parseIntErrorMessage() {
        return "Input cannot be converted to an valid number, please check your input";
    }

    public String missingCodeErrorMessage() {
        return "Course code is missing, please ensure there is a course code in your input";
    }

    public void showMissingDefinedListMessage() {
        System.out.println("The list of defined courses cannot be found");
    }

    public String missingDefinedListMessage() {
        return "The list of defined courses cannot be found";
    }

    public void showUndefinedCourseMessage() {
        System.out.println("This course is not defined in the list of defined courses");
    }

    public String missingInputErrorMessage() {
        return "Input is incomplete and information is missing, please check your input";
    }

    /**
     * Askes for user confirmation for an action. Returns true if user input Y or y
     * @param confirmationMessgae
     * @return
     */
    public boolean isUserConfirm(String confirmationMessgae) {
        System.out.println(confirmationMessgae);
        System.out.print("Please enter a Y/y to confirm: ");
        String userInput = scanner.nextLine().trim();
        return userInput.equalsIgnoreCase("y");
    }
    public void cancelMessage() {
        System.out.println("Cancelled");
    }
    public String getNewPlanName(String[] plans) {
        for (int i = 0; i < plans.length; i++) {
            System.out.println((i + 1) + ". " + plans[i]);
        }
        System.out.print("These are your prexisting plans");
        return this.newPlanInput();
    }
    public void renameSuccessfulMessage(){
        System.out.println("Successfully renamed");
    }
}





