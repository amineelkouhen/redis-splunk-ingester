# Splunk-Redis Ingester
Splunk Event Ingestion Module for Redis

## Usage
Clone the Repository:
```bash
git clone https://github.com/amineelkouhen/redis-splunk-ingester.git
```

There is a docker compose script which will bootstrap all the components required to make this demo work.

1. Build the project `./gradlew build`
2. Run `docker-compose up` from the root dir
3. The containers will start in the correct order
4. On startup:
- The Redis Service will bootstrap `Redis-Stack` on port 6379
- The Ingester Service will bootstrap the Splunk-Redis Ingestion Module on Port 8686

```bash
Creating redis-stack-service ... done
Creating ingester-service    ... done
Attaching to redis-stack-service, ingester-service
```

- The Ingester Module will create the forward stream and will catch every request on Port 8686 to store the events
```bash
ingester-service | [main] c.r.s.i.configuration.JedisConfig                   : host redis - port 6379
ingester-service | [main] c.r.s.i.configuration.JedisConfig                   : stream group created: OK
ingester-service | [nio-8686-exec-1] c.r.s.i.storage.service.StoreService     : starting a transaction
ingester-service | [nio-8686-exec-1] c.r.s.i.storage.service.StoreService     : adding object 70af7e91-97b6-48d7-b499-5fdb76b666e5 to stream
ingester-service | [nio-8686-exec-1] c.r.s.i.storage.service.StoreService     : saving object 70af7e91-97b6-48d7-b499-5fdb76b666e5 as JSON
ingester-service | [nio-8686-exec-1] c.r.s.i.storage.service.ProxyService     : transaction done
```

### Software Reqs
- Docker
- Docker Compose
- Java 11+
