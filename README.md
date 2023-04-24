# Selenium3xExamples
Selenium WebDriver 3.141.59 with Java , Maven and TestNG

Tech Stack
- Java jdk 1.8+
- Maven 3.x
- TestNG 6.x
- Selenium WebDriver


# Docker Files
 
#build images

docker build -t=jagalvanr/selenium-docker .

# Exploring the images

 docker run -it --entrypoint=/bin/sh jagalvanr/selenium-docker 

# docker compose 

Selenium Grid v.3

docker compose -f docker-compose-selenium-grid-3.yml up --scale chrome=2
docker compose -f docker-compose-selenium-grid-3.yml down

Selenium Grid v4 ( with video) 

docker compose -f docker-componse-SeleniumGrid-4.yml up --scale chrome=2
docker compose -f docker-componse-SeleniumGrid-4.yml down

 
