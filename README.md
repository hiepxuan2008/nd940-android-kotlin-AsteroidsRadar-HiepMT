# Asteroid Radar Project

Course link: https://www.udacity.com/course/android-kotlin-developer-nanodegree--nd940

## Description

Asteroid Radar is an app to view the asteroids detected by NASA that pass near Earth, you can view all the detected asteroids in a period of time, their data (Size, velocity, distance to Earth) and if they are potentially hazardous.

#### Requirements

- Main Screen: Display list of all the detected asteroids & show the NASA image of the day
- Detail screen: Display the data of that asteroid once it's selected in the Main screen list
- The asteroids and NASA image of the day are downloaded from the NASA API under https://api.nasa.gov/
- The app can save the downloaded asteroids in the database and then display them also from the database.
- Support filter asteroids by: Today, This Week or All saved.
- The app downloads the next 7 days asteroids and saves them in the database once a day using WorkManager with requirements of internet connection and device plugged in. The app can display saved asteroids from the database even if internet connection is not available.
- The app works correctly in talk back mode, it provides descriptions for all the texts and images: Asteroid images in details screen and image of the day. It also provides description for the details screen help button.

#### Bonus requirements

- Modify the app to support multiple languages, device sizes and orientations.
- Make the app delete all asteroids before today once a day from the same workManager that downloads the asteroids.
- Provide styles for the details screen subtitles and values to make it consistent, and make it look like in the designs.


#### Techical Skills

- MVVM + ViewModel + LiveData + Data Binding
- Room Database
- Retrofit
- Kotlin Coroutines
- WorkManager
- RecyclerView
- Single activity vs Multiple fragments
- Navigation component
- Support multiple languages

## How to build the app

1. Clone this repository.
2. Open `starter` folder via Android Studio IDE
3. Go to `gradle.properties` file, replace with your api key under `NASA_API_KEY` variable

## Demo

#### Demo main features
https://user-images.githubusercontent.com/6292433/120095850-6e7b8580-c152-11eb-987b-c3664187d8e1.mp4


#### Support multiple languges
https://user-images.githubusercontent.com/6292433/120095866-8bb05400-c152-11eb-94ca-05d034e01557.mp4


## License

Mai Thanh Hiep & Udacity

