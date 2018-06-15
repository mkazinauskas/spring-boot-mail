# spring-boot-mail
Spring boot mail sample

# How to run

1. `docker-compose up -d`
2. `./gradlew bootRun`
3. Check sent mails: `docker volume ls -q | docker volume inspect $(grep smtp_data)` and go to `"Mountpont"` directory
Example command output:
```$xslt
[root@localhost]# docker volume ls |  docker volume inspect $(grep smtp_data)
[
    {
        "CreatedAt": "2018-06-15T09:16:41+03:00",
        "Driver": "local",
        "Labels": {
            "com.docker.compose.project": "springbootmail",
            "com.docker.compose.volume": "smtp_data"
        },
        "Mountpoint": "/var/lib/docker/volumes/springbootmail_smtp_data/_data",
        "Name": "springbootmail_smtp_data",
        "Options": {},
        "Scope": "local"
    }
]
```

# How to stop
1. `docker-compose down`