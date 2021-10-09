# The AQI Index App
## The AQI Index for every states

AQI index app is an air quality indexing app for states of india.

- Created with the power of MVVM pattern
- Used [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) dependency injection library to cut out boilerplate codes


## Features

- RealTime AQI data of several states using websocket
- Used powerful [MPChart](https://github.com/PhilJay/MPAndroidChart) for graphical representation of each state aqi data

 

> App logic rely on the setting the data to ui. It just filtering the incoming
> data and and connect it to the database and then putting callback as bridge
> between database and ui. A database instrumentation test is added to test if any data is added

## Language

This project is written in Kotlin language


## Plugins

Dillinger is currently extended with the following plugins.
Instructions on how to use them in your own application are linked below.

| Plugin | README | Usage |
| ------ | ------ | ------ |
| Room | [https://developer.android.com/training/data-storage/room][PlDb] | Android room database for storing the data! |
| Navigation | [https://developer.android.com/guide/navigation][PlGh] | Used android jetpack navigation library |
| Hilt | [https://developer.android.com/training/dependency-injection/hilt-android][PlGd] | Used as dependency injection module. |
| MPAndroidChart | [https://github.com/PhilJay/MPAndroidChart][PlMe] | For graphical representation of the data |
| Coroutines | [https://kotlinlang.org/docs/coroutines-overview.html][PlGa] | For simple IO functions |



## License

Apache 2.0


[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [PlDb]: <https://developer.android.com/training/data-storage/room>
   [PlGh]: <https://developer.android.com/guide/navigation>
   [PlGd]: <https://developer.android.com/training/dependency-injection/hilt-android>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/PhilJay/MPAndroidChart>
   [PlGa]: <https://kotlinlang.org/docs/coroutines-overview.html>
