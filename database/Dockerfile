FROM mysql:8

COPY ./init.sql /docker-entrypoint-initdb.d/

ENV MYSQL_DATABASE=myapp \
    MYSQL_USER=myuser \
    MYSQL_PASSWORD=mypassword \
    MYSQL_ROOT_PASSWORD=mysecretpassword

EXPOSE 3306