# Receipt Processor

Name: Keanu Aloua

Backend Engineer Apprentice take home assignment

## How to run
1. Clone the repo
```
git clone https://github.com/keanu-a/receipt-processor.git
cd receipt-processor
```
2. Since using java Spring Boot, first build the jar
  ```
  ./mvnw clean package
  ```
3. Build the docker image
  ```
  docker build -t receipt-processor .
  ```
4. Run the container
  ```
  docker run -p 8080:8080 receipt-processor
  ```
5. Access the API
- `localhost:8080/receipts/process`
- `localhost:8080/receipts/{id}/points`
  
### Additions
- Removed leading/trailing whitespace before inserting into storage
- Validated date and time to ensure format `yyyy-mm-dd` and `hh-mm`
- Made sure there was at least 1 item on the receipt