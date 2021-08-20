# Mobile Weather app

Application to get the weather from different random locations using MVVM architecture with the latest technologies and best practices.

## Behavior
- One view to show the weather from a random location.
- One button to refresh the weather with a new random location.

## MAD ScoreCard
Do you like Modern Android Development scores? Welcome, here it is mine:

[MAD Scoreboard](https://madscorecard.withgoogle.com/scorecard/share/1841897138/)

## Libraries
- [Dagger Hilt](https://dagger.dev/hilt/)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [View Binding](https://developer.android.com/topic/libraries/view-binding)
- [Retrofit](https://square.github.io/retrofit/)
- [Moshi](https://github.com/square/moshi)
- Testing:
    - [Mockito](https://github.com/mockito/mockito)
    - [MockK](https://github.com/mockk/mockk)
    - [Expresso](https://developer.android.com/training/testing/espresso)

## Modules
- App:
    - DI: Dagger dependencies injections.
    - Framework: OpenWeather Retrofit implementation and mappers.
    - UI: Keeps the Activities, Fragments and ViewModel logic to manage the app behavior.
    - Utils: Some extra logic shared between the different App packages.
- Data:
    - DataSources: Interfaces to define the behavior of our services.
    - Repository: Used to maintain the DataSource logic. We could have a repository that handle data from the current OpenWeather service or from local by using Room.
- Domain:
    - Model: Our business logic related to the domain.
    - Repository: Interfaces to define the high level behavior of our repositories.
- Testcore:
    Some shared classes to unify repetitive code to test Coroutines or build test objects.