
Make sure that ActiveMQ up and running.
Should have below queues.

IN
OUT_2


Build all services with below command:

cd apache-router
cd KeyBankProfileService
cd MDMService

mvn clean install -DskipTests


Run all service.

Hit the below url.


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
