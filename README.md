# OAuthAuthorizationServer

The application has been created with educational purposes to get knowledge about machinisms behind OAuth2 server side operations. The application does not provide user registration functionality, but during app registration it is possible to associate an user with registered app.

After having registered the app it is possible to use OAuth2 authorization mechanism by:

1. Redirecting user to address providing necessary parameters client_id (appId), redirect_uri: 
https://oauth-authorization-server.herokuapp.com/oauth/authenticate?client_id=%s&redirect_uri=%s

2. Handling authorization server redirect and retrieving authorization code from the response.

3. Sending request to the authorization server requiring access token:
https://oauth-authorization-server.herokuapp.com/oauth/access_token?client_id=%s&redirect_uri=%s&client_secret=%s&code=%s

The request requires four parameters:
client_id - unique application id generated during application registration in authorization server system,
redirect_uri - address to which authorization server shall send response after authentication - for request validation purposes,
client_secret - unique identifier generated during application registration in authorization server system,
code - authorization code generated in previous step,

4. Getting required resources with use of access token received in previous step.
https://oauth-authorization-server.herokuapp.com/oauth/resource?access_token=%s

An example implementation of a client app using the authorization server api can be found in project:

OAuthClient - https://github.com/mkapiczy/OAuthClient

## Running the application

To run the application from your IDE, simply run the `com.github.mkapiczy.oauth_server.Application` class as
a Java Application.
Alternatively the application can be started from the terminal using maven with `mvn spring-boot:run`.



