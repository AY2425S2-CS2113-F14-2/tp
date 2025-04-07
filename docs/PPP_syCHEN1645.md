# Project Portfolio Page

CEGStudyBuddy is a desktop application that tracks and plans university courses efficiently for NUS Computer Engineering students who want a structured academic roadmap.

Given below are my contributions to the project.

## New Feature: Added command for adding a dummy (placeholder) course

### What it does

Dummy courses are auto-assigned unique course codes (e.g., DUM0, DUM1) with user assigned MCs.

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

Debugged issue *Inputting Ctrl+Z on startup causes program to loop infinitely* [#110](https://github.com/AY2425S2-CS2113-F14-2/tp/issues/110)

Debugged issue *List of defined courses not on JAR nor included in version release* [#113](https://github.com/AY2425S2-CS2113-F14-2/tp/issues/113)

## Documentation

### User Guide

Added documentation for the `add`, `edit`, `list` and `dummy` commands.

Added *Introduction* section.

### Developer Guide

Added documentation for the `edit` and `dummy` commands.

Added documentation and class diagrams for the classes `Course` and `CourseManager`.

Added documentation for the feature *Predefined List of Courses*.

Added sequential diagram for adding a course. 

