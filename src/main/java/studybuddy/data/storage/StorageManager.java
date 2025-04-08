package studybuddy.data.storage;

import java.io.File;
import java.util.Scanner;

import studybuddy.CEGStudyBuddy;
import studybuddy.data.exception.CEGStudyBuddyException;
import studybuddy.data.course.CourseList;
import studybuddy.data.io.Parser;
import studybuddy.data.io.Ui;

import java.io.FileWriter;
import java.nio.file.Files;


public class StorageManager {
    private final Ui ui;
    private String directory;

    /**
     * Constructs a StorageManager with a specified directory for storing plans.
     *
     * @param directory The directory path where plans will be stored.
     * @param scanner   The scanner from main class.
     */
    public StorageManager(String directory, Scanner scanner) {
        this.directory = directory;
        this.ui = new Ui(scanner);
    }

    /**
     * Constructs a StorageManager with a specified directory for testing.
     *
     * @param directory The directory path where plans will be stored.
     */
    public StorageManager(String directory) {
        this.directory = directory;
        this.ui = new Ui();
    }

    /**
     * Gets the current directory used for storing plans.
     *
     * @return The directory path.
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Sets the directory path for storing plans.
     *
     * @param directory The new directory path.
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * Saves a new plan to storage. If a plan with the same name already exists, an exception is thrown.
     *
     * @param plan The name of the new plan.
     * @throws CEGStudyBuddyException If a plan with the same name exists or if an error occurs during saving.
     */
    public void saveNewPlan(String plan) throws CEGStudyBuddyException {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs(); // Create directory if it doesn't exist
        }
        CEGStudyBuddy.courses = new CourseList(plan);
        String planFileName = plan + ".txt";
        File planFile = new File(dir, planFileName);
        if (planFile.exists()) {
            throw new CEGStudyBuddyException("A plan with this name already exists.");
        }

