package studybuddy.data.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import studybuddy.CEGStudyBuddy;
import studybuddy.data.exception.CEGStudyBuddyException;
import studybuddy.data.course.CourseList;
import studybuddy.data.io.Ui;


public class StorageManager {
    private String directory;
    private Ui ui;

    /**
     * Constructs a StorageManager with a specified directory for storing plans.
     *
     * @param directory The directory path where plans will be stored.
     */
    public StorageManager(String directory, Scanner scanner) {
        this.directory = directory;
        this.ui = new Ui(scanner);
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
        String planFileName = plan + ".bin";
        File planFile = new File(dir, planFileName);
        if (planFile.exists()) {
            throw new CEGStudyBuddyException("A plan with this name already exists.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(planFile))) {
            CEGStudyBuddy.courses.setPlanName(plan);
            oos.writeObject(CEGStudyBuddy.courses);
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Error in making new plan");
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

        String planFileName = CEGStudyBuddy.courses.getPlanName() + ".bin";
        File planFile = new File(dir, planFileName);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(planFile))) {
            oos.writeObject(CEGStudyBuddy.courses);
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Error in saving");
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
            throw new CEGStudyBuddyException("You have no plans saved");
        }

        File planFile = new File(dir, planName + ".bin");
        if (!planFile.exists()) {
            throw new CEGStudyBuddyException("Invalid Plan Name");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(planFile))) {
            CEGStudyBuddy.courses = (CourseList) ois.readObject();
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Data Source Corrupted");
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
            throw new CEGStudyBuddyException("You have no plans saved");
        }

        String[] plans = dir.list((d, name) -> name.endsWith(".bin"));
        if (plans == null || plans.length == 0) {
            throw new CEGStudyBuddyException("You have no plans saved");
        }

        // Remove ".bin" extension
        for (int i = 0; i < plans.length; i++) {
            if(plans[i].endsWith(".bin")) {
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

        if (planNumber.equals("0")) {
            this.newPlan();
            return;
        }

        try {
            int planNo = Integer.parseInt(planNumber);
            this.loadPlan(plans[planNo - 1]);
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Invalid plan number");
        }

        ui.planSuccessfullyLoadedMessage();
    }

    /**
     * This method allows the user to select a plan and delete it.
     *
     * @throws CEGStudyBuddyException
     */
    public void deletePlanWithSelection() throws CEGStudyBuddyException{
        String[] plans;
        try {
            plans = this.listPlans();
        } catch (Exception e) {
            ui.noPreviousPlansMessage();
            return;
        }

        String planNumber = ui.chooseDeletePlan(plans);
        try {
            int planNo = Integer.parseInt(planNumber);
            this.deletePlan(plans[planNo - 1]);
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Invalid plan number");
        }

        ui.displaySuccessfullyDeletedMessage();
    }

    /**
     * This method delets the plan
     * @param planName
     * @throws CEGStudyBuddyException
     */
    public void deletePlan(String planName) throws CEGStudyBuddyException {
        if(!ui.isUserConfirm("Are you sure you want to delete " + planName)) {
            ui.cancelMessage();
            return;
        }
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
            return;
        }
        File planFile = new File(dir, planName + ".bin");
        if (planFile.exists()) {
            planFile.delete();
        } else {
            throw new CEGStudyBuddyException("Plan does not exist");
        }
    }
    public void renamePlan() throws CEGStudyBuddyException {
        String[] plans = this.listPlans();
        String planName = ui.getNewPlanName(plans);
        if (!planName.matches("[a-zA-Z0-9]*")) {
            throw new CEGStudyBuddyException("Invalid Plan Name");
        }
        File newPlanFile = new File(directory, planName + ".bin");
        if(newPlanFile.exists()) {
            throw new CEGStudyBuddyException("Plan already exists");
        }
        File planFile = new File(directory, CEGStudyBuddy.courses.getPlanName() + ".bin");
        if (planFile.exists()) {
            planFile.delete();
        }
        CEGStudyBuddy.courses.setPlanName(planName);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(newPlanFile))) {
            oos.writeObject(CEGStudyBuddy.courses);
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Error in renaming plan");
        }
        ui.renameSuccessfulMessage();
    }
}
