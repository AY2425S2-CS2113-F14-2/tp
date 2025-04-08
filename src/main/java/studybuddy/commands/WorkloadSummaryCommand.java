package studybuddy.commands;

import java.util.Arrays;

import studybuddy.common.Utils;
import studybuddy.data.course.Course;
import studybuddy.data.course.CourseList;
import studybuddy.data.storage.StorageManager;

public class WorkloadSummaryCommand extends Command {
    public static final String COMMAND_DESCRIPTION = """
            workload_summary
                Displays the total workload for all semesters.""";

    public WorkloadSummaryCommand() {
        super(""); // no parameter
    }

    public String execute(CourseList courses, StorageManager storage) {
        assert (courses != null);
        int[] mcsInEachSemester = new int[Utils.NUM_OF_SEMESTERS];
        String[] period = new String[Utils.NUM_OF_SEMESTERS];
        int year = 1;
        int semester = 1;
        while (year <= 4) {
            int sem = ((year - 1) * 2) + semester;
            period[sem - 1] = "Year " + year + " Semester " + semester++;
            if (semester == 3) {
                semester = 1;
                year++;
            }
        }
        for (Course course : courses.getCourses()) {
            year = course.getTakeInYear();
            semester = course.getTakeInSem();
            int sem = ((year - 1) * 2) + semester;
            mcsInEachSemester[sem - 1] += course.getMc();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("---- Workload Summary ----\n");
        sb.append("Here is a summary of your workload for all the semesters:\n");
        for (int i = 0; i < Utils.NUM_OF_SEMESTERS; i++) {
            sb.append(period[i] + ": " + mcsInEachSemester[i] + "MCs ");
            sb.append(Utils.checkWorkload(mcsInEachSemester[i], i) + "\n");
        }

        int totalMCs = Arrays.stream(mcsInEachSemester).sum();
        sb.append("\nYou currently have ")
                .append(totalMCs)
                .append(" MCs in total.\n");

        int neededMCs = Utils.GRADUATION_MCS - totalMCs;
        if (neededMCs < 0) {
            neededMCs = 0;
        }
        sb.append("You still need ")
                .append(neededMCs)
                .append(" MCs to reach the minimum graduation requirement of 160 MCs.\n");
        if (neededMCs == 0) {
            sb.append("Congratulations! You have met the graduation requirement!\n");
        }


        return sb.toString();
    }
}
