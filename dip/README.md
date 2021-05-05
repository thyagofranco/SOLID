# D - Dependency Inversion Principle - DIP
<b>High-level modules should not depend on low-level modules. Both should depend on abstraction. Also, abstractions should not depend on details. Details should depend upon abstractions.</b>

This principle enables us to create systems that are loosely coupled, easy to change, and maintain.


## What is a high-level module or a low-level module? What is an abstraction

<b>High-level</b> modules are the part of our application that bring real value. They are the modules written to solve real problems and use cases. They are more abstract in nature and map to the business domain. Most of us call this business logic. High-level modules tell us what the software should fo, not how it should do it.

<b>Low-level</b> are implementation details that are required to execute the business logic. Because high-level modules tend to be more abstract in nature, at some point in time, we will need some concrete features that help us to get our business implementation ready. They are the plumbing for the internals of a system. They tell us how the software should do the tasks. 
Examples of low-level modules:
- logging
- data access
- network 
- communication
- IO
These are typical examples of low-level modules, and in reality, they tend to be very concrete. When we talk about logging, we'll talk about console logging or file logging. When we talk about data access, we'll talk about relational data access or NoSQL data access and so on.

 In a typical system, high-level modules work together with low-level modules to deliver the business value for our customers. In this example, we have payment and user management, which are all high-level modules of an HR framework. In order for them to work, they depend upon low-level modules such as networking, notification system, data access, and security. Notice that each of these low-level modules is very concrete. We tackle networking via GPRS. And when we talk about data access, we should have a relational system. And security is implemented via OAuth. Also, the notification system uses emails.

An <b>Abstraction</b> is something that is not concrete. It's something that we as developers cannot "new" up. In Java applications, we tend to model these abstractions using interfaces and abstract classes. 

## Violiation on DIP:

Traditionally, when we depend on details, our components tend to look like this.
[A] High-level-component ---> [B] Low-level-component

Introducing a abstraction to solve the problem.

[A] High-level-component ---> [I] Abstraction <---- [B]Low-level-component

A high-level component, no longer depends directly on component B. It depends upon an abstraction. And component B, which is low level, also depends upon that abstraction. We have the first part of the definition of the principal here. Also, the component B abstraction should not depend on other details. I also like this schematic because it illustrates the inversion word in the dependency inversion principle name. When we depend on details, the dependency flows from the high-level component to the low-level component. When we depend on abstractions, that dependency flow is inverted as shown with the arrows of this schema.


## Writing Code That Respects the Dependency Inversion Principle
```java
// low level class
// It's a concrete class that use SQL to return products from the database.
class SqlProductRepo {
    public Product getById(String productId) {
        // grab product from SQL database
    }
}

// High level class
class PaymentProcessor {
    public void pay(String productId) {
        SqlProductRepo repo = new SqlProductRepo();
        Product product = repo.getById(productId);
        this.processPayment(product);
    }
}

```
 PaymentProcessor is the high-level module and SqlProductRepo is the low-level module. However, let's look at the downside of implementing code this way because clearly the PaymentProcessor has a direct dependency with the SqlProductRepo. We can see it here in the pay method where we actually instantiate this repo. We are newing up a new instance of the SqlProductRepo class. This clearly violates the dependency inversion principle.

 ```java
 interface ProductRepo {
    Product getById(String productId);
}

// low level class depends on abstraction
class SqlProductRepo implements ProductRepo {
    @Override
    public Product getById(String productId) {
        // concrete details for fetching a product
    }
}

class PaymentProcessor {
    public void pay(String productId) {
        ProductRepo repo = ProductRepoFactory.create();
        Product product = repo.getById(productId);
        this.processPayment(product);
    }
}

class ProductRepoFactory {
    public static ProductRepo create(String type) {
        if (type.equals("mongo")) {
            return new MongoProductRepo();
        }

        return new SqlProductRepo();
    }
}
 ```

 We have the PaymentProcessor, which is the high-level module. At the bottom we have the SqlProductRepo class, which is the low-level module. And in between we have the ProductRepo interface. Both modules depend on the ProductRepo abstraction, and the abstraction itself does not depend on any details. So we have applied the dependency inversion principle to our particular case. More importantly, we have eliminated the coupling between PaymentProcessor and the SQL implementation of the ProductRepo.


