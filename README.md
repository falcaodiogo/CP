# CP Mobile App

**CP App** is a modern application built with Material Design 3 that leverages the Comboios API and CP's public API (cp.pt) to provide real-time train tracking and scheduling. Users can view upcoming trains at a specific station, track a train's delay and location, and monitor its status (stopped, entering a station, or in transit) with live updates and a progress bar. 


## Application Concept
**CP App** is a modern Material Design 3-based application for tracking Portuguese trains with real-time information using the Comboios API and CP's public API. The app includes the following features:
- **WelcomeScreen**: Introduces the app's with a beautiful animation with haptic feedback where you can login with you Google Account.
- **HomeScreen**: Serves as the main dashboard, displaying widgets about upcoming train schedules and shortcuts for other pages.
- **NextTrainsScreen**: Shows the next trains passing through a selected station, with real-time updates on delays, arrival times, and train statuses.
- **TrainsScreen**: Allows users to follow a specific train, view its location on the line, delay, and expected arrival time at the next station.
- **StallmentsScreen** (in progress): Provides real-time train status updates, such as delays and supressions (strikes).
- **ChatBotScreen**: Offers an AI-powered chatbot for user inquiries about train schedules, delays, and other services.
- **SettingsScreen**: Enables logging off the app.

## Implemented Solution

### Architecture Overview (Technical Design)

- **1. Architecture Pattern**
 **MVVM (Model-View-ViewModel)**: Ensures separation of concerns and a scalable structure.

- **2. Authentication**
 **Google Firebase Authentication**: Enables secure Google Account sign-in.

- **3. Data Storage**
 **RoomDatabase**: Stores user data and preferences for offline access.

- **4. API Integration**
 **Retrofit**: Handles communication with the Comboios API and CP public API.
 **Token Service and Interceptor**: Ensures secure and authenticated API requests.
 **Gemini API**: Provides chatbot functionality for natural language queries.

- **5. Real-Time Updates**
 Uses progress bars and live data to show train delays, locations, and statuses (e.g., stopped, in transit).

- **6. Design**
 **Material Design 3**: Implements a modern, responsive, and user-friendly interface.


### Implemented Interactions
- Users can view next trains, track train delays and locations, monitor real-time statuses (stopped, entering, in transit), and interact with an AI-powered chatbot for train-related queries.

## Resources

**Project Resources**:  
- **Code Repository**: [GitHub](https://github.com/falcaodiogo/CP)

Here is a more visually organized way to present the design with the images side by side:

## Design

### WelcomeScreen & HomeScreen
| WelcomeScreen | HomeScreen |
|:-------------:|:---------:|
| ![image](https://github.com/user-attachments/assets/30285206-0e35-47a2-8102-74bb58b38f10) | ![image](https://github.com/user-attachments/assets/aaf968df-71c1-4355-9e2f-005a2f6ef1a6) |

### Next Trains & Search by Train
| Next Trains | Search by Train |
|:-----------:|:--------------:|
| ![image](https://github.com/user-attachments/assets/2396a625-6474-4899-a748-ba70e9786717) | ![image](https://github.com/user-attachments/assets/0fdf536b-7eeb-4f95-872b-82bfd4b23374) |

### ChatBot Screen (Beta)
| ChatBot Screen (Beta) | Settings |
|:-------------:|:---------:|
| ![image](https://github.com/user-attachments/assets/8283d18f-b0cf-4198-a3a6-3d0fe58fcd8f) | ![image](https://github.com/user-attachments/assets/e513c0d6-708a-429b-8d52-3346a0bb5a98) |
This layout arranges the images side by side for a clearer view of your design elements.


## How to run

To execute the application, you must follow the following steps

1. Clone the repository to your local machine.
2. Open the "cp" app project in Android Studio.
3. Run the application on an mobile emulator or physical device.

### Google Authentication with Firebase

To allow the google authentication please contact one of the authors to permit it.

## Author

| Name | GitHub |
| :---: | :---: |
| Diogo Falcão | [falcaodiogo](https://github.com/falcaodiogo)
