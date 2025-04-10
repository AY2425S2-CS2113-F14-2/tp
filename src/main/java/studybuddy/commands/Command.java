package studybuddy.commands;

import studybuddy.data.course.CourseList;
import studybuddy.data.exception.CEGStudyBuddyException;
import studybuddy.data.io.Ui;
import studybuddy.data.storage.StorageManager;

public abstract class Command {
    protected String param;
    protected final Ui ui;

    public Command(String param) {
        this.param = param;
        this.ui = new Ui();
    }

    /**
     * Executes the command.
     * @return The result string to be printed.
     */
    public abstract String execute(CourseList courses, StorageManager storage) throws CEGStudyBuddyException;

    public boolean isRunning() {
        return true;
    }
}
