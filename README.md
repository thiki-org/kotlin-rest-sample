kotlin-rest-sample
==================

# Guide to installation

## install gradle

```shell
brew install gradle
gradle --version 
# should >= 4.7
```

## build
```shell
gradle build
```

## init the db

* create db

  Create a database named 'db_tms' with 'utf8mb4' encoding and 'utf8mb4_general_ci' in mysql.
  You can configure your db url in ``src/main/resources/application.yaml``.

* run the init sql
  You can find the sql file in ``src/test/resources/init_db.sql``.


## run the service 
```shell
gradle bootRun
```


## check it works

Just open http://localhost:8080/api/issues in your browser.

## write something and enjoy.
