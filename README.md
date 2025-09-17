# MSE Starter (CSCI 412 – Assignment 1)

Two-activity Android app demonstrating explicit and implicit intents.

**Student:** Leonard Dunor (ID: 1168722)

## Features
- **MainActivity**
    - Displays student name and ID
    - Two buttons:
        - Start Activity Explicitly → opens SecondActivity with an explicit intent
        - Start Activity Implicitly → opens SecondActivity with an implicit intent
- **SecondActivity**
    - Lists five mobile software engineering challenges
    - Includes a "Main Activity" button that returns to MainActivity

## Run Information
- Tested on: Pixel 6 Emulator
- Android OS Version: API 34 (Android 14)

## Requirements mapping
- Two activities ✅
- Main shows name + student ID ✅
- Explicit intent to SecondActivity ✅
- Implicit intent via action `com.example.msestarter.SHOW_CHALLENGES` ✅
- Second lists 5+ challenges ✅
- Button returns to Main ✅
