package studybuddy.data.io;

import java.util.Scanner;

import studybuddy.data.course.Course;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Reads the input and parses it into a String array.
     *
     * @return The String array containing the command and parameters.
     */
    public String[] readInput() {
        String userInput = scanner.nextLine();
        return userInput.split(" ", 2);
    }

    // Displays the welcome logo and greeting
    public void showWelcome() {
        System.out.println("Welcome to CEGStudyBuddy!");
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

    public void showSaveMessage(String message) {
        System.out.println(message);
    }

    public String newPlanInput() {
        System.out.print("Please enter a plan name \nNo special characters are allowed, only alphanumeric input: ");
        return scanner.nextLine().trim();
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
        return scanner.nextLine().trim();
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
        return scanner.nextLine().trim();
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

    public void showUndefinedCourseMessage() {
        System.out.println("This course is not defined in the list of defined courses");
    }

    public String missingInputErrorMessage() {
        return "Input is incomplete and information is missing, please check your input";
    }
}
