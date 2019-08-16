# Centralized Auth Gateway

This is a centralized authentication and authorization gateway which is built on top of Netflix Zuul.
The implementation basically chains an Authentication Filter and an Authorization Filter which precede the Pre Decoration Filter of Zuul. 

## Authentication Filter

The Authentication filter is the first filter that runs in the filter chain. As the name suggests it provides an authentication layer by validating the Access Token of the request. 
The Filter performs the following actions : 
- Extracts the token from the request
- Decodes the JWT
- Verifies the signature, issuer and expiry
- Sets the userRole, userId and userType claims in the Request Context
- Sets the isAuthorizationRequired Flag to true if all the above tasks succeed, else throws an exception indicating what caused the request to fail.


## Authorization Filter

The Authorization filter is the second filter that runs in the filter chain. As the name suggests it provides an authorization layer by validating the claims set by the Authentication Filter into the request context. 

The properties are configured in a property file called <strong>authorization.yml</strong> which captures the user defined validations to be processed and in order to perform these validations in an optimized way, it creates a URL Pattern Tree which is basically a tree made up of nodes that represent the path/patterns of the url at different levels, these levels are formed by splitting the url patterns by '/'. Every node that represents a url pattern is designated as a url node.

It performs 2 mandatory validations and one optional validation :

1. Valid Url Pattern
2. Valid Url Claim
3. Valid User Role (Optional)


  ### Valid URL Pattern
  The URL Pattern Tree formed above is used to validate if a request matches any valid url pattern. If the tree contains a url pattern node that matches the request it returns a valid response and performs the url claim validation, else it throws an exception indicating an <strong>Invalid Route</strong>.

  ### Valid URL Claim
  The URL Pattern once validated then is checked for claims in the pattern, the user can denote one of the path parameters in the request to match one of the jwt claims. This is achieved by finding out the part of the url that is designated as a claim (identified by the string :claim_name) and then substituting it with the claim value and checking for equality with the path in the request. If the url claim is found to be valid the request is allowed to proceed further, else an exception is thrown that specifies that the url claim was invalid.

  ### Valid User Role Claim
  This is an optional validation and is performed after the url claim validation, here the user can designate if the url pattern can only be requested by certain role/roles. If so, we validate the userRole claim against the list of claims/claim denoted by the user for the specific url pattern. If found to be valid the request is allowed to proceed further, else an exception is thrown that specifies that the userRole was not allowed.


If all the above validations succeed the request is allowed to proceed further in the filter chain and is reverse proxied to the designated zuul route that matches the request.



## Playing Around with the Gateway

Start the Application by building it using Maven and then running the application, the application by default runs on port 8082.
To Play around with the gateway one can make use of the inbuilt endpoint to generate a token by providing a userRole, userId and a userType and generate an access token for the subsequent requests. You can use the following command to do the same : 

```
curl -X POST \
  http://localhost:8082/token/generate \
  -H 'Content-Type: application/json' \
  -d '{
	"userId":1,
	"userType":"regular",
	"userRole":"regular"
}'
```
You can then use this token as shown below in the subsequent requests :

```
curl -X GET \
  http://localhost:8082/test/endpoint/v0/test/1/ping \
  -H 'Authorization: Bearer ACCESS_TOKEN_HERE'
```

You can validate the following scenarios: 
1. Invalid Token Signature
2. Expired Token
3. Invalid URL Pattern
4. Invalid URL Claim
5. Invalid User Role

