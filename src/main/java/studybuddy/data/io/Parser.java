package studybuddy.data.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import studybuddy.commands.AddCommand;
import studybuddy.commands.Command;
import studybuddy.commands.DeleteCourseCommand;
import studybuddy.commands.DeletePlanCommand;
import studybuddy.commands.EditCommand;
import studybuddy.commands.ExitCommand;
import studybuddy.commands.FindCommand;
import studybuddy.commands.GradRequirementCommand;
import studybuddy.commands.HelpCommand;
import studybuddy.commands.InvalidCommand;
import studybuddy.commands.ListCommand;
import studybuddy.commands.PlaceHolderCommand;
import studybuddy.commands.RenamePlanCommand;
import studybuddy.commands.ReplaceCommand;
import studybuddy.commands.SavePlanCommand;
import studybuddy.commands.SummaryCommand;
import studybuddy.commands.SwitchPlanCommand;
import studybuddy.commands.UndoCommand;
import studybuddy.commands.WorkloadForCommand;
import studybuddy.commands.WorkloadBalanceCommand;
import studybuddy.commands.WorkloadSummaryCommand;
import studybuddy.commands.PrereqCommand;
import studybuddy.common.Utils;
import studybuddy.data.course.Course;
import studybuddy.data.course.CourseList;
import studybuddy.data.course.CourseManager;
import studybuddy.data.exception.CEGStudyBuddyException;

public class Parser {
    private static final Ui ui = new Ui();
    /**
     * Parses the input into a command and returns the Command object for the command.
     *
     * @param inputParts Input String array containing the command and parameters.
     * @return The Command object corresponding to the input.
     * @throws IndexOutOfBoundsException If parameters are not included (inputParts.len() = 1).
     */
    public static Command parseCommand(String[] inputParts) throws CEGStudyBuddyException {
        try {
            //Logic to solve Exception message for command inputs without space and with space (no information)
            String command = inputParts[0].toLowerCase();
            switch (command) {
            case CommandNames.ADD:
                if (inputParts.length < 2) {
                    throw new CEGStudyBuddyException("Missing parameters! "
                            + "Format: add c/CODE t/Title mc/MCs y/Year s/Sem");
                }
                return new AddCommand(inputParts[1]);

            case CommandNames.EDIT:
                if (inputParts.length < 2) {
                    throw new CEGStudyBuddyException("Missing parameters! " +
                            "Format: edit c/CODE t/Title mc/MCs y/Year s/Sem");
                }
                return new EditCommand(inputParts[1]);

            case CommandNames.REPLACE:
                if (inputParts.length < 2) {
                    throw new CEGStudyBuddyException("Missing parameters!" +
                            "Format: replace c/OLD CODE c/NEW CODE t/Title mc/MCs y/Year s/Sem");
                }
                return new ReplaceCommand(inputParts[1]);

            case CommandNames.DELETE:
                if (inputParts.length < 2) {
                    throw new CEGStudyBuddyException("Missing parameters! Format: delete c/CODE");
                }
                return new DeleteCourseCommand(inputParts[1]);

            case CommandNames.FIND:
                if (inputParts.length < 2) {
                    throw new CEGStudyBuddyException("Missing parameters! Format: find c/CODE");
                }
                return new FindCommand(inputParts[1]);

            case CommandNames.PREREQ:
                if (inputParts.length < 2) {
                    throw new CEGStudyBuddyException("Missing parameters! Format: prereq c/CODE");
                }
                return new PrereqCommand(inputParts[1]);

            case CommandNames.WORKLOAD_FOR:
                if (inputParts.length < 2) {
                    throw new CEGStudyBuddyException("Missing parameters! Format: workload_for y/YEAR s/SEM");
                }
                return new WorkloadForCommand(inputParts[1]);

            case CommandNames.PLACEHOLDER:
                if (inputParts.length < 2) {
                    throw new CEGStudyBuddyException("Missing parameters! Format: dummy mc/MCs y/YEAR s/SEM");
                }
                return new PlaceHolderCommand(inputParts[1]);

            case CommandNames.LIST:
                return new ListCommand();

            case CommandNames.GRADREQ:
                return new GradRequirementCommand();

            case CommandNames.WORKLOAD_SUMMARY:
                return new WorkloadSummaryCommand();

            case CommandNames.WORKLOAD_BALANCE:
                return new WorkloadBalanceCommand();

            case CommandNames.SAVE:
                return new SavePlanCommand();

            case CommandNames.SWITCH_PLAN:
                return new SwitchPlanCommand();

            case CommandNames.DELETE_PLAN:
                return new DeletePlanCommand();

            case CommandNames.RENAME_PLAN:
                return new RenamePlanCommand();

            case CommandNames.HELP:
                return new HelpCommand();

            case CommandNames.EXIT:
                return new ExitCommand();

            case CommandNames.UNDO:
                return new UndoCommand();

            case CommandNames.SUMMARY:
                return new SummaryCommand();

            default:
                return new InvalidCommand();
            }

        } catch (CEGStudyBuddyException e) {
            throw e;
        } catch (Exception e) {
            throw new CEGStudyBuddyException("An unknown error occurred while parsing the command.");
        }
    }

