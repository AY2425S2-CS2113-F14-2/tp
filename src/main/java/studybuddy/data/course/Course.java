package studybuddy.data.course;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Represents a course in NUS.
 * Pre-set info: code, title, module credit (mc), offered in semester 1/2, ...
 * Editable info: take(n) in semester, is cleared/taken
 */
public class Course implements Serializable {
    public static final int DUMMYSIZE = 20;
    public static final String DUMMYWORD = "DUM";
    protected static boolean[] dummies = new boolean[20];
    protected String code;
    protected String title;
    protected int mc;
    protected boolean offerInSem1;
    protected boolean offerInSem2;
    // Editable to user
    protected int takeInSem;
    protected int takeInYear;
    protected static final String DUMMYTITLE = "To be confirmed";

    static {
        // false means this number is not used currently
        Arrays.fill(dummies, false);
    }

    /**
     * Constructor for common creation of course
     * @param code Code of the course, e.g. CS1231, CG1111As.
     * @param title Text title of the course, e.g. Programming Methodology.
     * @param mc Module credit of the course, e.g. 2, 4, 8.
     * @param takeInYear Take/Taken this course in which year, e.g. 1, 2, 3, 4.
     * @param takeInSem Take/Taken this course in which semester, e.g. 1, 2.
     */
    public Course(String code, String title, int mc, int takeInYear, int takeInSem) {
        this.code = code;
        this.title = title;
        this.mc = mc;
        this.takeInYear = takeInYear;
        this.takeInSem = takeInSem;
        this.offerInSem1 = true;
        this.offerInSem2 = true;
    }

    /**
     * Constructor for placeholder (dummy) course
     * @param mc Module credit of the course, e.g. 2, 4, 8.
     * @param takeInYear Take/Taken this course in which year, e.g. 1, 2, 3, 4.
     * @param takeInSem Take/Taken this course in which semester, e.g. 1, 2.
     */
    protected Course(String code, int mc, int takeInYear, int takeInSem) {
        this.code = code;
        this.title = DUMMYTITLE;
        this.mc = mc;
        this.takeInYear = takeInYear;
        this.takeInSem = takeInSem;
        this.offerInSem1 = true;
        this.offerInSem2 = true;
    }

    public static Course createDummyCourse(int mc, int takeInYear, int takeInSem) {
        int codeNumber = getAvailableDummyIndex();
        if (!isValidDummyIndex(codeNumber)) {
            return null;
        }
        String code = "DUM" + codeNumber;
        dummies[codeNumber] = true;
        return new Course(code, mc, takeInYear, takeInSem);
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getMc() {
        return mc;
    }

    public boolean isOfferInSem1() {
        return offerInSem1;
    }

    public boolean isOfferInSem2() {
        return offerInSem2;
    }

    public int getTakeInSem() {
        return takeInSem;
    }

    public int getTakeInYear() {
        return takeInYear;
    }

    public void setTakeInSem(int takeInSem) {
        this.takeInSem = takeInSem;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMc(int mc) {
        this.mc = mc;
    }

    public void setOfferInSem1(boolean offerInSem1) {
        this.offerInSem1 = offerInSem1;
    }

    public void setOfferInSem2(boolean offerInSem2) {
        this.offerInSem2 = offerInSem2;
    }

    public void setTakeInYear(int takeInYear) {
        this.takeInYear = takeInYear;
    }

    public static boolean[] getDummies() {
        return dummies;
    }

    @Override
    public String toString() {
        return getCode() + " - " + getTitle() + " (" + getMc() + " MCs)";
    }

    /**
     * Function to convert the course in to its storage format
     * @return String of the storage formatted course
     */
    public String toStoreFormat(){
        return "course : c/" + getCode() + " " +
                "t/" + getTitle() + " " +
                "mc/" + getMc() + " " +
                "y/" + getTakeInYear() + " " +
                "s/" + getTakeInSem();
    }

    public static boolean isDummyFull() {
        for (int i = 0; i < DUMMYSIZE; i++) {
            if (!dummies[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidDummyIndex(int number) {
        return (number >= 0 && number < DUMMYSIZE);
    }

    public static int getAvailableDummyIndex() {
        for (int i = 0; i < DUMMYSIZE; i++) {
            if (!dummies[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void dummyInitialiseCheck(CourseList list) {
        Arrays.fill(dummies, false);
        for (Course course: list.getCourses()) {
            String courseCode = course.code;
            if (courseCode.startsWith(DUMMYWORD)) {
                courseCode = courseCode.replace(DUMMYWORD, "");
                int index = -1;
                try {
                    index = Integer.parseInt(courseCode);
                } catch (NumberFormatException e) {
                    // there should not be any course code uses "DUM" but with invalid number
                    list.getCourses().remove(course);
                }
                if (isValidDummyIndex(index)) {
                    // this index is used
                    dummies[index] = true;
                }
            }
        }
    }
}
