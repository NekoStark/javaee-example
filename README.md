# javaee-example
An example of full-stack JavaEE Application with JPA, CDI and JSF

### Installation

Run on **Wildfly Server 10.1.0.Final**.

Deploy the war with the datasource file contained in the project

To add some user and notes to the database, uncomment the following annotations
```Java
package it.unifi.ing.stlab.swa.bean;

@Singleton
@Startup
public class StartupBean {
  ...
}
```
