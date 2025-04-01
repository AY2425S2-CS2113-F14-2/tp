package studybuddy.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import studybuddy.CEGStudyBuddy;
import studybuddy.course.Course;

public class EditCommandTest {
    @BeforeEach
    public void setup() {
        // Adds a fresh test data before each test
        CEGStudyBuddy.courses.clear();
        CEGStudyBuddy.courses.add(new Course("CS2113", "Software Engineering", 4, 2, 2));
        CEGStudyBuddy.courses.add(new Course("CS2040C", "Data Structures", 4, 2, 1));
        CEGStudyBuddy.courses.add(new Course("CS1010", "Programming Methodology", 4, 1, 1));
        CEGStudyBuddy.courses.add(new Course("DTK1234", "Design Thinking", 4, 1, 1));
        CEGStudyBuddy.courses.add(new Course("PF1101", "Project Management", 4, 1, 2));
    }

    @Test
    public void testEditExistingCourse1() throws CEGStudyBuddyException {
        EditCommand editCourse = new EditCommand("c/CS2113 s/1 y/3");
        String output = editCourse.execute();
        assertEquals("Success", output);

        // Ensure the course has actually been edited
        FindCommand findCourse = new FindCommand("c/CS2113");
        String actualContent = findCourse.execute();
        String expectedContent = "Course Code: CS2113\n"
                + "Course Title: Software Engineering\n"
                + "Number of MCs: 4\n"
                + "Year and Sem: Y3S1";
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testEditExistingCourse2() throws CEGStudyBuddyException {
        EditCommand editCourse = new EditCommand("c/PF1101 s/2 y/1 t/Management Project");
        String output = editCourse.execute();
        assertEquals("Success", output);

        // Ensure the course has actually been edited
        FindCommand findCourse = new FindCommand("c/PF1101");
        String actualContent = findCourse.execute();
        String expectedContent = "Course Code: PF1101\n"
                + "Course Title: Management Project\n"
                + "Number of MCs: 4\n"
                + "Year and Sem: Y1S2";
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testEditNonExistingCourse1() throws CEGStudyBuddyException {
        EditCommand editCourse = new EditCommand("c/CDE2501 mc/2");
        String output = editCourse.execute();
        assertEquals("Course not found.", output);
    }

    @Test
    public void testEditInvalidInput1() throws CEGStudyBuddyException {
        EditCommand editCourse = new EditCommand("c/DTK1234 mc/2.0 t/Design and Think");
        String output = editCourse.execute();
        assertEquals("Error: Cannot convert to Integer", output);
    }
}
