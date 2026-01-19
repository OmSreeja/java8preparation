OOPs:
encapsulation: Encapsulation is the process of wrapping data and methods into a single unit and restricting direct access to data to protect it from misuse.
“Encapsulation is mainly used for data hiding, security, and controlled modification of state.”
  
inheritance: Inheritance represents an IS-A relationship where a child class inherits behavior from a parent class.
HAS-A relationship is composition, where one class contains another class as a dependency.
“In real-time applications, composition is preferred over inheritance to avoid tight coupling.”
  
Ploymorphism: Polymorphism allows one interface or parent reference to represent multiple implementations.
Compile-time Polymorphism: “Method overloading – same method name with different parameters, resolved at compile time.”
Runtime Polymorphism (VERY IMPORTANT): “Method overriding – same method signature in parent and child classes. JVM decides the method execution at runtime based on object type.”

Abstraction: “Abstract class can have both abstract and implemented methods, so it provides partial abstraction.”
✔ Can have variables
✔ Single inheritance only

interface: Interface is 100% abstract till Java 7
“Interface provides full abstraction and acts as a contract.”
✔ Multiple inheritance supported
✔ One class can implement multiple interfaces
“In real-time applications, interfaces are preferred over abstract classes because they support loose coupling and multiple inheritance.”
