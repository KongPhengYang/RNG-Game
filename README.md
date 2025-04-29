# RNG-Game

A simple Android app that lets you “spin” for random numbers with different rarities and earn points. Spin until you reach **100 points** to win!

---

## Features

- **Five Rarity Tiers**  
  - **Common** (73.9%): numbers 1, 2, 3  
  - **Uncommon** (20%): numbers 5, 10, 50  
  - **Rare** (5%): numbers 777, 1000, 123  
  - **Epic** (1%): number 1_000_000
  - **Legendary** (0.1%): number 0
- **Animated Spin**: 20-frame cycling animation before revealing final number  
- **Point System**: Earn points per spin (ranging from 1 to 100)  
- **Scene Transitions**: Smooth UI changes using AndroidX `TransitionManager`  
- **List View**: Displays every number and its assigned point value  
- **Win Condition**: Reach 100 points to see the End screen  

---

## Requirements

- Android Studio 4.0+  
- Android SDK **API 21+** (minSdkVersion)  
- `compileSdkVersion` & `targetSdkVersion` set in `app/build.gradle`  
- Java 8 or newer  
- AndroidX AppCompat & Transition libraries 
