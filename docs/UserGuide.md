# CEG StudyBuddy User Guide

## Introduction

**CEGStudyBuddy** is a desktop application that tracks and plans university courses efficiently for NUS Computer Engineering students who want a structured academic roadmap.  
It helps to optimise workload, ensure graduation requirements, and keep students organised with ease, while managing multiple scenarios and possible schedules in their academic journey.

This application is optimised for a **Command Line Interface (CLI)**.  
If you are a fast typer, you can plan and track your courses faster than NUSMods and boost you CLI skills at the same time!

---

## Quick Start

1. Ensure you have **Java 17** installed on your computer.  
   You may download Java 17 from [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

   - **Windows Users**: Any Windows installer will work, but the Windows x64 Installer is recommended.
   - **Mac Users**: Use the macOS x64 DMG Installer if you have an Intel CPU, or the macOS Arm 64 DMG Installer for Apple M-series CPUs.
   - **Linux Users**: Use the Debian Package for Debian-based distros (Ubuntu, Linux Mint) or the RPM Package for Red Hat-based distros (Fedora, CentOS).

2. Download the latest `.jar` file for CEGStudyBuddy [here](http://link.to/cegstudybuddy).

3. Place the `.jar` file in a desired folder.  
   This folder will act as the working directory for the application.

4. Open your terminal in that folder:
   - On **Windows**: Right-click in the folder and select *Open in Terminal*.
   - On **Mac/Linux**: Use `cd` to navigate to the folder in Terminal.

5. Run the application:
   ```
java -jar cegstudybuddy.jar


6. Start typing commands and hit `Enter`.

Refer to the **Features** section below for all available commands.

---

## Features

### Notes about Course Plans
At any time, in CEGStudyBuddy, you will be working on a plan, so in the start you will be asked to create a new plan or work on a pre-existing plan.
You can save multiple course plans and switch between them (see Switch Plan Command below).

Note: While autosave has been implemented for this version of CEGStudyBuddy it is still recommended to manually save and exit the programme to ensure there is no loss in progress.

### Notes about the Command Format

- All commands are case sensitive in nature.
- Words in `UPPER_CASE` are parameters to be supplied by the user.  
  Example: `add c/CS2113`
- Square brackets `[]` indicate optional fields.  
  Example: `edit c/CODE [t/TITLE]`
- Extra parameters in commands that don’t require them (e.g. `list`) will be ignored.

---

### Adding a Course: `add`

Adds a course to the planner.

**Format:**
```
add c/CODE t/TITLE mc/MODULAR_CREDITS y/YEAR s/SEMESTER
```

**Examples:**
```
add c/CS2040 t/Data Structures mc/4 y/2 s/1
add c/MA1101 t/Linear Algebra mc/4 y/1 s/2
```

---

### Deleting a Course: `delete`

Removes a course from the planner.

**Format:**
```
delete c/CODE
```

**Examples:**
```
delete c/CS2040
delete c/MA1101
```

---

### Listing Courses: `list`

Displays all added courses by Year and Semester.

**Format:**
```
list
```

**Example Output:**
```
Y1S1 Courses:
No courses taken!

Y1S2 Courses:
1. MA1101 - Linear Algebra (4 MCs)

Y2S1 Courses:
1. CS2040 - Data Structures (4 MCs)

Y2S2 Courses:
No courses taken!

Y3S1 Courses:
No courses taken!

Y3S2 Courses:
No courses taken!

Y4S1 Courses:
No courses taken!

Y4S2 Courses:
No courses taken!
```

---

### Editing a Course: `edit`

Modifies course details.  
At least one optional field must be provided.

**Format:**
```
edit c/CODE [t/TITLE] [mc/MODULAR_CREDITS] [y/YEAR] [s/SEMESTER]
```

**Examples:**
```
edit c/CS2040 t/Advanced Data Structures mc/5
edit c/MA1101 y/2 s/1
```

---

### Finding a Course: `find`

Searches for a course by its code.

**Format:**
```
find c/CODE
```

**Example:**
```
find c/CS2040
```

**Example Output:**
```
Course Code: CS2040  
Course Title: Data Structures and Algorithms  
Number of MCs: 4  
Year and Sem: Y2S2
```

If not found:
```
Course CS2040 not found in your course list.
```

---

### Workload Summary: `workload_summary`

Displays the total Modular Credits (MCs) per semester. Will also inform you if the total MCs in the semester is too low or high.


**Format:**
```
workload_summary
```

**Example Output:**
```
Year 1 Semester 1: 16 MCs (Too low! Minimum workload: 18 MCs)
Year 1 Semester 2: 18 MCs  
Year 2 Semester 1: 20 MCs  
Year 2 Semester 2: 16 MCs (Too low! Minimum workload: 18 MCs)
Year 3 Semester 1: 18 MCs  
Year 3 Semester 2: 16 MCs (Too low! Minimum workload: 18 MCs)
Year 4 Semester 1: 14 MCs (Too low! Minimum workload: 18 MCs)
Year 4 Semester 2: 12 MCs (Too low! Minimum workload: 18 MCs)

```
---
### Workload For: `workload_for`
Displays the courses taken for the given semester and gives the total workload.

**Format:**
```
workload_for y/YEAR s/SEMESTER
```

Example Output:
```
These are the courses you will be taking:
1.(c/CS1010 t/Programming Methodology mc/4 y/1 s/1)
2.(c/EG1311 t/Design and Make mc/4 y/1 s/1)
3.(c/CDE2501 t/Liveable Cities mc/4 y/1 s/1)
4.(c/MA1511 t/Engineering Calculus mc/2 y/1 s/1)
5.(c/MA1512 t/Differential Equations for Engineering mc/2 y/1 s/1)
6.(c/CG1111A t/Engineering Principles and Practice I mc/4 y/1 s/1)
Total workload: 20
```

---
### Workload Balance: `workload_balance`
In order to gain a better understanding of the workload balance across all you semesters, you can use this command which
displays the minimum and maximum number of courses in a semester out of all semesters. This is intended to aid in balancing the workload between semesters.
Based on your workload balance, it will also advise you to modify your workload if it detects your workload is highly unbalanced.
**Format:**
```
workload_balance
```

Example Output:
```
Min: 22
Max: 38
You have a high variance in you workload, it can be very stressful and can negatively affect your CAP
```
```
Min: 20
Max: 22
You seem to have a pretty balanced workload
```
---

### Graduation Requirement: `gradreq`

Displays the total MCs completed, the graduation requirement (160 MCs), and the number of MCs still required to graduate.

**Format:**
```
gradreq
```

**Example Output (if <160 MCs):**
```
Current MCs Completed: 100 MCs  
Graduation Requirement: 160 MCs  
Remaining MCs: 60 MCs

Oh no! You don't meet graduation requirement yet.  
Keep on going Champ! You got this! 👍
```

**Example Output (if 160 MCs or more):**
```
Current MCs Completed: 160 MCs  
Graduation Requirement: 160 MCs  
Remaining MCs: 0 MCs

Congratulations! You have met the graduation requirement! 🎓
```

---

### Help: `help`

In cases where you need information about the different commands, you can use the help command to get information about the different commands and their formats.

**Format:**
```
help
```

---

### Save Plan: `save`
Saves the current course plan, similar to a simple save in most Microsoft Office applications. Since, there is no autosave, it is highly recommneded to use this before exiting. 
This command works for both new plans and saving changes to a pre-existing plan. It does not take any parameters.

**Format:**
```
save
```
**Expected Output:**
```
Plan saved successfully.
```
---

### Switch Plan: `switch_plan`
Allows you to switch to a different course plan after following the prompted flow. This also autosaves your changes made to the plan. Still, it is recommended to save manually before exit.

It does not take any parameters.
**Format:**
```
switch_plan
```

**Expected Output:**
```
Successfully renamed
```


---n

### Delete Plan: `delete_plan`
Allows you to delete a course plan. Using this command will allow you to select a plan to delete. Once you have selected a plan
using the delete_plan command and confirm, you cannot abort the deletion. 
This command does not take any parameters.

**Format:**
```
delete_plan
```

**Expected Output:**
```
Plan deleted successfully
```

---

### Exit Program: `exit`
Exits the program after asking for confirmation. This also autosaves your changes made to the plan. Still, it is recommended to save manually before exit.

It does not take any parameters.

**Format:**
```
exit
```
**Expected Output:**
```
Bye
```

---

## Command Summary

| **Action**           | **Format**                                                                                                                 |
|----------------------|----------------------------------------------------------------------------------------------------------------------------|
| **Add**              | `add c/CODE t/TITLE mc/MODULAR_CREDITS y/YEAR s/SEMESTER`<br>E.g. `add c/CS2040 t/Data Structures mc/4 y/2 s/1`            |
| **Delete**           | `delete c/CODE`                                                                                                            |
|                      | E.g., `delete c/CS2040`                                                                                                    |
| **List**             | `list`                                                                                                                     |
| **Edit**             | `edit c/CODE [t/TITLE] [mc/MODULAR_CREDITS] [y/YEAR] [s/SEMESTER]`<br>E.g. `edit c/CS2040 t/Advanced Data Structures mc/5` |
| **Find**             | `find c/CODE`<br>E.g., `find c/CS2113`                                                                                     |
| **Help**             | `help`                                                                                                                     |
| **Workload Summary** | `workload_summary`                                                                                                         |
| **Workload For**     | `workload_for y/YEAR s/SEMESTER`                                                                                           |
| **Workload Balance** | `workload_balance`                                                                                                         |
| **Grad Req**         | `gradreq`                                                                                                                  |
| **Save Plan**        | `save`                                                                                                                     |
| **Switch Plan**      | `switch_plan`                                                                                                              |
| **Delete Plan**      | `delete_plan`                                                                                                              |

---

## FAQ

### ❓ Q: Do I need an internet connection to use the app?

**A:** Nope! 🎉  
CEGStudyBuddy is a fully offline application. No internet required.

---

### ❓ Q: What happens if I accidentally delete a course?

**A:** Unfortunately, the app doesn’t support an undo feature (yet 😢).  
Make sure to double-check before deleting. You can always re-add the course using the `add` command.

---

### ❓ Q: Can I plan courses beyond 4 years?

**A:** Currently, CEGStudyBuddy supports up to Year 4 Semester 2.  
If you wish to extend it, you can fork the project and modify the planner's backend to support more semesters!

---

### ❓ Q: Is this application open-source?

**A:** Yes! You are free to fork, contribute, and improve the application. Contributions are welcome!  
Check out the [GitHub repository](http://link.to/cegstudybuddy) for more.

---

### ❓ Q: I'm seeing errors when running the `.jar` file. What do I do?

**A:** Make sure:
- You have **Java 17** installed.
- You are running the `.jar` file from **terminal/command prompt** using `java -jar CEGStudyBuddy.jar`


### ❓ Q: What if I accidentally delete a plan?

**A:** Currently, there is delete_plan is irreversible, and it is highly recommended to be 100% sure while deleting. 

### ❓ Q: Is there autosave?

**A:** Yes, after each and every command your progress in the current plan is backedup.

