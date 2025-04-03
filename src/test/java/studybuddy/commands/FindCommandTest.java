package studybuddy.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import studybuddy.data.course.Course;
import studybuddy.data.course.CourseList;
import studybuddy.data.exception.CEGStudyBuddyException;
import studybuddy.data.storage.StorageManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FindCommandTest {
    private CourseList courses;
    private StorageManager storage;
    //testing input FIX
    @BeforeEach
    public void setup() {
        courses = new CourseList("test");
        storage = new StorageManager("./PlanData", courses);
        courses.clear();

        courses.add(new Course("CS2113", "Software Engineering", 4, 2, 2));
        courses.add(new Course("CS2040", "Data Structures", 4, 2, 1));
    }

    @Test
    public void testFindExistingCourse() {
        try {
            FindCommand findCommand = new FindCommand("c/CS2040");
            String output = findCommand.execute(courses, storage);

            assertTrue(output.contains("Course Code: CS2040"));
            assertTrue(output.contains("Course Title: Data Structures"));
            assertTrue(output.contains("Number of MCs: 4"));
            assertTrue(output.contains("Y2S1"));
        } catch (CEGStudyBuddyException e) {
            fail("Exception should not be thrown for valid input");
        }
    }

    @Test
    public void testFindNonExistingCourse() {
        try {
            FindCommand findCommand = new FindCommand("c/CS9999");
            String output = findCommand.execute(courses, storage);

            assertEquals("Course CS9999 not found in your course list.", output);
        } catch (CEGStudyBuddyException e) {
            fail("Exception should not be thrown for non-existent course");
        }
    }

    @Test
    public void testInvalidFindFormat() {
        CEGStudyBuddyException exception = assertThrows(CEGStudyBuddyException.class, () -> {
            FindCommand findCommand = new FindCommand("missingTag"); // no c/
            findCommand.execute(courses, storage);
        });

        assertEquals("Invalid find format! Use: find c/CODE", exception.getMessage());
    }

}

