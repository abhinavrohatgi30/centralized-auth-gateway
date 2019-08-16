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
The Authentication filter is the first filter that runs in the filter chain. As the name suggests it provides an authorization layer by validating the claims set by the Authentication Filter into the request context. It performs 2 mandatory validations and one optional validation :

1. Valid Route
2. Url Claim
3. UserRole Claim (Optional)




