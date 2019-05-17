mvn clean install -DskipTests

java -jar target/company-profile-service-0.0.1-SNAPSHOT.jar


http://localhost:8082/companyprofile/api/swagger-ui.html

Request:

{
  "orgId": "BLOCKBORN-IND",
  "orgName": "BLOCKBORN"
}


Response:

{
  "status": "200",
  "statusMessage": {
    "code": "SUCCESS",
    "description": "Company profile details processed successfully."
  },
  "data": [
    {
      "companyId": "0828R3C3169TJ",
      "companyStatus": "Active",
      "companyName": "SASIIIIIIII LLC",
      "companyCity": "ELKHART",
      "companyState": "TX"
    },
    {
      "companyId": "cxbfh8888888909",
      "companyStatus": "In-Active",
      "companyName": "MKKKKKKK LLC",
      "companyCity": "HYDERBAD",
      "companyState": "IN"
    }
  ]
}
