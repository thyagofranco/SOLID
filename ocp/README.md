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

```java

``` 