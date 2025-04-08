# CEGStudyBuddy User Guide

## Introduction

**CEGStudyBuddy** is a desktop application that tracks and plans university courses efficiently for NUS Computer Engineering students who want a structured academic roadmap.  
It helps to optimise workload, ensure graduation requirements, and keep students organised with ease, while managing multiple scenarios and possible schedules in their academic journey.

This application is optimised for a **Command Line Interface (CLI)**.  
If you are a fast typer, you can plan and track your courses faster than NUSMods and boost you CLI skills at the same time!

---
<div style="page-break-after: always;"></div>

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
```


6. Start typing commands and hit `Enter`.

Refer to the **Features** section below for all available commands.

---
<div style="page-break-after: always;"></div>

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

### Notes about CEG Mandatory Courses

CEGStudyBuddy comes with a full list of pre-defined courses that are mandatory in the NUS Computer Engineering curriculum.

You may simply add these courses without having to specify the title and number of MCs (see Adding a Course below).

---

### Adding a Course: `add`

Adds a CEG required course to the planner.

#### CEG Required Courses
This applies to courses in the pre-defined list of CEG required courses.

**Format:**
```
add c/CODE y/YEAR s/SEMESTER
```

**Examples:**
```
add c/CS1010 y/1 s/1
```
**Example Output:**
```
Course added: CS1010 Programming Methodology (4 MCs)
```

You may view the list of pre-defined courses [here](https://github.com/AY2425S2-CS2113-F14-2/tp/blob/master/src/main/resources/data/Defined_Courses).

---

#### Non-CEG Required Courses

This applies to courses that are not in the pre-defined list of CEG required courses, e.g. GE-coded courses, unrestricted electives.

**Format:**
```
add c/CODE t/TITLE mc/MODULAR_CREDITS y/YEAR s/SEMESTER
```

**Examples:**
```
add c/GEC1005 t/Cultural Borrowing: Japan and China mc/4 y/2 s/1
```
**Example Output:**
```
Course added: GEC1005 Cultural Borrowing: Japan and China (4 MCs)
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

### Replacing a Course: `replace`

Replaces a course in the planner with a different course.\
This may be helpful if you are switching between different course variants or GE modules.

**Format:**
```
replace c/OLD CODE c/NEW CODE t/TITLE mc/MODULAR_CREDITS y/YEAR s/SEMESTER
```

**Example:**
```
replace c/GESS1005 c/GESS1025 t/Singapore: Imagining the Next 50 Years mc/4 y/2 s/2
```

This command does not use the pre-defined list of CEG required courses. You must include all parameters when using this command.

---

### Add a Placeholder Course: `dummy`

Adds a dummy course in the planner to hold some MCs.\
This may be helpful if you want to take courses worth of some MCs but have not decided on the exact courses to take.\
A maximum of 20 dummy courses can exist in the same planner at the same time. 

**Format:**
```
dummy mc/MODULAR_CREDITS y/YEAR s/SEMESTER
```

**Example:**
```
dummy mc/8 y/4 s/2
```

**Example Output:**

If this is the 3rd dummy added:
```
Dummy course added: DUM02 - To be confirmed (8 MCs)
```

If this is the 21st dummy added:
```
Reached maximum number of dummies, please delete dummies before adding
```

The `dummy` command cannot be undone by `undo`. To undo dummy, please use `delete`.

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

### Check Course Pre-requisites: `prereq`

Lists the pre-requisites of a given course.

**Format:**
```
prereq c/CODE
```

**Example:**
```
prereq c/CS2040C
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
---- Workload Summary ----
Here is a summary of your workload for all the semesters.
Year 1 Semester 1: 20MCs 
Year 1 Semester 2: 18MCs 
Year 2 Semester 1: 30MCs (Too high, please appeal for waiver! Maximum workload: 28 MCs)
Year 2 Semester 2: 8MCs (Too low! Minimum workload: 18 MCs)
Year 3 Semester 1: 0MCs (Too low! Minimum workload: 18 MCs)
Year 3 Semester 2: 0MCs (Too low! Minimum workload: 18 MCs)
Year 4 Semester 1: 0MCs (Too low! Minimum workload: 18 MCs)
Year 4 Semester 2: 0MCs (Too low! Minimum workload: 18 MCs)

You currently have 76 MCs in total
You still need 84 MCs to reach the minimum graduation requirement of 160 MCs

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
These are the courses that you are currently taking in year 1 semester 2:
1. CS2113 - Software Engineering & Object-Oriented Programming (4 MCs)
2. ES2631 - Critique and Communication of Thinking and Design (4 MCs)
3. CG2023 - Signals & Systems (4 MCs)
4. CS1231 - Discrete Structures (4 MCs)
5. CDE2501 - Liveable Cities (4 MCs)
6. MA1511 - Engineering Calculus (2 MCs)

Your total workload for year 1 semester 2 is: 22MCs
```

---
### Workload Balance: `workload_balance`
In order to gain a better understanding of the workload balance across all you semesters, you can use this command which
displays the minimum and maximum number of courses in a semester out of all semesters.\
This is intended to aid in balancing the workload between semesters.

Based on your workload balance, it will also advise you to modify your workload if it detects your workload is highly unbalanced.

