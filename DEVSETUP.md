# How to run app

## Prerequisites
- Docker is available
- PostgreSQL is installed
- Gradle 7.5

## Step 1: Update database info
go to [Dockerfile](Dockerfile) then update database info
```
ENV DATABASE_URL=jdbc:postgresql://db:5432/customer_app_db
ENV DATABASE_USER=admin
ENV DATABASE_PASSWORD=admin
```

## Step 2: Build docker image
```shell
docker build -t coffee-shop-chain .
```

## Step 3: Run container
- Make sure container `app` is not exist
```shell
docker container rm app -f
```
- If we connect to postgreSQL external server we just need update docker in `Step 1` then run this:
```shell
docker run -p 8080:8080 --name app coffee-shop-chain
```
- Otherwise, If we don't have postgreSQL external server. we can set up locally:
```shell
docker compose up
```
then run app with network: `coffeeshopchain_common-network`:
```shell
docker run -p 8080:8080 --name app --network coffeeshopchain_common-network coffee-shop-chain
```

## Step 4: Testing
- Now you are able to test via http://localhost:8080
- Back to [README](README.md) to see how to test.

# Note
- If you plan to deploy this application to AWS, you'll need to follow through at least `Step 3`.
