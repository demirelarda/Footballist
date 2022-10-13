# Footballist

## Description 

* This is an app for football fans. And ones who are interested in football.
* You can see the recent news about football in the app.
* You can see the "Football Dictionary" which contains a lot of data about football leagues, teams, and players.

## Technical Details

* For football data, app uses api-football by api-sports. (https://rapidapi.com/api-sports/api/api-football)
* For news data, app uses sports news api by news api. (https://newsapi.org)
* MVVM pattern used in this app.
* Room, Retrofit, RxJava, Glide, Coil used.
* Used Room for a local database and saving the datas in device for 30 minutes. After 30 minutes datas are automatically updating by api.
* Used Room and Custom Shared Preferences for caching. And those reduce the api calls.


## Football Dictionary Structure:

![ssChart](https://user-images.githubusercontent.com/93993257/195689394-62618e19-6fc2-44ec-bb5b-6f411ab45cf0.PNG)


## Build

* Get api key for sports data from api-sports : https://rapidapi.com/api-sports/api/api-football
* Get api key for news data from api-news : https://newsapi.org
* Put your api keys to `utils/Constants.kt` (Football api to :  `API_KEY` and news api to `NEWS_API_KEY`)
