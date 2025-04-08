package studybuddy.commands;

import studybuddy.common.Utils;
import studybuddy.data.course.Course;
import studybuddy.data.course.CourseList;
import studybuddy.data.exception.CEGStudyBuddyException;
import studybuddy.data.io.Parser;
import studybuddy.data.storage.StorageManager;

public class WorkloadForCommand extends Command {
    public static final String COMMAND_DESCRIPTION = """
            workload_for y/YEAR s/SEMESTER
                Displays the courses and the total workload for the given semester.""";

    public WorkloadForCommand(String params) {
        super(params);
    }

    @Override
    public String execute(CourseList courses, StorageManager storage) throws CEGStudyBuddyException {
        int[] paramParts = Parser.parseWorkloadFor(param);
        int sem = paramParts[0];
        int year = paramParts[1];
        if (!Utils.isValidSem(sem) || !Utils.isValidYear(year)) {
            throw new CEGStudyBuddyException("Invalid input. Year must be from 1 to 4 and sem must be either 1 or 2.");
        }

        StringBuilder output = new StringBuilder("These are the courses that you are currently taking in Year "
                + year + " Semester " + sem + ":");
        int totalWorkLoad = 0;
        int index = 1;

        for (Course course : courses.getCourses()) {
            if (course.getTakeInSem() == sem && course.getTakeInYear() == year) {
                totalWorkLoad += course.getMc();
                output.append("\n").append(index).append(". ").append(course);
                index++;
            }
        }

        if (index == 1) {
            return "You are not taking any courses in this semester yet.";
        }

        output.append("\n\nYour total workload for Year " + year + " Semester " + sem + " is: ")
                .append(totalWorkLoad).append("MCs\n")
                .append(Utils.checkWorkload(totalWorkLoad, sem));
        return output.toString();
    }
}
