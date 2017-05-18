Client Owner Manager
====================

This module provides an extension to Keycloak so clients can be linked and managed by specific users.

An example of all the possible requests that can be done to the API can be found 
in https://www.getpostman.com/collections/d5601317d3dbcf9c6421. 
This queries can be tested using Postman.

## Build  and deployment

Builds are created by using Maven. Make sure to have an updated version of Maven (>3.3.0)or 
builds might not work properly. Maven will handle fetching dependencies following the 
indications in pom.xml and save the build in the target directory. 
The output is a `JAR` file including the compiled java files and metadata.

In case new dependencies are required, there is the `META_INF/jboss-deployment-structure.xml`
file, which includes and declares all the dependencies required, and where the list should
be updated. Including this file allows an easier deployment using WildFly.

### Deployment Using Docker

Using Travis CI, a new image based on `jbos/keycloak-mysql` and `jboss/keycloak-postgres`
are created. The Dockerfiles can be found in the distribution folder. In general, the only
thing done is compiling the JAR file and adding it to the `$KEYCLOAK_HOME/standalone/deployments`
folder, which automatically registers the application with JBoss/WildFly.

This is the recommended path and you can use it following the original instructions
from Keycoak's stock Docker images.

### Manual Deployment

To deploy the extension manually in an existing Keycoak instance, the only requirement
is to copy the compiled file into the `$KEYCLOAK_HOME/standalone/deployments` folder.
In case you want to disable it, you only need to delete the file from the same folder.
This also works without restarting the instance.

## Usage

API CRUD actions can be found in [Postman's collection](https://www.getpostman.com/collections/d5601317d3dbcf9c6421).

1. Create an authentication token. At the moment only people with `admin` role can do so.
In the future, custom configuration of which role is the one to check will be possible.
This means that any user with the proper roles can use it, even Service Accounts from Clients. 
This last case has the benefit of allowing to cancel both API client and account at once,
in case this API is used from a remote interface.
```curl
# Create a token with user "admin" and pass "admin"

curl -X POST \
  http://localhost:8080/auth/realms/master/protocol/openid-connect/token \
  -H 'accept-language: application/json' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d 'grant_type=password&username=admin&password=admin&client_id=admin-cli'
```
This returns a JSON containing Access Token, TTL, and Refresh Token. The Access Token
can be used to do authenticated calls to the API. The Refresh Token allows to retrieve
a new Access Token, as this last one is short lived. A Refresh Token allows you to authenticate
again without knowing/having to send again the credentials of the user.

2. With the given Access Token, do authenticated calls to the API to do CRUD 
actions to the clients. For example, to create a client with the currrent user:

```curl
curl -X POST \
  http://localhost:8080/auth/realms/master/client-registration/ \
  -H 'authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJDc3ZKVXZZT09Gc3RxNVU4VEQ2ME1kUDFzVnh4Z1IwdnBlaDdnOW4wSmpzIn0.eyJqdGkiOiJmMGZkMjRiNS1iMzIxLTRhMDItYmI4Mi1hYjc0MmU4MmZlYmIiLCJleHAiOjE0OTQ5MjkxOTAsIm5iZiI6MCwiaWF0IjoxNDk0OTI5MTMwLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiNDdjMDVmNTQtOWU5Mi00NTAzLWE1ZGMtMWNhYTM1MjI4ODQ0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiZmI0NWVkY2YtZGEyNy00NDk4LWJlYzYtNTQ1M2EyNTQwYWRlIiwiYWNyIjoiMSIsImNsaWVudF9zZXNzaW9uIjoiY2Q0NzYyMjctNmNlNi00ZDNjLThjZWItYWVjYzJkZDA2ZGJlIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImFkbWluIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19fSwibmFtZSI6IiIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIn0.pHSVF9qvt5jFASl87FwR0b5qb-3CcK_9sjTHyww9IX4xvPv6kzE8LwRTheNrgQcxIyp2KSq3iiR5Ra9sTg4hpvC_Ww_XytkVBwTnmWqT9ZzK3n_doDHGtCnkI7vgT3Bqfd2VHweNzVYi2JRi0m8Y91o-c_UWyAhj2tACDmbWpcoZAPyfRn-iKkNkD3UGjdu6lvxn5JwvCwVYnkhn_0I5zNqHmh1DrHhqgBghgO24sPtQNByOmitUf8EuVwQPjdxU6uIpNAogAzDLvCxUDz6rnjIVcwfr3PC-LKtJlroBJo3gEf49wDA7YrRUItmj2eLC74IwFMa8HTMMNV-c7asXCg' \
  -H 'cache-control: no-cache' \
  -d '{
    "clientId": "hello"
}'
```

The payload should be a client JSON following the model used as well in Keycloak's API:
http://www.keycloak.org/docs-api/3.1/rest-api/index.html#_clientrepresentation

None of the parameters is required. In case of the clientId, it will be a randomly generated
one, and it will load all the other settings with the default ones.

The returned JSON includes the complete representation of the new client (including the clientId, 
clientSecret and any other parameter related to the client).

In order for the clients to be available in this API, they need to be created via the API.
 Clients created through the Admin interface or the Admin API, won't appear available here.
 
 

## Updating to a new Keycloak version

To update the build to a new Keycloak version, the only task needed to do so is to update the Keycloack version in 
the `pom.xml` file in the root, to reflect the new version of Keycloak as dependency.

Afterwards, please, tests that the build compiles and deploys properly as dependencies 
in Keycloak may change.