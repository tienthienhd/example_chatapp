# Gradle vs. Maven
## Gradle
- build automation system 
- open source
- uses the concepts on Apache Maven and Apache Ant
- uses domain-specific language based on the programming language Groovy
- uses XML for project configuration
- it also determines the order of tasks run by using a directed acyclic graph.
- it was designed to support multi-project builds that are expected to be quite huge.
- It also allows for incrementally adding to your build, because it knows which parts of your project are updated.
- based on a graph of task dependencies
## Maven
- project build automation using Java
- map out how a particular software is built: dequendencies
- uses an XML file to describe the project, dependencies of software
- predefined targets for tasks such as packaging and compiling.
- 

# Maven tutorial
- POM file contains information about the project and various configuration detail
- POM also contains the goals and plugins
- Configuratons:
    - project dependencies
    - plugins
    - goals
    - build profiles
    - project version
    - developers
    - mailing list
- Before creating a POM, we should first decide the project group(groudid), its name (artifactId) and its version as these attributes help in uniquely identifying the project in repository.

# Json Web Token
- why need to save token on server: avoid case multi token on one user. Save token on server, we can update token.
- Step to verify:
    - user send username and password to authentication
    - server received, verify and create JWT
    - server send JWT to user and user storage it to local
    - when user want to use api of server, user send request and JWT
    - Server verify JWT and send response to user.
- Create JWT:
    - create header: 
    ```json
    {
        "typ": "JWT",
        "alg": "HS256"
    }
    ```
    - create payload: save info of section: username, time ...
    - create signature
    - create jwt from : header.payload.signature
    - verify the jwt
# Vert.x
- a toolkit or platform for implementing reactive applications on the Java
- can deploy and execute components called Verticles.
- verticles are event driven (they do not run unless they receive a msg). Verticles can communicate with each other via the Vert.x event bus.
- Messages can be simple object: string, csv, json, binary data
- Verticles can send and listen to addresses.
- Address is named channel.
- when a msg is sent to a given address, all verticles that listen on that address receive the msg.
- Verticles can subscribe and unsubcribe to address without the senders knowing.
- all msg handling is asychronous, if verticle send a msg to another verticle, that msg is first put on the event bus, and control is returned to the sending verticle. Last, the msg is dequeued and given to the verticles listening on the address the msg was sent to.

## Vert.x tutorial
### Create Vertx Instance
```java
import io.vertx.core.Vertx;

public class VertxApp {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
    }
}
```
- Vertx instance create a number of threads internally to handle the exchange of messages between verticles.
- These threads are not daemon threads (not daemon threads : when main thread finish, work threads util run to finish.)
### Create a Verticle
```java
package examples.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class MyVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {
        System.out.println("MyVerticle started!");
    }

    @Override
    public void stop(Future stopFuture) throws Exception {
        System.out.println("MyVerticle stopped!");
    }

}
```

- A verticle has a start() and stop() methods which are called when the verticle is deployed and when it is undeployed.
- Initialization work inside the start() method, and cleanup work inside the stop() method.

### Deploying a Verticle
```java
import io.vertx.core.Vertx;

public class VertxVerticleMain {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new MyVerticle());
    }
}
```
- 