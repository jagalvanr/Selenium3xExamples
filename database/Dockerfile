FROM mysql:8

COPY ./init.sql /docker-entrypoint-initdb.d/

ENV MYSQL_DATABASE=testdb \
    MYSQL_USER=jagalvanr \
    MYSQL_PASSWORD=S0f773k.001 \
    MYSQL_ROOT_PASSWORD=S0f773k.001

COPY init.sql /data.sql

CMD ["bash", "-c", "mysql -ujagalvanr -pS0f773k.001 testdb < /data.sql"]