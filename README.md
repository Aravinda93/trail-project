# Trail project

This project has been developed using the Quarkus backend and Nuxt 3 front-end frameworks. In addition to this I have used MySQL database to store the relational information.

I have used the Docker container to set-up MySQL and phpmyadmin.


# Prerequisites
You have following supporting applications installed:
Java, JDK, Maven, Node.js, NPM, etc

# Steps to run front-end

1. Navigate to the folder `/front-end`
2. Run `npm install`
3. Run `npm run dev`

This will bring-up your Nuxt 3 based web application.

# Steps to run back-end
From the root folder run `mvn compile quarkus:dev`.