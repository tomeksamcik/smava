smava
===

#### Technologies

* Java 1.8.0_25
* Maven 3.3.9
* Spring Boot 1.3.2.RELEASE
* Spring MVC
* Spring Data
* Spring JMS
* Active MQ 
* JUnit4
* AngularJS 1.4.1
* Bootstrap 3.3.2
* ngToast 1.5.6
* Swagger

#### About

Application consists of six modules, four from which build the web application, one is a standalone jms client and the remaining one is a parent for the web app.

* <code>smava-data</code> - Data related services 
* <code>smava-jms-consumer</code> - Standalone JMS consumer
* <code>smava-jms-producer</code> - JMS producer module 
* <code>smava-parent</code> - Parent module
* <code>smava-rest</code> - RESTful API exposing CRUD operations 
* <code>smava-web</code> - Executable Spring Boot web application bundling data, jms-producer and rest modules

#### Building application

This will run the build and install all the <code>smava-parent</code> submodules in local Maven repository:

```
cd smava-parent
mvn clean install
```

This will build and install standalone JMS consumer module (note that test requires a running instance of ActiveMQ broker, you can ignore that requirement by skipping tests with <code>-DskipTests</code> flag)

```
cd smava-jms-consumer
mvn clean install
```

#### Running application

This launches the application in an embedded Tomcat instance running on port <code>8080</code> (running application also requires a running ActiveMQ broker)

```
cd smava-web
mvn spring-boot:run
```

This builds a <code>war</code> file that is deployable in a servlet container (I only tested with Tomcat 7)

```
cd smava-web
mvn clean package
```

#### Configuration

The only configuration needed is selecting data storage, it can either be a <code>h2</code> in-memory persisted storage or serializing data in http session.

Confugiration has to be made in <code>de.smava.Application</code> class in <code>smava-web</code> module.

Persistent storage:

```
@Bean
public AccountService accountService() {
return new AccountPersistentService();
}
```

Session-based storage:

```
@Bean
public AccountService accountService() {
return new AccountSessionService();
}
```

#### Notes

* RESTful endpoint is based on Spring MVC rather then Jetty, because Jetty conflicted with the Spring Boot embedded web container. Spring Boot simplifies bootstrapping applications, but makes it more troublesome to customize things.
* Endpoint descriptor is done with Swagger rather then WADL, because there is no out-of-the-box support for WADL in Spring MVC.  There are some custom solutions, but hard to implement
* I haven't found a tool for client code generation based on Swagger descriptors
* Swagger UI is available after runnig the application at <code>http://localhost:8080/swagger-ui.html</code>
* Swagger API descriptor is available at <code>http://localhost:8080/v2/api-docs?group=account</code>
* Account API is available at <code>http://localhost:8080/account</code>