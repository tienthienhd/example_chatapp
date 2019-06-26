# SQL
## basic
- Query
- Insert
- Update
- Delete

# Java
## basic
- controller express
- basic string process
- multi thread
- connect mysql
- OOP

# Maven
- dependency

# Json Web Token
- Step to verify:
  - user send usename and password to authentication
  - user send username and password to authentication
  - server received, verify and create JWT
  - server send JWT to user and user storage it to local
  - when user want to use api of server, user send request and JWT
  - Server verify JWT and send response to user.
- JWT has 3 part:
  - header.payload.signature
  - header

    ```json
    {
        "typ": "JWT",
        "alg": "HS256"
    }
    ```
  - payload: info session: username, issuer, time...

# Vert.x
- send and listen message to addresss
- asynchronous
- event driven: not run unless they receive a msg.
- pub-sub