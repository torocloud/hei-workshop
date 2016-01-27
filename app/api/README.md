Blog API
===

## Running
Assuming you have atleast java 7:
```
➜  api git:(api) ✗ cd dist
➜  dist git:(api) ✗ java -jar blog-api-1.0.0-SNAPSHOT.jar 
```

## RESTful Endpoints

GET     /api/blogs
GET     /api/blogs/{id}
POST    /api/blogs
DELETE  /api/blogs/{id}

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