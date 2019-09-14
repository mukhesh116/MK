Steps:


1) Download axonserver.jar from online. Unzip it. start the server.

java -jar axonserver.jar

access below url:

http://localhost:8024

2) Go to project home folder. Run below comman which buils all services.

mvn clean install -DskipTests

Start individual service and have a look into Axondash board. After running three services, u will be able to see them in dashboard.


3) Saga Manager is defined in order service.


Enter below url:

http://localhost:8080

{
"currency":"USD",
"itemType":"LAPTOP",
"price":2000
}


Flow starts from :

order ---> payment ---> shipping
