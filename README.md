# TaskManager APP

## OverView
TaskManager is an Android app for managing tasks, built with Jetpack Compose. 
Users can add, edit, complete, and delete tasks, with changes saved locally using Room. 
Firebase Analytics tracks task actions, while Crashlytics monitors crashes, including manual and database tests.


## Features
Fetch tasks from a mock API .
Display tasks in a RecyclerView, with options to add, edit, and mark as completed.
Persist tasks locally using Room Database.
Track events and crashes with Firebase Analytics and Crashlytics.
Monitor network performance via Firebase Performance Monitoring.


## Tech Stack
Languages: Kotlin
UI: Jetpack Compose
Networking: Retrofit
Database: Room Persistence Library
Firebase: Analytics, Crashlytics, Performance Monitoring
Architecture: MVVM
Build Tool: Gradle

## Explanation
The app’s design is concise and easy to understand, prioritizing a clean UI and intuitive task management.

**Adding a Task:** Click the Floating Action Button (FAB) at the bottom—a dialog appears where you can input and save a new task.
**Editing a Task:** After adding, tap the "Edit" button on the right of any task to modify it.
**Deleting a Task:** Use the "Delete" button next to each task to remove it.
**Firebase Crash:** The warning button (⚠️) at the top triggers a manual crash (e.g., null pointer exception) for Crashlytics testing.
**Room Crash:** The info button triggers a Room Database error (e.g., invalid query) to simulate a persistence crash.
This approach ensures users can quickly grasp the app’s functionality, while the crash triggers provide clear testing for Firebase integration.

## APP SCREENSHOTS AND RECORDING

**RECORDING**
[App_Recording](https://drive.google.com/file/d/1VcjP8NmitK-vGVmiFE1uvKBzHPQ2ojgZ/view?usp=sharing)


**SCREENSHOTS**
<img src="https://github.com/user-attachments/assets/5e2b5a79-e62a-4ac0-8ed2-525f0da357f0" alt="First Screen" width="300"/> <img src="https://github.com/user-attachments/assets/b3ee4ccf-61f8-4f11-8e61-b3841e2383e2" alt="Editing" width="300"/> <img src="https://github.com/user-attachments/assets/8bce9aad-457b-43c1-885c-0ed7a5e1e989" alt="Deleting" width="300"/> <img src="https://github.com/user-attachments/assets/7d1049d3-86ac-42e8-b72a-c15a25cfb3c0" alt="Completed" width="300"/> <img src="https://github.com/user-attachments/assets/bd74cf07-83ba-447b-9dae-0315bff02571" alt="Add Task" width="300"/> <img src="https://github.com/user-attachments/assets/7737b7d3-f565-4d70-a596-50612e1fffca" alt="Adding" width="300"/>



## Firebase Screenshots

**Firebase Crashlytics**
![image](https://github.com/user-attachments/assets/4a60234d-08ae-4942-8fbe-7887089e4eda)


**Firebase Analytics Events**
![image](https://github.com/user-attachments/assets/284e40cc-e409-45ae-a304-e77ac8add8f5)
![image](https://github.com/user-attachments/assets/20c82942-74ce-4bca-90cb-38bfe2782d46)