    public static String parseDelete(String param) throws CEGStudyBuddyException {
        // Ensure the parameter starts with the required prefix
        String trimmedParam = param.trim();
        if (!trimmedParam.startsWith("c/")) {
            throw new CEGStudyBuddyException("Invalid format! Use: delete c/CODE");
        }

        // Extract and normalize course code (convert to uppercase)
        String code = trimmedParam.substring(2).trim().toUpperCase();

        // Validate course code format using regex
        if (!code.matches("^[A-Z]{2,3}\\d{4}[A-Z]?$") && !code.matches("DUM\\d{1,4}?$")) {
            throw new CEGStudyBuddyException("Invalid course code format. Expected: CS2040, EE2026, CG2111A, etc.");
        }
        return code;
    }

    public static String[] parseReplace(String param) {
        String[] tokens = param.trim().split("\\s+");

        // Only parse and return the codes — don't validate formatting here
        if (tokens.length < 2 || !tokens[0].startsWith("c/") || !tokens[1].startsWith("c/")) {
            return new String[]{"", ""}; // Allow ReplaceCommand to handle the formatting issues
        }

        String oldCode = tokens[0].substring(2).toUpperCase();
        String newCode = tokens[1].substring(2).toUpperCase();

        return new String[]{oldCode, newCode};
    }

    public static Course parseCourse(String param, boolean admin) throws CEGStudyBuddyException {
        assert (!param.isEmpty());

        // Early check for decimals using regex
        if (param.matches(".*(mc/\\d+\\.\\d+|y/\\d+\\.\\d+|s/\\d+\\.\\d+).*")) {
            throw new CEGStudyBuddyException("Invalid input: MC, year, and semester"
                    + " must be whole numbers, not decimals.");
        }

        Pattern predefinedCoursePattern = Pattern.compile(
                "c/(?<code>[^\\s]+).*?y/(?<year>\\d+).*?s/(?<sem>\\d+)");
        Matcher predefinedCourseMatcher = predefinedCoursePattern.matcher(param);
        Course c = parseDefinedCourse(predefinedCourseMatcher);
        if (c != null) {
            return c;
        }

        Pattern pattern = Pattern.compile(
                "c/(?<code>\\S+)\\s+" +
                        "t/(?<title>.*?)\\s+" +
                        "mc/(?<mc>\\d+)\\s+" +
                        "y/(?<year>\\d+)\\s+" +
                        "s/(?<sem>\\d+)"
        );
        Matcher matcher = pattern.matcher(param);

        if (!matcher.find()) {
            throw new CEGStudyBuddyException("""
                    Missing or disordered fields. Please follow the format:
                    add c/CODE t/TITLE mc/VALUE y/YEAR s/SEM""");
        }

        String code = matcher.group("code").trim().toUpperCase();
        String title = matcher.group("title").trim();
        String mcStr = matcher.group("mc").trim();
        String yearStr = matcher.group("year").trim();
        String semStr = matcher.group("sem").trim();

        if (code.isEmpty() || title.isEmpty() || mcStr.isEmpty() || yearStr.isEmpty() || semStr.isEmpty()) {
            throw new CEGStudyBuddyException("One or more fields are empty. Please provide all fields:\n" +
                    "add c/CODE t/TITLE mc/VALUE y/YEAR s/SEM");
        }

        if (!code.matches("^[A-Z]{2,3}\\d{4}[A-Z]?$") && !code.matches("DUM\\d{1,4}?$")) {
            throw new CEGStudyBuddyException("Invalid course code format. Expected: CS2040, EE2026, CG2111A etc.");
        }
        // Disallow user input containing "DUM"
        if (!admin && code.contains("DUM")) {
            throw new CEGStudyBuddyException("Invalid course code format. Course code cannot contain \"DUM\".");
        }

        int mc;
        int year;
        int sem;
        try {
            mc = Integer.parseInt(mcStr);
            year = Integer.parseInt(yearStr);
            sem = Integer.parseInt(semStr);
        } catch (NumberFormatException e) {
            throw new CEGStudyBuddyException("Invalid number format. MC, year, and semester must be integers.");
        }

        if (!Utils.isValidMC(mc)) {
            throw new CEGStudyBuddyException("Invalid MC value. MC must be an even number between 0 and 12.");
        }
        if (!Utils.isValidYear(year)) {
            throw new CEGStudyBuddyException("Invalid year. Must be between 1 and 4.");
        }
        if (!Utils.isValidSem(sem)) {
            throw new CEGStudyBuddyException("Invalid semester. Must be either 1 or 2.");
        }

        return new Course(code, title, mc, year, sem);
    }


