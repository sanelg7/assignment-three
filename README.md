# assignment-three
Repository for DefineX Spring Practicum - Assignment 3

# Outline
This project uses Visual Crossing Weather API to retrieve daily, weekly and monthly weather data and show it on a basic html web page. Demonstrated basic **validation** and **exception handling**.

# How to run:

1. Clone the project using HTTPS or SSH:
HTTPS
```
git clone https://github.com/sanelg7/assignment-three.git
```
SSH
```
git clone git@github.com:sanelg7/assignment-three.git
```
2.You need to create your own 'api.connection.properties' file inside :
```
src/main/resources/templates
```
This should include two lines:
```
weather.api.key=&key=<your-api-key>
weather.api.base.url=<base-url-for-visualcrossing-weather-timeline-api>
```

3. You will see two files, **mvnw** and **mvnw.cmd**. These are maven wrappers and can be used to run the spring boot project.
Go into the build path.
For Windows:
```
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```
For Linux/MacOS
```
> ./mvnw clean install
> ./mvnw spring-boot:run
```

4. Go to your browser and enter 
```
localhost:8080/home
```
# Screenshots:
Homepage
![image](https://user-images.githubusercontent.com/26389575/217628463-4e41cf01-eee3-4d6a-b841-883b6fa84a64.png)

Hourly Weather Report for Today:
![image](https://user-images.githubusercontent.com/26389575/217628577-ad126432-d5b0-41e7-96a6-bb89a3d5945c.png)

Daily Weather Report for Week:
![image](https://user-images.githubusercontent.com/26389575/217628783-5f74fafd-1738-43c3-92c5-f4a77cd6fb3f.png)

*Month is similar but with more rows


## What is used?
-Java 17
-Spring Boot
-Lombok
-Thymeleaf
-Spring AOP
-Hibernate Validator

## TODO
Will include geolocation for the home page. Which is not required by the asasignment.
