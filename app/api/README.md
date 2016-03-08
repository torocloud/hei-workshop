Blog API
===
[![Build Status]( 	https://img.shields.io/travis/TORO-IO/hei-workshop.svg)]()

## Running
Assuming you have atleast java 7:
```
➜  api git:(api) ✗ cd dist
➜  dist git:(api) ✗ java -jar blog-api-1.0.0-SNAPSHOT.jar 
```
## Authentication
All requests must be made with header - Authorization: Bearer *token*
- POST	  /api/login	: returns { "token" : "jwt token" }


## RESTful Endpoints

- GET     /api/blogs
- GET     /api/blogs/{id}
- POST    /api/blogs
- DELETE  /api/blogs/{id}

## Payload

**Blog**
```
{
    "id"            : number,
    "title          : "text",
    "description    : "description"
}
```

**ApiExceptionModel**
```
{
    "httpStatus"    : number,
    "errorMessage"  : "text",
    "timestamp"     : "timestamp(millis)"
}
```