# Dependency injection (DI)
Dependency injection is very used in conjunction with the dependency inversion principle. However, they are not the same thing.  


 ```java

class PaymentProcessor {
    public void pay(String productId) {
        ProductRepo repo = ProductRepoFactory.create();
        Product product = repo.getById(productId);
        this.processPayment(product);
    }
}
 ```
 Not coupling with concrete SqlProductRepo, but coupling with ProductRepoFactory.

 Dependency injection is a technique that allows the creation of dependent objects outside of a class and provides those objects to a class. We have various methods of doing this. One of them is by using public setters to set those dependencies. However, this is not a good approach because it might leave objects in an uninitialized state. A better approach is to declare all the dependencies in the component's constructor. 


 ```java
class PaymentProcessor {

    private final ProductRepo repo;

    public PaymentProcessor(ProductRepo repo){
        this.repo = repo;
    }
    public void pay(String productId) {
        Product product = repo.getById(productId);
        this.processPayment(product);
    }
}


class Application {

 ProductRepo repo = ProductRepoFactory.create();
 PaymentProcessor payment = new PaymentProcessor(repo);

 payment.pay("123") ;

}
 ```

 The PaymentProcessor class is no longer responsible for creating its own dependencies. It is the caller's job to provide those dependencies


```java
class A {}
class B {}
class C {
    public C (A a){}
}
class D {
    public C (B b){}
}
class E{
    public E(D d){}
}
```

This is how we use dependency injection in order to decouple our components even more. Let's look at a more complex example. We have five classes over here. Classes A and B have no dependencies whatsoever. Class C depends on class A. Class D has a dependency on class B. And class E has a dependency on both classes C and D. If you want to call a method on class E, we also need to create all the dependencies that it needs. We need to take care of all of that dependency handling. We need to create concrete instances of those classes in a particular order. We have to create classes A and B first because they have no dependencies, then classes C and D. And at the end, you can create class E and call a method on it. This is a pretty simple example with just five classes. But our real projects have hundreds if not thousands of classes that can be instantiated. Although dependency injection is a pretty cool technique, doing it manually is clearly not the way to go forward. We cannot tackle this much complexity by hand. Also, think about the lifecycle of these objects. Maybe we want A and B to be singletons and C, D, and E to be objects that are created on every request. How do we do that manually? Well, even more logic has to come into the creation of these objects. Clearly, we need something else. Luckily for us, there's another piece to this puzzle, and that piece is called the inversion of control principle.


# Inversion of Control (IOC) 

Inversion of control can help us create large systems by taking away the responsibility of creating objects. Inversion of control is a design principle in which the control of object creation, configuration, and lifecycle is passed to a container or framework. The control of creating and managing objects is inversed from the programmer to this container. We don't have to "new" up objects anymore. Something else creates them for us, and that something else is usually called an IoC container or DI container. The control of object creation is inverted. It's not the programmer but the container that controls those objects. It makes sense to use it for some objects in an application like services, data access, or controllers. However, for entities, data transfer objects, or value objects, it doesn't make sense to use an IoC container. We can simply "new" up those objects, and it's perfectly okay from an architectural point of view. There are many benefits in using an IoC container for your system. First of all, it makes it easy to switch between different implementations of a particular class at runtime. Then, it increases the programs modularity. And, last but not least, it manages the lifecycle of objects and their configuration. For example, you can decide that some objects should be singletons while other objects should be created per every web request. I think that Spring is the most popular Java framework out there. At the core of the Spring framework is the Spring IoC container. You've probably heard about Spring beans. They are objects used by your application that are managed by the Spring IoC container. They are created with the configuration that you supply for that container. There are many ways to configure an IoC container in Spring. XML is one example. Creating configuration classes is another. Or simply by annotating classes with special annotations like @Service, @Component, @Repository, and so on and so forth. Let's look at a simple Spring bean definition example:
```java
@Configuration
public class DependencyConfig {
    @Bean
    public A a() {
        return new A();
    }

    @Bean
    public B b() {
        return new B();
    }

    @Bean
    public C c(A a, B b) {
        return new C(a, b);
    }
}
```
A dependency inversion principle, dependency injection, and inversion of control work very well together and are the most effective way to eliminate coupling. They are not, however, the same thing, and sometimes they will get confused. 
<b>The dependency inversion principle </b> tells us that high-level modules should not depend on lower-level modules. They should both depend upon abstractions. 
<b>Dependency injection</b> is a technique in which a component is not responsible for the creation of its own dependencies. Rather, it can declare those dependencies in the constructor, and in the calling code, a programmer or another framework can inject them. Manually handling and creating dependencies is not scalable, especially for large applications. 
This is where an <b>inversion of control container</b> comes in. It can handle the creation and lifecycle of all the objects in our application.
