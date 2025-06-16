# CP Mobile App

**CP App** is a modern application built with a combination of Material Design 3 and Material Design 3 Expressive that uses the [Comboios API](https://github.com/juliuste/comboios) and [CP's public API (cp.pt)](https://www.cp.pt/passageiros/pt/consultar-horarios/proximos-comboios) to provide real-time train tracking and scheduling. Users can view upcoming trains at a specific station, track a train's delay and location, and monitor its status (stopped, entering a station, or in transit) with live updates and a progress bar. 

## Terms and Conditions

This project is created for educational purposes and personal use only. It utilizes the CP API strictly for coding experiments and non-commercial projects. This application is **not used in any production environment** or for any business-related activities.

Additionally, I have reached out to CP for permission to use the API in this manner. If there are any concerns, please feel free to contact me, and I will address them promptly.

## Application Concept
**CP App** is a Material Design 3-based application for tracking Portuguese trains with real-time information using the Comboios API and CP's public API. The app includes the following features:
- **WelcomeScreen**: Introduces the app's with an animation with haptic feedback where you can login with you Google Account or visit the CP's website.
- **HomeScreen**: Serves as the main dashboard, displaying widgets about upcoming train schedules, your saved trains and shortcuts for other pages.
- **NextTrainsScreen**: Shows the next trains passing through a selected station, with real-time updates on delays, arrival times, and train statuses. When you click on any train card, you can also go directly to that train page information (TrainsScreen).
- **TrainsScreen**: Allows users to follow a specific train, view its location on the line, delay, if is it supressed and expected arrival time at the next station with real time information.
- **ChatBotScreen**: Offers an AI-powered chatbot for user inquiries about train schedules, delays, and other services using Google Gemini.
- **SettingsScreen**: Enables logging off the app, your 4 past notifications and other usefull infos about the app.

## Implemented Solution

### Architecture Overview (Technical Design)

- **1. Architecture Pattern**
 **MVVM (Model-View-ViewModel)**: Ensures separation of concerns and a scalable structure.

- **2. Authentication**
 **Google Firebase Authentication**: Enables secure Google Account sign-in and the Gemini chatbot experience.

- **3. Data Storage**
 **RoomDatabase**: Stores user data and preferences for offline access.

- **4. API Integration**
 **Retrofit**: Handles communication with the Comboios API and CP public API.
 **Token Service and Interceptor**: Ensures secure and authenticated API requests.
 **Google Services API**: Google account loggin.
 **Gemini API**: Provides chatbot functionality for natural language queries.
 **Google Maps**: See the real time location of a train in a map.

- **5. Real-Time Updates**
 Uses progress bars and live data to show train delays, locations, and statuses (e.g., stopped, in transit).

- **6. Design**
 **Material Design 3 and Material Design 3 Expressive**: Implements a modern, responsive, and a very user-friendly interface with white and dark mode, accordingly to the system theme.


## Resources

**Project Resources**:  
- **Code Repository**: [GitHub](https://github.com/falcaodiogo/CP)

## Design

### WelcomeScreen & HomeScreen
| WelcomeScreen | HomeScreen |
|:-------------:|:---------:|
| <img src="https://github.com/user-attachments/assets/30285206-0e35-47a2-8102-74bb58b38f10" width="300"/> | <img src="https://github.com/user-attachments/assets/c6fd0974-50fe-435a-8971-e1bf514ab1f9" width="300"/> |

### Next Trains & Search by Train
| Next Trains | Search by Train (1) |
|:-----------:|:--------------:|
| <img src="https://github.com/user-attachments/assets/2396a625-6474-4899-a748-ba70e9786717" width="300"/> | <img src="https://github.com/user-attachments/assets/4ad9093f-5118-4566-8953-0ddc4d57f19e" width="300"/> |

### Search by Train
| Next Train (2) | Search by Train (3) |
|:-----------:|:--------------:|
| <img src="https://github.com/user-attachments/assets/eb9c496c-65a8-46fc-96c1-158b704de58e" width="300"/> | <img src="https://github.com/user-attachments/assets/1483996c-be5e-4872-896b-f7cc5eec2cd9" width="300"/> |

### Notifications
| Notifications example | Strike Warning |
|:-------------:|:---------:|
| <img src="https://github.com/user-attachments/assets/9fa5e337-d0b9-461c-8d54-c32134c24866" width="300"/> | <img src="https://github.com/user-attachments/assets/bd6b3e1a-e0cb-46e4-9925-3e81d388d500" width="300"/>  |

### ChatBot Screen (Beta)
| ChatBot Screen (Beta) | ChatBot Screen (Beta) |
|:-------------:|:---------:|
| <img src="https://github.com/user-attachments/assets/96773366-baaf-42e1-b095-6219fdf9b76f" width="300"/> | <img src="https://github.com/user-attachments/assets/dbb52bff-2ce5-48aa-8a2f-fbeedbe35473" width="300"/> | 

### Strike notification and themed icon
| Themed Icon | Settings |
|:-------------:|:---------:|
| <img src="https://github.com/user-attachments/assets/9b3e3353-f8b8-4482-acbc-704dc84876be" width="300"/> | <img src="https://github.com/user-attachments/assets/cef461b8-8008-479c-916d-da9608ffc045" width="300"/> |

## How to run

To execute the application, you must follow the following steps

1. Clone the repository to your local machine.
2. Open the "cp" app project in Android Studio.
3. Make sure you create an Gemini API key and a Google Services API key.
4. Run the application on an mobile emulator or physical device.

Or

1. Download the APK available in the releases tab for Android 14 or higher

## Author

| Name | GitHub |
| :---: | :---: |
| Diogo Falcão | [falcaodiogo](https://github.com/falcaodiogo)
