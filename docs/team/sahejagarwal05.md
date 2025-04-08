# Sahej Agarwal - Project Portfolio Page

## Project: CEGStudyBuddy

CEGStudyBuddy is a desktop application designed specifically for NUS Computer Engineering students to efficiently track and plan their university courses. The application helps students optimize their workloads, manage multiple course-planning scenarios, ensure graduation requirements, and maintain organization throughout their academic journey.

Given below are my contributions to the project.

### New Feature: Storage Manager
- **What it does:** Manages all aspects of data storage, ensuring reliable saving, loading, and management of multiple course plans.
- **Initialization:** Allows users to select an existing course plan from saved options or create a new plan when the application is first started, offering an intuitive starting point.
- **Creation:** Enables users to effortlessly create new course plans, providing flexibility and encouraging exploration of diverse academic planning scenarios.
- **Switching:** Supports seamless and intuitive switching between different saved plans, automatically saving the current plan state to maintain consistency and prevent data loss.
- **Renaming:** Provides a simple interface to rename existing course plans, ensuring clear differentiation and easier organization when managing multiple plans.
- **Deletion:** Allows straightforward deletion of outdated or unnecessary plans, enhancing user control. If the current active plan is deleted, the system intelligently triggers re-initialization, preserving usability.
- **Saving and Autosave:** Offers comprehensive manual save options and incorporates an autosave feature that regularly and automatically saves user progress, safeguarding against accidental data loss or interruptions.
- **Justification:** Critical for maintaining data integrity and enhancing user experience by providing robust and comprehensive data management capabilities that support complex and varied academic planning.
- **Highlights:** Required extensive analysis of potential design alternatives and careful modularization. Significantly improved object-oriented design by decoupling UI-specific functions from the core storage logic, resulting in increased maintainability and ease of future enhancements. Implemented intuitive `toSaveFormat` methods that store data in human-readable formats within text files, facilitating manual editing and verification.
- **Testing and Error Handling:** Developed and implemented comprehensive automated tests for all storage functionalities. This included robust error handling for edge cases, automatic directory creation, and informative error messages to guide users effectively.
- **Documentation:** Created extensive documentation in both the User Guide (UG) and Developer Guide (DG). Provided step-by-step user instructions, frequently asked questions for manual data editing, troubleshooting advice, and detailed explanations of each method and component involved in the storage process.
- **Code contributed:** [StorageManager.java](https://github.com/AY2425S2-CS2113-F14-2/tp/blob/master/src/main/java/studybuddy/data/storage/StorageManager.java)

### New Feature: Workload_for Command
- **What it does:** Allows users to obtain a detailed breakdown of courses and associated workloads for a specified semester.
- **Justification:** Enhances course planning clarity, helping users make informed decisions.
- **Highlights:** Designed initial command parsing logic which was later optimized and integrated into the centralized Parser class.
- **Documentation:** Added detailed instructions and implementation notes to both UG and DG.
- **Code contributed:** [WorkloadForCommand.java](https://github.com/AY2425S2-CS2113-F14-2/tp/blob/master/src/main/java/studybuddy/commands/WorkloadForCommand.java)

### New Feature: Workload_balance Command
- **What it does:** Provides students with recommendations to maintain consistent and balanced workloads across semesters based on practical advice from senior students.
- **Justification:** Supports sustainable academic planning, preventing workload peaks and improving long-term student performance.
- **Documentation:** Included thorough documentation in UG and DG, detailing usage scenarios and implementation insights.
- **Code contributed:** [WorkloadBalanceCommand.java](https://github.com/AY2425S2-CS2113-F14-2/tp/blob/master/src/main/java/studybuddy/commands/WorkloadBalanceCommand.java)

### Contributions to Common Infrastructure
- Developed essential foundational classes including `Command`, `CEGStudyBuddy`, `CEGStudyBuddyException`, and constant definitions for command names.
- Enhanced object-oriented programming practices, improving the overall software structure.
- Provided detailed documentation in both UG and DG for all common classes created.

### Project Management & Minor Enhancements
- Conducted incremental improvements in UI and overall usability.
- Implemented user-friendly exit confirmation to prevent accidental termination of sessions.
- Contributed significantly towards comprehensive JavaDocs and rigorous testing coverage to enhance code reliability.

My overall project dashboard, including specific commits and contribution details, is available at [Sahej's TP Dashboard](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=functional-code&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=SahejAgarwal05&tabRepo=AY2425S2-CS2113-F14-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false).

