# Setup

This is the rental tool app. It requires JAVA_HOME be set to Java 17+.

The app can be run directly from an IDE by selecting run on RentaltoolApplication (Tested with IntelliJ). However,
the experience will be dependent on the terminal the IDE decides to use. IntelliJ's doesn't support the up arrow and
history.

Using a command prompt, the app ships with a maven wrapper, so you should be able to package it with:
```
    .\mvnw package
```

Then it can be run, either with maven:
```
    .\mvnw exec:java
```

Or from the jar:
```
    %JAVA_HOME%\bin\java -jar target\rentaltool-0.0.1-SNAPSHOT.jar
```


## Double Holidays?
The requirements say that July 4th can be observed on a different day if it's on the weekend. Would the actual day
count as a holiday for charging purposes as well? Default behavior is NO. But it can be changed by setting the property 
`dh` to true. This can be changed with a JVM argument on boot. 
```
    %JAVA_HOME%\bin\java -jar target\rentaltool-0.0.1-SNAPSHOT.jar dh=true"
```

## Tests
Tests can be run separately with
```
    .\mvnw test
```