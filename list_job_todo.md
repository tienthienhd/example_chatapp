# Prepare
- IDE: [intellijidea](https://www.jetbrains.com/idea/)
- Database manager: [MongoDB](https://docs.mongodb.com/manual/administration/install-community/) or [SQL](https://dev.mysql.com/downloads/installer/)
- Learn maven basic (dependencies)
- Install java environment

# Design database
- Analyse problems
- Choose database management
  - MongoDb: [tutorial mongodb](https://www.tutorialspoint.com/mongodb/)
  - SQL: [tutorial sql](https://www.tutorialspoint.com/sql/)

- Create schema on database is choiced

# Design System ([example mvc model](docs.md))
- Design architecture of system
- Analyse components of system
- Analyse data instances
- Analyse flow of system
- Design protocol (message format to transfer)

# Code (java)
- Create data instances
- Create connection to database
  - MongoDb: [tutorial MongoDB - java](https://www.tutorialspoint.com/mongodb/mongodb_java.htm)
  - SQL: [tutorial SQL - java](https://o7planning.org/vi/10167/huong-dan-su-dung-java-jdbc)

- Create functions get, update, delete, insert data 
- Create test unit for functions above
- Based on protocol, code server tier and client tier to process message from client and response to client. Done one by one message transfer.
- Code interface for system using core function written.
- Warning:
  - Deadlock when use message passing
  - Concurrency control when using multi thread