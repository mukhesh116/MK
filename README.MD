mvn clean install -DskipTests

http://localhost:8082/keybank/api/swagger-ui.html#!/profile-controller/processProfileDetailsUsingPOST

{
  "orgName": "WELLSFARGO"
}


{
  "status": "200",
  "statusMessage": {
    "code": "SUCCESS",
    "description": "Company profile details processed successfully."
  },
  "response": [
    {
      "companyId": "0828R3C3169TJ",
      "companyStatus": "Active",
      "companyName": "WELLSFARGO NEYWORK",
      "companyCity": "ELKHART",
      "companyState": "TX"
    },
    {
      "companyId": "cxbfh8888888909",
      "companyStatus": "In-Active",
      "companyName": "WELLSFARGO BROOKLYN",
      "companyCity": "HYDERBAD",
      "companyState": "IN"
    }
  ]
}
