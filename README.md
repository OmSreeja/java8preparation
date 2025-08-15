# java8preparation
**What is lambda expression..?**
 A lambda expression is a short, anonymous way to write an implementation of a functional interface.
 It’s a concise replacement for creating an anonymous inner class.
 Runnable r = () -> System.out.println("Running");
Comparator<String> c = (a, b) -> a.compareTo(b);
**Benefits:**
Less boilerplate code
Improved readability
Enables functional programming
Cleaner use with Streams, Collections, Threading
 **What is a Functional Interface?**
A functional interface is simply an interface that has only one abstract method.
This is important because Lambda expressions can only be used with functional interfaces.
** Why only one abstract method?**
Because the lambda expression doesn't specify which method it wants to implement.
If there were multiple abstract methods, Java wouldn’t know which one the lambda refers to.
@FunctionalInterface
interface MyPrinter {
    void print(String message);
}
This interface has one abstract method print(String) So you can write a lambda for it:
MyPrinter p = msg -> System.out.println("Message: " + msg);
p.print("Hello!");
**Rule of Functional Interface:**
Must have only one abstract method
Can have:
default methods
static methods
methods from Objectclass (liketoString(), equals()`)
**Built-in Functional Interfaces in Java 8**
Interface	Method	Description
Predicate<T>	test(T t)	Returns boolean
Function<T,R>	apply(T t)	Returns result of R
Consumer<T>	accept(T t)	Consumes, returns void
Supplier<T>	get()	Supplies a value
**Difference between lambda and anonymous inner class?**
**What is "Effectively Final"?**
A variable is effectively final if: It is not explicitly declared as final,
but you never change its value after assigning it once.
In other words, it acts like a final variable, even if you didn’t write final
Lambda expressions (and anonymous inner classes) can only use local variables from the enclosing method if they are final or effectively final.
This is because Java captures the variable's value, not its reference, to ensure thread safety and avoid unexpected behavior.
public void test() {
    String greeting = "Hello"; // effectively final
    Runnable r = () -> System.out.println(greeting); // ✅ OK
    r.run();
}
Here, greeting is not declared final, but we never change it, so Java treats it as effectively final.
**Fix with Wrapper**
public class Test {
    public void sum(List<Integer> nums) {
        final int[] total = {0};

        nums.forEach(n -> total[0] += n);

        System.out.println("Total: " + total[0]);
    }
}
Will this compile and run? Answer: Yes
Why?
total is a final reference to an array. Its content can be modified, which is a workaround
**AtomicInteger Example**
public class Test {
    public void countEven(List<Integer> nums) {
        AtomicInteger count = new AtomicInteger(0);
        nums.forEach(n -> {
            if (n % 2 == 0) count.incrementAndGet();
        });
        System.out.println("Even count: " + count.get());
    }
}
Will this compile and print even count? Answer: Yes
Why?
AtomicInteger is mutable and used to safely modify values inside lambdas (also thread-safe).
**Consumer**<T> – "Consumes but doesn't return"
 A printer — it takes data, prints it, returns nothing.
Consumer<String> printer = message -> System.out.println("Printing: " + message);
printer.accept("Hello World");
Method: void accept(T t)
**Predicate**<T> – "Tests a condition, returns boolean"
A filter gate — only lets certain things pass.
Predicate<Integer> isEven = num -> num % 2 == 0;
System.out.println(isEven.test(4)); // true
Method: boolean test(T t)
**Function**<T, R> – "Takes input, returns transformed output"
 A vending machine — input a number, get an item.
Function<String, Integer> lengthFunc = str -> str.length();
System.out.println(lengthFunc.apply("Sreeja")); // 6
Method: R apply(T t)
**Supplier**<T> – "Gives data, takes nothing"
 A gift box — no input, but gives you something.
Supplier<Double> randomGenerator = () -> Math.random();
System.out.println(randomGenerator.get());
Method: T get()
**list.stream()?**
You're converting a collection (like List) into a Stream, which is like a data pipeline.
**.map()** – Transform each item (uses Function<T, R>)
 Changing every lowercase name to uppercase.
names.stream()
     .map(str -> str.toUpperCase())  // 👈 convert to uppercase
     .forEach(System.out::println);
**.filter()** – Keep only items that match a condition (uses Predicate<T>)
Only keep names starting with "S".
names.stream()
     .filter(name -> name.startsWith("S"))  // 👈 check condition
     .forEach(System.out::println);
** .forEach() **– Do something with each item (uses Consumer<T>)
Doing a side task like printing/logging.
names.forEach(name -> System.out.println(name));


