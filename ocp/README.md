<h1>  O - Open Closed Principle - OCP</h1>

Evolving Code with the Open Closed Principle, lead us to a code that is easy to change and evolve over time.

### Classes, functions, and modules should be closed for modification, but open for extension. 

## Close to modification 
Each new feature should not modify existing source code. (Source code basically becomes immutable).

## Open for extension 
A component is open for extension if allows us to make it behave in new ways by creating or writing new code.

### Modifying Existing Code is a Risk.
When Class B and Class C depends on Class A. 
If we modify Class A to delivery some new behavior to Class B, some times we can broke Class C.

In the real application the dependencies graph is much more complex, changes to a particular component can have ripple effects in various parts of the system. (Fragility)

# Why Should we apply the OCP ?

- New features can be added easily and with minimal cost. 
Even in a legacy applications that are tangled, old and complex, which time we need to make a change the best way is put that change in a new components, unit test this and don't touch the legacy code. 

- Minimizes the risk of regression bugs.

- Enforces decoupling by isolating changes in specific components, works along with the SRP.

# Solid principles are most effective when applied together. 

# Simple ways to apply OCP

## Inheritance
- The drawback of inheritance is coupling of subclasses and the Base class, specially if we are using concrete base class. 

```java
public class BankAccount {
    ...
    void transferMoney(double amount){
        // business logic for local transfer
    }
}

public class InternationalBankAccount extends BankAccount{
    ...
    @Override
    void transferMoney(double amount){
        // business logic for international tranfer
    }

}

``` 

## Strategy Design Pattern
- Better Approach using interfaces.

```java
//Money Transfer Processor
public interface MoneyTransferProc {
    public void transferMoney(double amount);
}

public class BankAccount implements MoneyTransferProc {
    public void transferMoney(double amount){...}
}

public class IntlBankAccount implements MoneyTransferProc {
    public void transferMoney(double amount){...}
}

```

Here we have a transfer money capability (interface MoneyTransferProc), then we can create classes the implement this interface.

When we have our strategies , we need a factory that can build then base on a particular property.

```java
public class MoneyTransferProcessorFactory {
    public void MoneyTransferProc build(TransferType type){
        if(type == TransferType.Local) return new BankAccount();
        else if(type == TransferType.Intl) return new IntlBankAccount();
    }
}

```
```java
...
void processPayment(double amount, TransferType type){
    ...
    MoneyTransferProc mtp = factory.build(type);
    mtp.transferMoney(amount);
}
```

## Progressively applying the OCP
- Start small, Make changes inline for the first time and then, if is a real need, extract to external components. 
- Don't apply for bug fixing.
- If you have more changes, consider start with inheritance.
- If you have many changes, dynamic decision, consider interfaces and design patterns like Strategy.

# Applying OCP for Frameworks and APIs Design 
- API : A contract/agreement between different software components on how they should work together. 

A public framework or API is complete under our control. But clients might use it in ways that you aren't aware of. 
Clients can't change de APIs, but changes that you do, can impact clients. 

## Exposed Framework / SDK


```java
@Version1
public class TaxCalculator {
    public double calculate(Employee e){
        // business logic.
    }
}

@Version2
public class TaxCalculator {
    public double calculate(Employee e, String currency){
        // business logic.
    }
}
```
Changing existent functionalities in public frameworks can break clients. 
If we release the Version2 of TaxCalculator this change (break change) will break all clients that are using Version1.

How to solve this ? We can make our library open for extension: 

```java
public interface AbstractTaxCalculator {
    public double calculate(Employee e);
}

// implement by customer
class CustomerUSACalc implements AbstractTaxCalculator {
    public double calculate(Employee e, String currency){
        // business logic.
    }
}
```
We can extract a interface and provide in our framework a concrete implementation, if costumer isn't satisfied with our implementation, Then he can provide one implementation.


# Best Pratices for Changing APIs.
- Do not change existing public contracts: data classes, method signatures.
- Expose abstractions to your costumers and let then add new features on top your framework.
- If a breaking change is inevitable, give your clients time to adapt. (create new methods, and mark with @Deprecated the old ones, and let they know, 
that in a particular period of time, they will need to change for newer versions). 

