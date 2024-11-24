# **Kaizen Gaming Task**

This project is an Android application built as part of an interview process. The app displays a list of sports and their respective events fetched from a remote API. Users can mark events as favorites, filter them, and view a countdown to event start times.

---

## **Features**

- **Sports List**: Displays a collapsible list of sports, each showing its associated events.
- **Favorite Events**: Users can mark/unmark events as favorites and filter to show only favorites.
- **Real-Time Countdown**: Shows a live countdown timer for each event until it starts, or an appropriate message if the event is live or has passed.
- **State Persistence**: Favorites are stored in a local Room database and persist across app sessions.
- **Responsive UI**: Built with Jetpack Compose for a modern and responsive user interface.

---

## **Technologies Used**

- **Programming Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Dependency Injection**: Hilt
- **Networking**: Retrofit
- **JSON Parsing**: GSON
- **Local Storage**: Room Database
- **State Management**: StateFlow and MutableStateFlow
- **Coroutines**: For asynchronous operations
- **Unit Tests**: JUnit, Mockk, kotlinx-coroutines-test
- **UI Tests**: Jetpack Compose Testing Framework

---

## **Project Structure**

```plaintext
├── data                 # Handles API and local database interactions
│   ├── api              # Retrofit service and data models
│   ├── db               # Room database and DAO interfaces
│   ├── repository       # Repository for data management
├── domain               # Contains business logic
│   ├── models           # Domain-level models
│   ├── usecases         # Use cases for fetching and managing data
├── ui                   # Jetpack Compose UI components
│   ├── view             # Composables and activities
│   ├── viewmodel        # ViewModel for state and UI interaction
├── utils                # Utility classes and extensions
└── tests                # Unit and UI tests