        try {
            dumpToFile(planFile, CEGStudyBuddy.courses.toStoreFormat());
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Error in making new plan.");
        }
    }

    /**
     * Saves the currently loaded plan to storage.
     *
     * @throws CEGStudyBuddyException If an error occurs during saving.
     */
    public String saveCurrentPlan() throws CEGStudyBuddyException {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String planFileName = CEGStudyBuddy.courses.getPlanName() + ".txt";
        File planFile = new File(dir, planFileName);
        try {
            dumpToFile(planFile, CEGStudyBuddy.courses.toStoreFormat());
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Error in saving.");
        }
        return "Plan saved successfully.";
    }

    /**
     * Loads a saved plan by name and sets it as the current plan.
     *
     * @param planName The name of the plan to load.
     * @throws CEGStudyBuddyException If the plan does not exist or the data is corrupted.
     */

    public void loadPlan(String planName) throws CEGStudyBuddyException {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
            throw new CEGStudyBuddyException("You have no plans saved.");
        }

        File planFile = new File(dir, planName + ".txt");
        if (!planFile.exists()) {
            throw new CEGStudyBuddyException("Invalid Plan Name.");
        }

        try {
            CourseList courses = new CourseList(planName);
            String[] data = Files.readString(planFile.toPath()).split("\n");
            for (String line : data) {
                if (!line.trim().isEmpty()) {
                    courses.add(Parser.parseCourse(line, true));
                }
            }
            CEGStudyBuddy.courses = courses;
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Data Source Corrupted.");
        }
    }

    /**
     * Lists all saved plan names in the storage directory.
     *
     * @return An array of plan names without file extensions.
     * @throws CEGStudyBuddyException If no plans are found.
     */
    public String[] listPlans() throws CEGStudyBuddyException {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
            throw new CEGStudyBuddyException("You have no plans saved.");
        }

        String[] plans = dir.list((d, name) -> name.endsWith(".txt"));
        if (plans == null || plans.length == 0) {
            throw new CEGStudyBuddyException("You have no plans saved.");
        }

        // Remove ".txt" extension
        for (int i = 0; i < plans.length; i++) {
            if (plans[i].endsWith(".txt")) {
                plans[i] = plans[i].substring(0, plans[i].length() - 4);
            }
        }
        return plans;
    }

    /**
     * Prompts the user to create a new plan by entering a valid alphanumeric plan name.
     *
     * @throws CEGStudyBuddyException If saving the new plan fails.
     */
    public void newPlan() throws CEGStudyBuddyException {
        String planName = "";
        while (planName.isEmpty()) {
            planName = ui.newPlanInput();
            // check that name does not have any special characters
            if (!planName.matches("[a-zA-Z0-9]*")) {
                planName = "";
            }
        }
        try {
            this.saveNewPlan(planName);
            ui.createNewPlanMessage();
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Initializes the plan selection process.
     * If an error occurs, it prompts the user again.
     */
    public void initializePlan() {
        ui.printLine();
        boolean initRun = true;
        while (initRun) {
            initRun = false;
            try {
                selectPlan();
            } catch (Exception e) {
                ui.showError(e.getMessage());
                initRun = true;
            }
        }
    }

    /**
     * Allows the user to select an existing plan or create a new one.
     *
     * @throws CEGStudyBuddyException If the selected plan number is invalid.
     */
    public void selectPlan() throws CEGStudyBuddyException {
        String[] plans;
        try {
            plans = this.listPlans();
        } catch (Exception e) {
            ui.noPreviousPlansMessage();
            this.newPlan();
            return;
        }

        String planNumber = ui.chooseOrCreateNewPlans(plans);

        if (planNumber.equals("0")) { // check for new plan creation
            this.newPlan();
            return;
        }

        int planNo = 0;

        try {
            planNo = Integer.parseInt(planNumber);
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Invalid plan number.");
        }
        if (planNo < 0 || planNo > plans.length) {
            throw new CEGStudyBuddyException("Plan number is  out of range.");
        }
        try {
            this.loadPlan(plans[planNo - 1]);
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Error in plan loading.");
        }

        ui.planSuccessfullyLoadedMessage();
    }

    /**
     * This method allows the user to select a plan and delete it.
     *
     * @throws CEGStudyBuddyException If the plan number input is invalid or an error occurs when deleting the plan.
     */
    public void deletePlanWithSelection() throws CEGStudyBuddyException {
        String[] plans;
        try {
            plans = this.listPlans();
        } catch (Exception e) {
            ui.noPreviousPlansMessage();
            return;
        }
        String planNumber = ui.chooseDeletePlan(plans);
        int planNo = -1;
        try {
            planNo = Integer.parseInt(planNumber);
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Invalid plan number.");
        }
        if (planNo <= 0 || planNo > plans.length) {
            throw new CEGStudyBuddyException("Plan number is out of range.");
        }
        try {
            this.deletePlan(plans[planNo - 1]);
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Error deleting plan.");
        }
    }

    /**
     * This method deletes the plan
     *
     * @param planName The name of the plan to be deleted.
     * @throws CEGStudyBuddyException If the plan does not exist.
     */
    public void deletePlan(String planName) throws CEGStudyBuddyException {
        // Get user confirmation to delete
        if (!ui.isUserConfirm("Are you sure you want to delete " + planName + "?")) {
            ui.cancelMessage();
            return;
        }
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
            return;
        }
        File planFile = new File(dir, planName + ".txt");
        if (planFile.exists()) {
            planFile.delete();
        } else {
            throw new CEGStudyBuddyException("Plan does not exist.");
        }
        ui.displaySuccessfullyDeletedMessage();
        if (planName.equals(CEGStudyBuddy.courses.getPlanName())) {
            this.initializePlan();
        }
    }

    /**
     * This function starts the input sequence to rename the current plan
     *
     * @throws CEGStudyBuddyException If plan name is invalid or plan already exists.
     */
    public void renamePlan() throws CEGStudyBuddyException {
        String[] plans = this.listPlans();
        String planName = ui.getNewPlanName(plans);
        if (!planName.matches("[a-zA-Z0-9]*")) {
            throw new CEGStudyBuddyException("Invalid Plan Name.");
        }
        File newPlanFile = new File(directory, planName + ".txt");
        if (newPlanFile.exists()) {
            throw new CEGStudyBuddyException("This plan already exists.");
        }
        File planFile = new File(directory, CEGStudyBuddy.courses.getPlanName() + ".txt");
        CEGStudyBuddy.courses.setPlanName(planName);
        planFile.renameTo(newPlanFile);
        ui.renameSuccessfulMessage();
    }

    /**
     * This function writes the data into the file.
     *
     * @param file File the data has to be stored in.
     * @param data Data that has to be stored.
     * @throws CEGStudyBuddyException If an error occurs while writing to file.
     */
    private void dumpToFile(File file, String data) throws CEGStudyBuddyException {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(data);
            fw.close();
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Error occurred in saving to file.");
        }
    }

    /**
     * Function to autosave.
     *
     * @throws CEGStudyBuddyException If an error occurs while saving.
     */
    public void autoSave() throws CEGStudyBuddyException {
        File saveFile = new File(directory, CEGStudyBuddy.courses.getPlanName() + ".txt");
        try {
            dumpToFile(saveFile, CEGStudyBuddy.courses.toStoreFormat());
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Error in autosaving");
        }
    }
}
