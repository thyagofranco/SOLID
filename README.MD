
# SOLID Principles 
_________

#[S - Single Responsibility Principle](./srp/README.md)

#[O - Open Closed Principle](./ocp/README.md)

#[L - Liskov Substitution Principle](./lsp/README.md)

#[I - Interface Segregation Principle](./isp/README.md)

#[D - Dependency Inversion Principle](./dip/README.md)

__________

# Top Benefits of Solid Code.
- Easy to understand and reason about.
- Changes are faster and have a minimal risk level.
- Highly maintainable over long periods of time.
- Cost effective.

Solid principles are the foundation of Clean Code, but we have other ways to keep our Architecture Clean making our application easy to change and maintain over time. 
- Constant refactoring. 
- Design Patterns.
- Unit testing with (TDD)


#Problems that appear when SOLID Principles are not used:

## Code fragility : 
### Fragility is the tendency of the software to break in many places every time it is changed. 

[Example] When you change a module Payment and this change in this module breaks unsuspected modules like Persistence and Reporting. 

## Code Rigidity
### Rigidity is the tendency for software to be difficult to change, even in simples ways. Every change causes a cascade of subsequent changes in dependent modules. 

[Example] When you need to modify a Reporting module in for do that, you will need to change modules Documents and Persistence. 

-------------------

Fragility and rigidity are symptoms of high technical debt. 


# TECHNICAL DEBT
The cost of prioritizing fast delivery over code quality for long periods of time.

Fast Delivery x Code quality 

Technical debt make cost of change grow over time. 
We add cost of customer responsiveness when our project have to much technical debt. 

## Technical debt Facts:
- No matter how good the team is, technical debt will accumulate over time.
- Left uncontrolled, it will kill your project.
- The keys is to keep it under control.  

## Controlling Technical Debt 
- Write Code 
- Pay debt (refactor, apply solid principles, apply design patterns, decouple components, write tests ).
- Repeat 

---------------------

References: 
Phan, Manh. Understanding about SOLID - Dependency Inversion Principle, 2015. https://ducmanhphan.github.io/2020-01-15-Understanding-about-SOLID-part-5/ 

Geabunea, Dan. SOLID Software Design Principles in Java, 2020.
https://www.pluralsight.com/courses/solid-software-design-principles-java