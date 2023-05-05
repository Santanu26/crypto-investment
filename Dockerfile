FROM amazoncorretto:17-alpine3.14
COPY prices /Users/Santanu_Barua/Documents/assignment/recommendation-system/prices
ADD target/*.jar recommendation-system.jar
ENTRYPOINT ["java", "-jar", "recommendation-system.jar"]
