## 개발용 DB
docker run -p 5432:5432 --name study-db -e POSTGRES_USER=study -e POSTGRES_PASSWORD=study -e POSTGRES_DB=study -d postgres
