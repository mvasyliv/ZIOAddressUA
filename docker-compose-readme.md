# PostgreSQL to docker compose
1.1. docker-compose up   - we can start Postgres with just one simple command
1.2. docker-compose up -d   - run Postgres in the background with this command, adding -d Detached mode

docker-compose down   - stop the Postgres service running

Для docker-composeправильного способу видалення томів буде docker-compose down --volumes
або 
docker-compose down --rmi all --volumes.


docker-compose -f docker-compose-pg-only.yml


Щоб просто запустити контейнер за допомогою образу Postgres 14.1 alpine, ми можемо виконати таку команду:

docker run --name basic-postgres --rm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=4y7sV96vA9wv46VR -e PGDATA=/var/lib/postgresql/data/pgdata -v /tmp:/var/lib/postgresql/data -p 5432:5432 -it postgres:14.1-alpine

Контейнер запущений і готовий приймати підключення. Якщо ми запустимо наступну команду, ми зможемо зайти всередину контейнера та виконати psqlкоманду, щоб побачити postgresбазу даних, яка збігається з іменем користувача, указаним у змінній події.
docker exec -it basic-postgres /bin/sh


sudo chmod 777 /var/run/docker.sock

<!-- sudo rm -r -f postgres-data/  --> remove folder


https://gist.github.com/onjin/2dd3cc52ef79069de1faa2dfd456c945    - run init database sql

