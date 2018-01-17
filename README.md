# playlist-weather
# iFood Backend Advanced Test

Create a micro-service able to accept RESTful requests receiving as parameter either city name or lat long coordinates and returns a playlist (only track names is fine) suggestion according to the current temperature.

## Business rules

* If temperature (celcius) is above 30 degrees, suggest tracks for party
* In case temperature is between 15 and 30 degrees, suggest pop music tracks
* If it's a bit chilly (between 10 and 14 degrees), suggest rock music tracks
* Otherwise, if it's freezing outside, suggests classical music tracks 

## Hints

You can make usage of OpenWeatherMaps API (https://openweathermap.org) to fetch temperature data and Spotify (https://developer.spotify.com) to suggest the tracks as part of the playlist.

## Non functional requirements

As this service will be a worldwide success, it must be prepared to be fault tolerant, responsive and resilient.

Use whatever language, tools and frameworks you feel comfortable to, and briefly elaborate on your solution, architecture details, choice of patterns and frameworks.

Also, make it easy to deploy/run your service(s) locally (consider using some container/vm solution for this). Once done, share your code with us.

## Required tools
    Java 8 or newer
    Docker(optional)

## Instructions

1.  Build the application 

```
./mvnw package
  ```

2.  Build docker image(optional)

```
./mvnw dockerfile:build
```

3.  Run the application
   *  The aplication can be run by using docker or direclty with maven
      *  Docker
      ```
      docker run -p 5000:5000 -t playlist-weather/playlist-weather:latest
      ```

      *  Maven

      ```
      ./mvnw spring-boot:run
      ```
      
## Using the service
* Getting tracks by city name
  * Local
  ```
  Example http://localhost:5000/playlist?city=Goiania
  ```
  * Remote 
  ```
  Example http://playlist.bretas.me/playlist?city=Goiania
  ```
* Getting tracks by coordinates
  * Local
  ```
  Example http://localhost:5000/playlist?lon=-49.25&lat=-16.68
  ```
  * Remote 
  ```
  Example http://playlist.bretas.me/playlist?lat=-49.25&lon=-16.68
  ```
  
## Deployment to Elastic Beanstalk
* First Deployment to environment speficied in pom.xml
```
./mvnw beanstalk:upload-source-bundle beanstalk:create-application-version beanstalk:create-environment
```
* Update environment speficied in pom.xml
```
./mvnw beanstalk:upload-source-bundle beanstalk:create-application-version beanstalk:update-environment
```

  
  
