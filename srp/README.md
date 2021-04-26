<h1>  S - Single Responsibility Principle - SRP </h1>

Every function, class or module should have one and only one reason to change. 
Reason to change = responsibility

Always identify the reasons to change that your components have and reduce then to a single one. 

# Why Should we use SPR

- It makes code easier to understand, fix, and maintain.
- Classes are less coupled and more resilient to change (less fragility, less rigidity).
- More testable design.

## Identify Multiple Reasons to Change

### If Statements
```java
if(employee.getMonthlyIncome() > 2000){
    // some logic here
}else{
    some other logic here
}
``` 
Here we can extract the if logic to another class or method, and extract the else logic, to another class or method. 

### Switch Statements
```java
switch(employee.getNbHoursPerWork()){
    case 40: {
        // logic for full time
    }
    case 20:{
        // logic for part time
    }

}
``` 
Here we can extract the 40 hours logic to another class or method, and extract the 20 hours logic, to another class or method. 

### Monster Methods and Good Classes 
Usually, they have a large number of lines, and mix levels of abstraction with implementation.
They have a lot of dependencies.
So they have a lot reasons to change and violate SRP.

```java
Income getIncome(Employee e){
    Income income = employeeRepository.getIncome(e.id);
    StateAuthorityApi.send(income, e.fullName);
    Payslip payslip = PayslipGenerator.get(income);
    JsonObject payslipJson = convertToJson(payslip);
    EmailService.send(e.mail, payslipJson);
    ...
return income;
}


class Utils{

void saveToDb(Object o){...}
void convertToJson(Object o){...}
byte[] serialize(Object o){...} 
void log(String msg){...}
String toFriendlyDate(LocalDateTime data){...}
int roundDoubleToInt(double val){...}

}
``` 
Caution with classes like Utils, Helper, Shared, Commons...
Prefer have specialized classes that handle particular use cases. 

### People 
People are reason to change in software applications. 
Different actors will want different features at some point in time.
Know which actor are responsible for changes in the same piece of code needs a lot of expertize in business domain. 
SRP is not all about code, but about actors that use application.

```java
Report generate(){
    // method used by HR and Management actors 
    // each one will want different features at some point in time
}
``` 

## SPR EXAMPLE
```java
class ConsoleLogger(){
    void logInfo(String msg){System.out.println(msg)}
    
    void logError(String msg, Exception e){...}
}
``` 
This is the felling that we will have when we see a component that have a well define purpose. The names should be explicit, the functions should be inline whit that purpose, everything should be very focus. 

## Symptoms of Not Using SRP. 
- code is more difficult to read and reason about (We pass more than 90% of our time reading code)
- Code which has more than one responsibility decreased quality due to testing difficulty.
- Side effects (When a function declare that do one thing, but internally do other things)
- High coupling (More DANGEROUS symptom of violate SRP )

# Coupling 
The level of inter-dependency between various software components: Components that violate SRP usually are tight coupled.

Code that have many reasons to change tends to have many dependencies. 

## Couping with concrete components is specially dangerous 
We are exposed to internal implementation details of classes.

```java

import ...RepositoryImp

Income getIncome(Employee e){
    
    RepositoryImpl repo = new RepositoryImp(srv,port,db);

    Income income = repo.getIncome(e.id);

    return income;
}
``` 

This code will break when RepositoryImp change. This introduce fragility and rigidity in our code.

Example of Refactoring:

```java

Income getIncome(Employee e, Repository repo){  // using dependency inversion principle to do that.
    
    Income income = repo.getIncome(e.id);

    return income;
}
``` 

## If Module a know too much about Module B, changes to the internals of Module B may break functionality in Module A.

"We want to design components that are self-contained: independent, and with a single, well-defined purpose". Andrew Hunt & David Thomas, The Pragmatic Programmer.

## Steps to do SRP:
- Correctly identify reasons to change.
- Refactor responsibilities out to specialized components.