    private static Course parseDefinedCourse(Matcher predefinedCourseMatcher)
            throws CEGStudyBuddyException {
        if (!predefinedCourseMatcher.find()) {
            return null;
        }

        String code = predefinedCourseMatcher.group("code").trim().toUpperCase();
        String yearStr = predefinedCourseMatcher.group("year").trim();
        String semStr = predefinedCourseMatcher.group("sem").trim();

        if (code.isEmpty() || yearStr.isEmpty() || semStr.isEmpty()) {
            return null;
        }

        if (!code.matches("^[A-Z]{2,3}\\d{4}[A-Z]?$")) {
            return null;
        }

        int year;
        int sem;
        try {
            year = Integer.parseInt(yearStr);
            sem = Integer.parseInt(semStr);
        } catch (NumberFormatException e) {
            return null;
        }

        return getDefinedCourse(code, year, sem);
    }

    private static Course getDefinedCourse(String code, int year, int sem)
            throws CEGStudyBuddyException {
        Course course = CourseManager.getCourse(code);
        if (course != null) {
            // this course is defined
            if (!Utils.isValidYear(year)) {
                throw new CEGStudyBuddyException("Invalid year. Must be between 1 and 4.");
            }
            if (!Utils.isValidSem(sem)) {
                throw new CEGStudyBuddyException("Invalid semester. Must be either 1 or 2.");
            }
            course.setTakeInYear(year);
            course.setTakeInSem(sem);
            return course;
        }
        return null;
    }

    public static String[] parseEdit(String param) throws ArrayIndexOutOfBoundsException, NumberFormatException {
        assert (!param.isEmpty());

        String[] parts = param.split(" ");
        String code = null;
        StringBuilder title = new StringBuilder();
        Integer mc = null;
        Integer y = null;
        Integer s = null;

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].startsWith("c/")) {
                code = parts[i].substring(2);
            } else if (parts[i].startsWith("t/")) {
                // title may consist of a few words
                title = new StringBuilder(parts[i].substring(2));
                i += 1;
                // while i is in bound and parts[i] is not next identifier
                while (i < parts.length && !Utils.hasIdentifier(parts[i])) {
                    title.append(" ").append(parts[i]);
                    i += 1;
                }
                // reset i back to the ending element of title
                i -= 1;
            } else if (parts[i].startsWith("mc/")) {
                mc = Integer.parseInt(parts[i].substring(3));
            } else if (parts[i].startsWith("y/")) {
                y = Integer.parseInt(parts[i].substring(2));
            } else if (parts[i].startsWith("s/")) {
                s = Integer.parseInt(parts[i].substring(2));
            }
        }
        // if no edit value, hold place with empty string
        return new String[]{code,
                            title.toString(),
                            mc != null ? mc.toString() : "",
                            y != null ? y.toString() : "",
                            s != null ? s.toString() : ""};
    }

    public static String[] parseFind(String param) throws CEGStudyBuddyException {
        // Ensure the command has proper format
        if (!param.trim().toLowerCase().startsWith("c/")) {
            throw new CEGStudyBuddyException("Invalid find format! Use: find c/CODE");
        }

        // Extract course code
        String[] parts = param.trim().split("c/", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new CEGStudyBuddyException("Please provide a course code after c/");
        }
        return parts;
    }

    public static int[] parseWorkloadFor(String param) throws CEGStudyBuddyException {
        String[] splits = param.trim().split("s/");
        int[] output = new int[2];
        try {
            output[0] = Integer.parseInt(splits[1].trim());
            output[1] = Integer.parseInt(splits[0].substring(2).trim());
            assert (output[0] == 1 || output[0] == 2) && (output[1] > 0);
        } catch (Exception e) {
            throw new CEGStudyBuddyException("Invalid year and/or sem");
        }

        return output;
    }

    public static Course parseDummy(String param, CourseList courses) throws CEGStudyBuddyException {
        assert (!param.isEmpty());

        String[] parts = param.split(" ");
        if (parts.length < 3) {
            throw new CEGStudyBuddyException(ui.missingInputErrorMessage());
        }
        if (parts.length > 3) {
            throw new CEGStudyBuddyException(ui.extraInputErrorMessage());
        }

        Course.dummyInitialiseCheck(courses);
        int codeNumber = Course.getAvailableDummyIndex();
        // reach max number of dummies, no more dummy to be added
        if (!Course.isValidDummyIndex(codeNumber)) {
            throw new CEGStudyBuddyException(ui.showDummyFullMessage());
        }

        Integer mc = null;
        Integer y = null;
        Integer s = null;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].startsWith("mc/")) {
                mc = Integer.parseInt(parts[i].substring(3));
            } else if (parts[i].startsWith("y/")) {
                y = Integer.parseInt(parts[i].substring(2));
            } else if (parts[i].startsWith("s/")) {
                s = Integer.parseInt(parts[i].substring(2));
            }
        }

        if (!Utils.isValidMC(mc)) {
            throw new CEGStudyBuddyException("Invalid MC value. MC must be an even number between 0 and 12.");
        }
        if (!Utils.isValidYear(y)) {
            throw new CEGStudyBuddyException("Invalid year. Must be between 1 and 4.");
        }
        if (!Utils.isValidSem(s)) {
            throw new CEGStudyBuddyException("Invalid semester. Must be either 1 or 2.");
        }

        return Course.createDummyCourse(mc, y, s);
    }
}
