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

**Longest length**

 List<String> names = List.of("Sreeja", "Arjun", "Sara", "John");
 
Optional<String> longestName = names.stream() .max((name1, name2) -> Integer.compare(name1.length(), name2.length()));

longestName.ifPresent(System.out::println);

**Shorter using Comparator.comparingInt():**

import java.util.Comparator;

String longest = names.stream()
    .max(Comparator.comparingInt(String::length))
    .orElse("No name");
	
    System.out.println(longest);

 **What is Stream API?**
The Stream API in Java 8 is a declarative way (what to do, not how) to process collections of data — like filtering, mapping, sorting, counting, etc.
Old way (imperative): loop with for, if, etc.
New way (declarative): use stream().filter().map().collect()
Imagine a factory conveyor belt:
Items go through a pipeline of steps like:
Filter
Transform
Sort
Collect
Same thing happens in Java with Stream pipelines!

**Filter names starting with "S"**

List<String> names = List.of("Sreeja", "Anil", "Sara");

names.stream()
     .filter(name -> name.startsWith("S"))  // Predicate
     .forEach(System.out::println);

**Collect to a List**

List<String> result = names.stream()
    .filter(name -> name.length() > 4)
    .collect(Collectors.toList());
	
System.out.println(result);

**What is a Method Reference?**

Method reference is a shorter way of writing a lambda expression that calls an existing method.

Instead of writing:

x -> something(x)

You can write:

ClassName::something

**Types of Method References**

Syntax	Used For	Example

ClassName::staticMethod	Static methods	Math::abs

objectRef::instanceMethod	Instance method of a particular object	System.out::println

ClassName::instanceMethod	Instance method of any object of a class	String::toLowerCase

ClassName::new	Constructor reference	ArrayList::new

**Static Method Reference**

➡ClassName::staticMethod

Function<Integer, Integer> absFunc = Math::abs;

System.out.println(absFunc.apply(-5))

**Instance Method of Specific Object**

objectRef::instanceMethod

List<String> list = List.of("A", "B", "C");

list.forEach(System.out::println);

**Instance Method of Arbitrary Object**

ClassName::instanceMethod

List<String> names = List.of("Sreeja", "Arjun");
names.stream()
     .map(String::toUpperCase)  // same as: name -> name.toUpperCase()
     .forEach(System.out::println);

**Constructor Reference**

ClassName::new

Supplier<List<String>> listSupplier = ArrayList::new;

List<String> myList = listSupplier.get();

** What is Optional in Java?**

Optional<T> is a container object introduced in Java 8, which may or may not contain a non-null value.
It helps you write safer code by avoiding NullPointerException (NPE).

**Real-Life Analogy:**
Optional is like a sealed gift box:
You don't know if there's a gift inside (value) or not.
You must open it carefully, not assume it's always full.

 **How to Create an Optional?**
Optional<String> empty = Optional.empty();         // No value

Optional<String> name  = Optional.of("Sreeja");    // Value present

Optional<String> maybe = Optional.ofNullable(null); // Might be null

**Using ifPresent()**
Optional<String> name = Optional.of("Sreeja");

name.ifPresent(n -> System.out.println(n.toUpperCase()));
**2. Using orElse()**
Optional<String> name = Optional.ofNullable(null);

System.out.println(name.orElse("Guest"));  // Output: Guest
**3. Using map()**
Optional<String> name = Optional.of("sreeja");

Optional<String> upper = name.map(String::toUpperCase);

System.out.println(upper.get());  // Output: SREEJA
** 4. Using filter()**

Optional<String> name = Optional.of("Sreeja");

name.filter(n -> n.startsWith("S"))
    .ifPresent(System.out::println);  // Output: Sreeja

**1. What Are Terminal Operations?**
In Stream API, operations are of two types:

Type	Examples	Behavior

Intermediate	map(), filter(), sorted()	Returns a new stream, does not execute

Terminal	collect(), forEach(), count()	Triggers execution of stream pipeline

**What Is Collectors?**

Collectors is a utility class that provides ready-made collectors to convert a stream into:

List

Set

Map

String (joined)

Count, average, groupings

Commonly Used Collectors:

Collector	Use Case	Example

Collectors.toList()	Convert stream to list	.collect(Collectors.toList())

Collectors.toSet()	Convert to set (no duplicates)	.collect(Collectors.toSet())

Collectors.joining()	Join strings	.collect(Collectors.joining(", "))

Collectors.counting()	Count elements	.collect(Collectors.counting())

Collectors.averagingInt()	Average of int values	.collect(Collectors.averagingInt(...))

Collectors.groupingBy()	Group by field or logic	.collect(Collectors.groupingBy(...))

Collectors.partitioningBy()	True/false split	.collect(Collectors.partitioningBy(...))

 **What is a Parallel Stream?**
A parallel stream is a feature in Java 8 that allows you to process data using multiple threads, automatically.
Internally uses Java’s Fork/Join framework to split the work and combine the result.

List<String> names = List.of("Sreeja", "Arjun", "Sara", "John", "Kiran");

names.parallelStream()  // 👈 instead of .stream()
     .map(String::toUpperCase)
     .forEach(System.out::println);

  **Behind the Scenes**
  
The stream is divided into chunks.

Each chunk is processed by a different thread.

All results are merged back automatically.

**When to Use Parallel Stream?**
Yes:
Heavy data processing
CPU-intensive operations
Independent tasks

 No:
When order matters
Small datasets
When using non-thread-safe code

**What is the difference between .map() and .flatMap()?**
.map()
Transforms each element using a function.
The result is one output per input.
If each input returns a List, you get a Stream of Lists.
List<String> words = List.of("java", "spring");

List<List<String>> mapped = words.stream()
    .map(word -> List.of(word.split("")))
    .collect(Collectors.toList());

System.out.println(mapped);
Output:

[[j, a, v, a], [s, p, r, i, n, g]]
Here, you get a List<List<String>> → stream inside stream.

✅ **.flatMap()**
Flattens multiple streams into a single stream.

Best used when your .map() produces nested structures (like List of List, or Stream of Stream).
List<String> words = List.of("java", "spring");

List<String> flatMapped = words.stream()
    .flatMap(word -> Arrays.stream(word.split("")))
    .collect(Collectors.toList());

System.out.println(flatMapped);
🔸 Output:
[j, a, v, a, s, p, r, i, n, g]

What is reduce()?
reduce() is a terminal operation in Java Stream API that:

Combines all elements in the stream into a single result
(by repeatedly applying a binary operation).
Think: ✨ Reduce the stream to one value

**Sum of Numbers**

List<Integer> nums = List.of(1, 2, 3, 4, 5);

int sum = nums.stream()
              .reduce(0, (a, b) -> a + b);  // Identity = 0

System.out.println(sum); // Output: 15

**Group names by starting letter**
List<String> names = List.of("Sreeja", "Sara", "John", "Arjun");

Map<Character, List<String>> grouped = names.stream()
    .collect(Collectors.groupingBy(name -> name.charAt(0)));

System.out.println(grouped);

**Date & Time API**
**Why was it introduced?**

Before Java 8:
Dates used java.util.Date and Calendar → ❌ confusing, mutable, and error-prone
Java 8 introduced the new java.time package:

✔ Immutable
✔ Thread-safe
✔ Inspired by Joda-Time
✔ Much easier to use

Class	       Purpose

LocalDate	Represents a date (no time)

LocalTime	Represents a time (no date)

LocalDateTime	Combines date + time

DateTimeFormatter	For formatting and parsing

Period	Difference between dates (years, months, days)

Duration	Difference between times (hours, mins)







