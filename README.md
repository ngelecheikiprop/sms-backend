# Student management  system

This is a backend for a student management system.
## table of contents

[features](#features)
[endpoints](#endpoints)

## features

- login url is not protected
- onced logged in a token is used for other calls.

## endpoints

The base url : `localhost:8080/api/v1/`
### login 

endpoint : `/login`
```json
{
  "id": 1,
  "username": "kiprop",
  "password": "k@123"
}
```

### update

url : `/update`

body

```json
{
  "id": 1,
  "firstName": "David",
  "lastName": "Kiprop",
  "birthDate": "1999-02-02",
  "className": "SS",
  "score": 95,
  "status": 1,
  "photoPath": "/images/students/kiprop.jpg"
}

```

response if it updates

```json
{
  "id": 1,
  "firstName": "David",
  "lastName": "Kiprop",
  "birthDate": "1999-02-02",
  "className": "SS",
  "score": 95,
  "status": 1,
  "photoPath": "/images/students/kiprop.jpg"
}
```




