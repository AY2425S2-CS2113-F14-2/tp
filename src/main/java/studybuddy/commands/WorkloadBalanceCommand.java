package studybuddy.commands;

import studybuddy.data.course.Course;
import studybuddy.data.course.CourseList;
import studybuddy.data.exception.CEGStudyBuddyException;
import studybuddy.data.storage.StorageManager;

public class WorkloadBalanceCommand extends Command {
    public static final String COMMAND_DESCRIPTION = """
            workload_balance
                Displays the minimum and maximum semester workload for the course plan.""";

    public WorkloadBalanceCommand() {
        super(""); // no parameters
    }

    public String execute(CourseList courses, StorageManager storage) throws CEGStudyBuddyException {
        int[] mCs = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        for (Course course : courses.getCourses()) {
            int index = (course.getTakeInYear()-1) * 2 + course.getTakeInSem() - 1;
            mCs[index] += course.getMc();
        }
        int max = 0;
        int min = 1000000;
        for (int i : mCs) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
        }
        String output = "Min: " + min + " MCs\nMax: " + max + " MCs";
        if (max - min > 8){
            output = output + "\n You have a high variance in you workload. This can be very stressful and can " +
                    "negatively affect your CAP.";
        } else{
            output = output + "\nYou seem to have a pretty balanced workload.";
        }
        return output;
    }
}
