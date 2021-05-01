<h1>I - Interface Segregation Principle - ISP</h1>

# Definition
## Clients should not be forced to depend on methods that they do not use.

The goal of ISP is find the perfect granularity level for our abstraction. We have to split interfaces that are very large into smaller, more focused interfaces, so that clients that use then will <b>not be forced to depend on things that they do not need</b>.
In the ISP, the word "interface" does not necessarily mean a Java interface. MOst of time this is the case. However, the interface segregation principle also applies for abstract classes or, in fact, any public method that our won class depends upon. The nice thing about the interface segregation principle is that it reinforces other SOLID principles. For example, if we keep interfaces small, the classes that implement then have a higher chance to fully substitute that interface. This is the LSP. By applying ISP we are refactoring LSP. if we have large interfaces with many methods, then there's a great chance that the derived types will not fully support all those methods, this violate the LSP. Also, classes that implement small interfaces are more focused and tend to have a single purpose. So we reinforce the SRP. 

###ISP reinforce SRP and LSP.

Lean interfaces minimize dependencies on unused members and reduce code coupling. Code coupling is the number one enemy of clean code. Code coupling leads to technical debt. Then code becomes more cohesive and more focused, which is also a  good thing.

# Identifying “Fat” Interfaces
The are a couple of symptoms that manifest themselves and that tell us precisely when an interface should be refactored and made smaller. 

## Interface whit many methods
```java
interface LoginService{
    void onSignIn();
    void onSignOut();
    UserDetails getUserDetails();
    void setSessionExpiration();
    void updateRememberMeCookie();
    void validateToken();
}
```
 Let's say we want to create a class called GoogleLoginService. We want to implement the LoginService interface. However, because the authentication is handled by Google, we might need to implement just signIn and signOut. Methods like updateRememberMeCookie or setSessionExpiration are not valid in the context of a Google authentication. Therefore, we throw an UnsupportedOperationException and not implement them. This is a problem because the GoogleLoginService class violates the interface segregation principle and also the Liskov substitution principle. So pay attention when you have large interfaces. They might force you to create classes that do not fully support them.

 ## Interface with Low Cohesion
 ```java
 interface ShoppingCart{
     void clearShoopingCart();
     double getTotal();
     void processPayment();
     boolean checkItemInStock(Item item);
 }
 
 ```
 ProcessPayment and checkItemInStock do not conceptually belong to a shopping cart. They probably belong to a payment processor or a stock verify service. This interface and the methods within it are not cohesive. We don't have unnecessarily a lot of methods. Just four methods is not a large number. But the methods are not cohesive with the overall purpose of the interface. And when we try to implement it, we create a class called ShoppingCartImpl, and we are forced to implement those two methods. And for processPayment, for example, we're not going to throw an exception. We're actually going to implement it, so we need to bring in a lot of dependencies. We need a PaymentService. We need the UserService. We need the EmailService. So we've effectively increased coupling a lot because we are forced to provide an implementation for the processPayment method when all we wanted was a ShoppingCartImpl. Although this class does not violate the interface segregation principle because it fully implements that interface, it does, however, violate the single responsibility principle because now the ShoppingCart has more responsibilities than it has to. Cohesion was also affected because ShoppingCart is now responsible for doing other things besides its main focus, keeping track of the items added to the basket.

 ```java
 abstract class Account{
    BigDecimal getTotalBalance();
    void processLocalPayment();
    void processInternationalPayment();
}

class SchoolAccount extends Account{

    BigDecimal getTotalBalance() {// implementation}
    void processLocalPayment() {// implementation}
    void processInternationalPayment(){throw Exception }

}
 ```

 Interfaces with lots of methods should definitely be a warning sign. Interfaces with low cohesion where the methods do not adhere to the overall goal of your component should also be a warning sign that should tell you you're dealing with an interface that is not properly structured, is not granular enough
 Clients that throw exceptions instead of implementing methods provide a clear symptom of interface pollution. Clients that provide empty implementations are another example. And, last but not least, when a client forces implementation and becomes highly coupled, again, it's more subtle, but it's a good indicator that you're taking on more features than you need.

# Refactoring Code That Depends on Large Interfaces
If you own the code, breaking interfaces is pretty easy and safe due to the possibility to implement as many interfaces as you want. Instead of having one interface with three methods, we can have three interfaces each with one method. And then the class can implement all of them to achieve the same result. So that's pretty straightforward and pretty safe.
On the other hand, if you're working with external legacy code and you don't control the interfaces that you have to implement, then you cannot break them down. So you need something else. Design patterns can help you with this. The <b>Adapter pattern</b> is particularly useful for dealing with this kind of situation because it translates, it adapts interfaces that you can't control to another interface that you can use in your own code.

```java
interface BaseAccount{
    double getBalance();
}

interface LocalMoneyTransferCapability(){
    void processLocalPayment();
}

interface InternationalMoneyTransferCapability(){
    void processInternationalPayment();
}

class SchoolAccount implements BaseAccount,LocalMoneyTransferCapability{
    double getBalance(){...}
    void processLocalPayment(){...}
}

class InternationalLoanService implements InternationalMoneyTransferCapability{
    void processInternationalPayment(){...}
}
```
We are now using those lean interfaces in ways that we didn't think about when we first created them, and that's the good thing about having small interfaces. We can compose and reuse them however we see fit during the evolution of our product. This technique of breaking interfaces down is pretty simple. So respecting the interface segregation principle once you know the symptoms is pretty straightforward.
