package studybuddy.commands;

import studybuddy.data.course.CourseList;
import studybuddy.data.exception.CEGStudyBuddyException;
import studybuddy.data.storage.StorageManager;

public class ExitCommand extends Command {
    public static final String COMMAND_DESCRIPTION = """
            exit
                Exits the program.""";
    private boolean isRunning = true;

    public ExitCommand() {
        super("");
        this.isRunning = true;
    }

    public String execute(CourseList courses, StorageManager storage) throws CEGStudyBuddyException {
        // get user confirmation to exit
        if (!ui.isUserConfirm("Are you sure you want to exit?")) {
            isRunning = true;
            return "Exit Cancelled.";
        }
        isRunning = false; // to exit the program after confirmation
        storage.saveCurrentPlan();
        return "Goodbye!";
    }

    public boolean isRunning() {
        return isRunning;
    }
}