**Format:**
```
workload_balance
```

**Example Output:**
```
Min: 22
Max: 38
You have a high variance in you workload. This can be very stressful and can negatively affect your CAP.
```
```
Min: 20
Max: 22
You seem to have a pretty balanced workload.
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

### Undo Last Command: `undo`

If you accidentally run a command that you would like to undo, you may run this command.
The undo command works for the following operations (Undo the following):
- Add
- Delete
- Edit
- Replace

**Format:**
```
undo
```

---

### Save Plan: `save`
Saves the current course plan, similar to a simple save in most Microsoft Office applications.\
Since, there is no autosave, it is highly recommended to use this before exiting.

This command works for both new plans and saving changes to a pre-existing plan.

**Format:**
```
save
```

---

### Switch Plan: `switch_plan`
Allows you to switch to a different course plan after following the prompted flow. This also automatically saves your changes made to the plan. Still, it is recommended to save manually before exit.

**Format:**
```
switch_plan
```

---

### Delete Plan: `delete_plan`
Allows you to delete a course plan. Using this command will allow you to select a plan to delete.\
Once you have selected a plan
using the delete_plan command and confirm, you cannot abort the deletion. 

**Format:**
```
delete_plan
```

---

### Rename Plan: `rename_plan`
Allows you to rename the current plan.

**Format:**
```
rename_plan
```

---

### Exit Program: `exit`
Exits the program after asking for confirmation. This also automatically saves your changes made to the plan. Still, it is recommended to save manually before exit.

It does not take any parameters.

**Format:**
```
exit
```

---

### Summary Command: `summary`
Outputs the summary of all the commands executed both valid and error commands

It does not take any parameters.

**Format:**
```
summary
```
**Example Output:**
```
---- Command Summary ----
1. add c/EE2211 y/1 s/1
2. list
3. delete c/EE2211
```

### Manual Data Editing

This is only recommended for advanced users. All data is stored in the PlanData folder in .txt files of the format - `PLANNAME.txt`. Each file has lines in the format `course c/CODE t/TITLE mc/MODULAR_CREDITS y/YEAR s/SEMESTER`. you can change any of the parameters as long as the changes comply. Rest assured, if your changes are incompatible, they can still be manually reverted and the the program will not delete the plan unless asked to do so.

At the same time, it is advised to take caution while changing data manually.


---
## Command Summary

| **Action**           | **Format**                                                                                                                                    |
|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**              | `add c/CODE t/TITLE mc/MODULAR_CREDITS y/YEAR s/SEMESTER`<br>E.g. `add c/CS2040 t/Data Structures mc/4 y/2 s/1`                               |
| **Delete**           | `delete c/CODE`<br>E.g. `delete c/CS2040`                                                                                                     |
| **List**             | `list`                                                                                                                                        |
| **Edit**             | `edit c/CODE [t/TITLE] [mc/MODULAR_CREDITS] [y/YEAR] [s/SEMESTER]`<br>E.g. `edit c/CS2040 t/Advanced Data Structures mc/5`                    |
| **Find**             | `find c/CODE`<br>E.g., `find c/CS2113`                                                                                                        |
| **Placeholder**      | `dummy mc/MODULAR_CREDITS y/YEAR s/SEMESTER`<br>E.g. `dummy mc/4 y/2 s/1`                                                                     |
| **Help**             | `help`                                                                                                                                        |
| **Workload Summary** | `workload_summary`                                                                                                                            |
| **Workload For**     | `workload_for y/YEAR s/SEMESTER`                                                                                                              |
| **Workload Balance** | `workload_balance`                                                                                                                            |
| **Grad Req**         | `gradreq`                                                                                                                                     |
| **Save Plan**        | `save`                                                                                                                                        |
| **Switch Plan**      | `switch_plan`                                                                                                                                 |
| **Delete Plan**      | `delete_plan`                                                                                                                                 |
| **Rename Plan**      | `rename_plan`                                                                                                                                 |
| **Replace a Course** | `replace c/OLDCODE c/NEWCODE t/TITLE mc/MODULAR_CREDITS y/YEAR s/SEMESTER`<br>E.g. `replace c/CS2040 c/EE2026 t/Digital Design mc/4 y/2 s/1`  |
| **Pre Requisite**    | `prereq c/CODE`<br>E.g. `prereq c/CS2113`                                                                                                     |
| **Summary**          | `summary`                                                                                                                                     |
| **Undo**             | `undo`                                                                                                                                        |
| **Exit**             | `exit`                                                                                                                                        |
---

## FAQ

### ❓ Q: Do I need an internet connection to use the app?

**A:** Nope! 🎉  
CEGStudyBuddy is a fully offline application. No internet required.

---

### ❓ Q: What happens if I accidentally delete a course?

**A:** You can use `undo` to undo this action. 
In fact, you can undo `delete`, `add` and `replace`.

---

### ❓ Q: What happens if I have not decided on which exact course to take?

**A:** You can add a placeholder course and delete/replace it later. 
As long as you know how many MCs you plan to take, you can add the placeholder using `dummy`. 

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

**A:** Yes, after each and every command your progress in the current plan is backed up. But, manually saving the progress is still recommended.

