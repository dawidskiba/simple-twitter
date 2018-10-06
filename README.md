# Simple Twitter like application

Prerequisite:
* Maven in version at least 3.3.3 installed
* MAVEN_HOME system env is set up
* Rest API testing tool installed (Postman)

Application run:
* mvn spring-boot:run

Application usage:
* use Postman or different tool
* use CURL

This is a simple Spring Boot application exposed on default port 8080

Application endpoints:
* GET <server-address>:8080/twitter/{user-name}
    * produces User's wall with his own and following publisher's messages in JSON format
    
example (GET localhost:8080/twitter/adam):
````
[
    {
        "user": "adam",
        "timestamp": "2018-10-05T14:45:13.094",
        "message": "Welcome"
    }
]
````

* POST <server-address>:8080/twitter/{user-name}
    * consumes user's message in JSON format
    
example (POST localhost:8080/twitter/adam):
````
{
	"message": "New post"
}
````

* GET <server-address>:8080/twitter/{user-name}/follow?publisher={user-to-follow}
    * endpoint used to follow a user provided as a request parameter
    
example:
````
localhost:8080/twitter/adam/follow?publisher=tom
````
 
