# Setup

This is the rental tool app. It requires Java 17.

The app ships with a maven wrapper, so you should be able to start it with:
```
    .\mvnw spring-boot:run
```

It can also be run from a jar:
```
    .\mvnw package
    %JAVA_HOME%\bin\java -jar target\rentaltool-0.0.1-SNAPSHOT.jar
```

## Double Holidays?
The requirements say that July 4th can be observed on a different day if it's on the weekend. Would the actual day
count as a holiday for charging purposes as well? Default behavior is NO. But it can be changed by setting the property 
`rentaltool.doubleHoliday` to true. This can be changed in `application.propteries`, or with a JVM argument on boot. 
```
    .\mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Drentaltool.doubleHoliday=true"
    Or
    %JAVA_HOME%\bin\java -Drentaltool.doubleHoliday=true -jar target\rentaltool-0.0.1-SNAPSHOT.jar
```

## Tests
Tests can be run separately with
```
    .\mvnw test
```