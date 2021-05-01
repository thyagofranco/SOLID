<h1>  L - Liskov Substitution Principle  - OCP</h1>

# Definitions
* If S is a subtype of T, then objects of type T in a program may be replaced with objects of type S without modifying the functionality of the program. 

* Any object of a type must be <b>substitutable</b> by objects of a derived typed without altering the correctness of that program.

## Relationships

### IS A: 
- Square is a kind of rectangle 
- An ostrich is a bird

In object-oriented terms the "is a" relationship is not really helpful and can even make us create incorrect hierarchy of classes.

### IS SUBSTITUTABLE BY:

Is the class rectangle <b>fully</b> substitutable by the class square in the context of our application ? 
This is the correct question when we create a relationship between our types. 

### Incorrect relationships between types cause unexpected bugs or side effects. And usually correcting then involves a lot of re-factoring and re-engineering.

# Detecting violations of the LSP:

## Empty Methods/Functions
```java

class Bird{
    public void fly(int altitude){
        setAltitude(altitude);
        // Fly logic for this particular bird
    }
}

class Ostrich extends Bird{
    @Override 
    public void fly(int altitude){
        
        // Do nothing; Ostrich can't fly.
    }
}

...

Bird ostrich = new Ostrich();
ostrich.fly(1000); // BUG - unexpected result.


```
In biology Ostrich is a Bird, but in object-oriented programing and particularly in our program, tge class Bird is not fully substitutable by the class Ostrich.

## Harden Preconditions
```java

class Rectangle{
    public void setHeight(int Height){}
    public void setWidth(int Width){}        
    
    public int calculateArea(){
        return this.width * this.height;
    }
}

class Square extends Rectangle{
    public void setHeight(int height){}
    // Width must be equal to height 
        this.height = height;
        this.width = width;
    
    public void setWidth(int width){} //Same logic w = h;
    
    public int calculateArea(){
        return this.width * this.height;
    }
}

...

Rectangle r = new Square();
r.setWidth(10);
r.setHeight(20);
r.calculateArea(); // Will return 400


```
The rectangle is not fully substitutable for the class Square.


## Partial Implemented Interfaces
```java

interface Account{
    void processLocalTransfer(double amount);
    void processInternationalTransfer(double amount);
}

class SchoolAccount implements Account{
    void processLocalTransfer(double amount){
        // Business logic here
    }
    void processInternalTransfer(double amount){
        throw new RuntimeException("Not Implemented");
    }
}

...

Account account = new SchoolAccount();
account.processInternationalTransfer(100000); // Will crash

```

## Type Checking 

```java 
for (Task t: tasks){
    
    if(t instanceof BugFix){
        BugFix bf = (BugFix) t;
        bf.initializeBugDescription();
    }
    
    t.setInProgress();
}

```

Here we want to .setInProgress() all the Tasks except BugFixes that we need .initializeBugDescription() before .setInProfess().
This kind of approach, where for most subtypes, you do one thing and for a particular subtype you do another thing is a indication that those subtypes cannot substitute there base type. 

# Fixing Incorrect Relationships 
Here is tow ways of refactor code to LSP.
- Eliminate incorrect relations between objects.
- Use "Tell, don't ask!" principle to eliminate type checking and casting. 

## Fixed Empty Methods/Functions

```java

class Bird{
    // Bird data and capabilities 
    public void fly(int altitude){}
}

class Ostrich {
    // Ostrich data and capabilities. No fly method.
}
``` 

Breaking the relationship, Ostrich no longer extends Bird.

## Fixed Partial Implemented Interfaces
```java

interface LocalAccount{
    void processLocalTransfer(double amount);
}

class SchoolAccount implements LocalAccount {
    void processLocalTransfer(double amount){
        // Business logic here
    }

}

```
By splitting de Account interface in small other interfaces, we can now correctly implement em fully substitute the sub types, because of the correct relationship of LocalAccount and SchoolAccount.

## Fixed Type
```java
class BugFix extends Task{

    @Override
    public void setInProgress(){
        this.initializeBugDescription();
        super.setInProgress();
    }

}
...
for (Task t: tasks){
    //Task should be replaceable by BugFix
    t.setInProgress();
}

```
# Apply the LSP in a Proactive Way
- Make sure that a derived type can substitute its base type completely.
Don't ask your self "This Square class IS A Rectangle" 
Ask "This Square class fully substitute the class Rectangle in all the context for my application."

- Keep base classes small and focused.
If you have a pretty large base class then braking the LSP has a higher probability. Fat base classes have a lots of functionality and methods and make it harder to create elegant and precise inheritance trees. 

- Keep interfaces lean and focus. 