# syCHEN1645's Project Portfolio Page

CEGStudyBuddy is a desktop application that tracks and plans university courses efficiently for NUS Computer Engineering students who want a structured academic roadmap.

Given below are my contributions to the project.

---

## New Feature: Added command for adding a dummy (placeholder) course

### What it does

Adds dummy courses that are auto-assigned unique course codes (e.g., DUM0, DUM1) with user assigned MCs.

### Justification

This feature improves flexibility by allowing users to plan around modules that are undecided or not yet offered. 

### Highlights

Limited to 20 dummy courses in each course plan to keep trace and also prevent malicious inputs flooding the plan with dummies. Added input validation in Parser.parseCourse() to prevent adding dummies through command add. 

## New Feature: Simplified user inputs  a predefined list of courses

### What it does

When adding a course, if the course code has a match in Defined_Courses, a text file containing predefined core CEG courses, the add command can skip course title and MC inputs. 

From
```
add c/CODE t/TITLE mc/MC y/YEAR s/SEMESTER
```
To
```
add c/CODE y/YEAR s/SEMESTER
```

### Justification
Reduces user input length. Reduces false information from user inputs (e.g. wrong course titles, wrong MCs). 

### Highlights 
Defined_Courses is managed by a new class CourseManager which specialises in reading and searching in the text file. It also provides static methods to convert between Course objects and JSON strings. 

### Credits
This function implements Gson dependency 'com.google.code.gson:gson:2.10.1'. 

## Enhancements

### High-Severity and High-Complexity Debug

Debugged issue *Inputting Ctrl+Z on startup causes program to loop infinitely* [issue #110](https://github.com/AY2425S2-CS2113-F14-2/tp/issues/110) [PR #183](https://github.com/AY2425S2-CS2113-F14-2/tp/pull/183)

Debugged issue *List of defined courses not on JAR nor included in version release* [issue #113](https://github.com/AY2425S2-CS2113-F14-2/tp/issues/113) [PR 180](https://github.com/AY2425S2-CS2113-F14-2/tp/pull/180)

### Test Cases

Wrote test cases for CourseManager *CourseManagerTest* [PR #77](https://github.com/AY2425S2-CS2113-F14-2/tp/pull/77)

Wrote test cases for PlaceHolderCommand *PlaceHolderTest* [PR #205](https://github.com/AY2425S2-CS2113-F14-2/tp/pull/205)

Wrote test cases for EditCommand *EditCommandTest* [PR #54](https://github.com/AY2425S2-CS2113-F14-2/tp/pull/54)

Wrote test cases for ReplaceCommand *ReplaceCommandTest* [PR #203](https://github.com/AY2425S2-CS2113-F14-2/tp/pull/203)

### Improve Existing Features

Improved overall code quality by contributing to OOP transformation. Example: [PR #82](https://github.com/AY2425S2-CS2113-F14-2/tp/pull/82) [PR #45](https://github.com/AY2425S2-CS2113-F14-2/tp/pull/45/files)

Improved user interation by updating accurate error output messages. Example: [PR #203](https://github.com/AY2425S2-CS2113-F14-2/tp/pull/203) [PR #206](https://github.com/AY2425S2-CS2113-F14-2/tp/pull/206)

---

## Documentation

### User Guide

Added documentation for the `add`, `edit`, `list` and `dummy` commands.

Added *Introduction* section.

### Developer Guide

Added documentation for the `edit` and `dummy` commands.

Added documentation and class diagrams for class `Course`.

[Class Course](docs/class_diagrams/CourseClassDiagram.png)

Added documentation for class `CourseManager`.

Added documentation for the feature *Predefined List of Courses*.

Added sequential diagram for adding a course. 

[Add a Course](docs/class_diagrams/CourseManagerClassDiagram.png)

