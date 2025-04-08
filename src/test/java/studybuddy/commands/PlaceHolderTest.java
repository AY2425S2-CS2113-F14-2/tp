package studybuddy.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import studybuddy.data.course.CourseList;
import studybuddy.data.exception.CEGStudyBuddyException;
import studybuddy.data.storage.StorageManager;

public class PlaceHolderTest {
    private CourseList courses;
    private final StorageManager storage = new StorageManager("./PlanData");

    @BeforeEach
    public void setup() {
        courses = new CourseList("test");
    }

    @Test
    public void testValidCommand() {
        String input = "dummy mc/6 y/2 s/2";
        PlaceHolderCommand command = new PlaceHolderCommand(input);
        String expected = "There is extra input. Please check your input";
        assertEquals(expected, execute(command));
    }

    @Test
    public void testDummyCommand1() {
        String input = "dummy mc/6 y/2 s/2";
        PlaceHolderCommand command = new PlaceHolderCommand(input);
        String expected = "There is extra input. Please check your input";
        assertEquals(expected, execute(command));
    }

    @Test
    public void testDummyCommand2() {
        String input = "dummy mc/6 y/2 s/2";
        PlaceHolderCommand command = new PlaceHolderCommand(input);
        String expected = "There is extra input. Please check your input";
        assertEquals(expected, execute(command));
    }

    @Test
    public void testDummyCommand3() {
        String input = "dummy mc/6 y/2 s/2";
        PlaceHolderCommand command = new PlaceHolderCommand(input);
        String expected = "There is extra input. Please check your input";
        assertEquals(expected, execute(command));
    }

    private String execute(PlaceHolderCommand cmd) {
        try {
            return cmd.execute(courses, storage);
        } catch (CEGStudyBuddyException e) {
            return e.getMessage();
        }
    }
}
