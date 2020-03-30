Java 8 study manual

Code example: https://github.com/aalansehaiyang/java8-tutorial

## table of Contents

* Interface uses default method
* Lambda expression
* Functional interface
* Method and Constructor reference
* Lambda scope
	* Local variable
	* Accessing global or static variables
	* default method
* Method reference
* Built-in Functional Interfaces
	* Judging Predicates
	* Functions
	* Production Suppliers
	* Consumers
	* Compare Comparators
	* Other functional interfaces
* Optionals
* Streams
	* Filter
	* Map
	* FlatMap
	* Sorted
	* Reduce
	* Count
	* Match
	* Skip
	* Output limit
	* Output collect
* Parallel Streams
	* Serial Sort
	* Parallel Sort
* Collection Maps
* Date API
	* clock
	* Time zone
	* local time
	* Local date
	* Local datetime
	* annotation
	
## Interface uses default method
Java 8 allows us to use unstructured methods such as defaultmodifiers in interface classes to implement interfaces.

This feature is described in detail in virtual extension methods .

Example:

interface Formula {
    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
FormulaIn addition to the abstract methods defined in the interface class, the calculatedefault method is also defined sqrt. The implementing class must implement abstract methods calculate. The default method sqrtcan also be used outside the class.

Formula formula = new Formula() {
    @Override
    public double calculate(int a) {
        return sqrt(a * 100);
    }
};

formula.calculate(100);     // 100.0
formula.sqrt(16);           // 4.0
Formulas use anonymous implementation classes. The code is very redundant and requires 6 lines of code to implement a simple calculation sqrt(a * 100). In the next section, you can implement a method more cleverly with Java 8.

## Lambda expression
A simple example, sorting a collection of strings

List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return b.compareTo(a);
    }
});
Static method Collections.sortreceiving a set of list and a comparator of the comparator in order to achieve sorting. Usually you need to create an anonymous comparator and pass it to the sort method.

To replace anonymous class instances, Java 8 introduced a very concise syntax, lambda expressions :

Collections.sort(names, (String a, String b) -> {
    return b.compareTo(a);
});
Of course, you can also use a shorter and more readable form, as above.

Collections.sort(names, (String a, String b) -> b.compareTo(a));
Can be further streamlined, leaving only one line of code, omissions {}and returnmethods, as above.

names.sort((a, b) -> b.compareTo(a));
The List class now provides sortmethods. At the same time, the java compiler can automatically recognize the parameter types, so you can ignore them when coding. Let's dive into how lambda expressions are widely used.

## Functional interface
How do lambda expressions identify Java system types? Each lambda corresponds to a type specified by the interface. So every definition of _functional interface_ must include an abstract method declaration. The type of each lambda expression needs to match the abstract method. Since the default method is not abstract, you need to add the default method to the function interface.

We can arbitrarily define an interface as a lambda expression, which needs to contain an abstract method internally. In order to ensure that the interface to meet the specifications, you need to add @FunctionalInterfaceannotations. Once you try to add a second abstract method, the compiler will automatically detect and throw a compilation error.

Example:

@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}
Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
Integer converted = converter.convert("123");
System.out.println(converted);    // 123


Remember, if omitted @FunctionalInterface, the code is also valid.

Method and Constructor reference
The above code example can be further simplified with static method references:

Converter<String, Integer> converter = Integer::valueOf;
Integer converted = converter.convert("123");
System.out.println(converted);   // 123
Java 8 allows you to pass references to methods or constructors, such as ::. The example above demonstrates referencing a static method. In addition, we can also use the method of the class instance object.

class Something {
    String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}
Something something = new Something();
Converter<String, String> converter = something::startsWith;
String converted = converter.convert("Java");
System.out.println(converted);    // "J"
Let us know the next ::word key word How in the constructor. First define a class with the following structure:

class Person {
    String firstName;
    String lastName;

    Person() {}

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
Next, we define a person factory interface for creating new persons:

interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}
Unlike the traditional implementation, we implement it by calling the constructor method:

PersonFactory<Person> personFactory = Person::new;
Person person = personFactory.create("Peter", "Parker");
We Person::newtriggering Person constructor function. The Java compiler can automatically select the appropriate constructor function to match PersonFactory.create.

## Lambda scope
Lambda expressions access external variables similar to anonymous objects. You can access the final local local variables.

#### Local variable
We can read final decorated local variables

final int num = 1;
Converter<Integer, String> stringConverter =
        (from) -> String.valueOf(from + num);

stringConverter.convert(2);     // 3
Unlike anonymous objects, variables numdo not necessarily have to be decorated with final. The following writing is also valid:

int num = 1;
Converter<Integer, String> stringConverter =
        (from) -> String.valueOf(from + num);

stringConverter.convert(2);     // 3
numMust be implicitly final when compiling code. The following compilation will give an error:

int num = 1;
Converter<Integer, String> stringConverter =
        (from) -> String.valueOf(from + num);
num = 3;

### Accessing global or static variables
In contrast to local variables, we can read or write global or static global variables in lambda expressions.

class Lambda4 {
    static int outerStaticNum;
    int outerNum;
	
	// ??
    void testScopes() {
        Converter<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };

        Converter<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }
}

### default method
Remember the example formula in the first section? FormulaThe interface class defines a default method sqrtthat can be accessed by each formula instance object, including anonymous objects. But this does not apply to lambda expressions.

Lambda expression statements cannot directly access the default method. However, the following compilation does not work:

Formula formula = (a) -> sqrt(a * 100);

## Method reference
Method references have the same characteristics as lambda expressions (for example, they both need a target type and need to be converted to an instance of a functional interface), but we don't need to provide a method body for method references, we can pass the method name directly Reference an existing method.

There are three cases of method references:

1) Class + dynamic method
2) Class + static method
3) Class instance object + dynamic method
4) Class instance object + static method (invalid, incorrect writing)
    public static void main(String[] args) {

        // 1
        BiConsumer<LinkedHashSet, Object> biConsumer1 = LinkedHashSet::add;
        LinkedHashSet s1 = new LinkedHashSet();
        biConsumer1.accept(s1, "aaa");
        System.out.println(s1);

        // 2
        BiConsumer<String, Long> biConsumer2 = Utils::concatStatic;
        biConsumer2.accept("first_param", 6L);

        // 3
        BiConsumer<String, Long> biConsumer3 = new Utils()::concat;
        biConsumer3.accept("first_param", 7L);

        // 4
        // Error:(35, 48)
        // BiConsumer<String, Long> biConsumer4 = new Utils()::concatStatic;
        // biConsumer4.accept("first_param", 8L);

    }
Array constructor that takes int parameters

IntFunction<int[]> arrayMaker = int[]::new;
int[] array = arrayMaker.apply(10) // int[10]

## Built-in Functional Interfaces
The JDK 1.8 API provides many built-in functional interfaces. Some of them are from older versions of Java, such as Comparatoror Runnable. By @FunctionalInterfaceannotation expand those already existing interfaces to achieve lambda support.

At the same time, the Java 8 API also provides some new functional interfaces to meet the needs of multiple scenarios. Some new features come from the Google Guava tripartite library.

### Judging Predicates
Predicates internally defines a boolean type judgment method with an input parameter. This interface also contains many default methods to satisfy various complex logical expressions, such as (AND, OR, NOT)


Predicate<String> predicate = (s) -> s.length() > 0;

predicate.test("foo");              // true
predicate.negate().test("foo");     // false

Predicate<Boolean> nonNull = Objects::nonNull;
Predicate<Boolean> isNull = Objects::isNull;

Predicate<String> isEmpty = String::isEmpty;
Predicate<String> isNotEmpty = isEmpty.negate();

### Functions
The function takes an input parameter and returns a result. defaultMethods are used to link multiple functional functions together (execute before compose, and after theThen)


Function<String, Integer> toInteger = Integer::valueOf;
Function<String, String> backToString = toInteger.andThen(String::valueOf);

backToString.apply("123");     // "123"
Production Suppliers
Suppliers produce results of the specified type. Unlike Functions, Suppliers do not accept any parameters.


Supplier<Person> personSupplier = Person::new;
personSupplier.get();   // new Person
Case: java8 introduced an overloaded version of the log method. This version of the log method accepts a Supplier as a parameter. The function signature of this alternative version of the log method is as follows:

public void log(Level level, Supplier<String> msgSupplier)

logger.log(Level.FINER, () -> "Problem: " + generateDiagnostic());

????? Log ?????????:
public void log(Level level, Supplier<String> msgSupplier){
    if(logger.isLoggable(level)){
        log(level, msgSupplier.get());
    }
}

## Consumers
Consumers represents processing of a single input parameter, and provides andThen 'default' method for subsequent processing.


Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
greeter.accept(new Person("Luke", "Skywalker"));
Compare Comparators
Comparators existed in older versions of Java. Java 8 adds various defaultmethods


Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);

Person p1 = new Person("John", "Doe");
Person p2 = new Person("Alice", "Wonderland");

comparator.compare(p1, p2);             // > 0
comparator.reversed().compare(p1, p2);  // < 0
Comparator is used in combination with reversed to support sorting of multiple fields.

List<Score> list = new ArrayList<>();
list.add(new Score("xiaohong", 90L, 91L));
list.add(new Score("xiaoming", 85L, 90L));
list.add(new Score("wanggang", 90L, 96L));
list.add(new Score("xiaoma", 85L, 70L));

Collections.sort(list, Comparator.comparing(Score::getYuwen).thenComparing(Score::getShuxue));
list.forEach(System.out::println);

Comparator c1 = Comparator.comparing(Score::getYuwen).reversed();
Comparator c2 = Comparator.comparing(Score::getShuxue).reversed();
Collections.sort(list, c1.thenComparing(c2));
list.forEach(System.out::println);
Other functional interfaces
Like BinaryOperator, etc.


## Optionals
Optionals is not a functional interface, mainly for prevention NullPointerException. The next section will focus on it. Now let's first understand how Optionals work.

Optional is a simple container for storing null or non-null values. Imagine that a method with a return value sometimes returns null. Instead, Java 8 returns yes Optional, not null.

??:com.winterbe.java8.samples.stream.Optional1

Optional<String> optional = Optional.of("bam");

optional.isPresent();           // true
optional.get();                 // "bam"
optional.orElse("fallback");    // "bam"

optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"

## Streams
java.util.StreamYou can perform one or more operations on the element list. Stream operations can be intermediate or final results. The last operation returned some type of result, while the intermediate operation returned the stream itself. So you can chain multiple method calls in one line of code. Streams are created java.util.Collection, such as list or set (map is not supported). Streams can be executed sequentially or in parallel.

Streams is very powerful, so I wrote a separate article about Java 8 Streams Tutorial . Codebase Sequency

filter?map?mapToInt?mapToLong?mapToDouble?flatMap?sorted?distinct?limit?skip?of?iterate
forEach?count?collect?reduce?toArray?anyMatch?allMatch?noneMatch?findAny?findFirst?max?min
Primitive type specialization stream: IntStream, LongStream, DoubleStream

### Filter
Filter uses a predicatejudgment function to filter all elements. This operation is an intermediate operation and needs to be terminated to trigger execution.

??:com.winterbe.java8.samples.stream.Stream_filter
stringCollection
    .stream()
    .filter((s) -> s.startsWith("a"))
    .forEach(System.out::println);

// "aaa2", "aaa1"

### Map
mapIs an intermediate procedure operation that transforms an element into another form with the help of a function expression. The following example converts each string to an uppercase string. But you can also use to mapconvert each object to another type. The final result type depends on the function expression you pass in.

stringCollection
    .stream()
    .map(String::toUpperCase)
    .sorted((a, b) -> b.compareTo(a))  //????
    .forEach(System.out::println);

// "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"

### FlatMap
If one-to-many mapping is involved, the mapping result needs to be put into a Stream. The effect of using the flatMap method is that multiple converted results are not mapped into a stream, but are mapped into the content of the stream.

??:com.winterbe.java8.samples.stream.Stream_flatMap

List<String> lists = Arrays.asList("Hello", "World");
lists.stream().flatMap(word-> Stream.of(word.split("")))
        .distinct()
        .forEach(System.out::println);
		
###  Sorted
Sorted is an intermediate state operation that returns an ordered view of the stream. Unless you pass a custom one Comparator, the elements are ????sorted by default .


stringCollection.stream().sorted().forEach(System.out::println);
System.out.println(stringCollection);
stringCollection
        .stream()
        .map(String::toUpperCase)
        .sorted((a, b) -> b.compareTo(a))
        .forEach(System.out::println);
		
### Reduce
Termination operation, which uses the given function expression to process two elements before or after, or the intermediate result and the next element. Lambda combines each element repeatedly until the stream is reduced to a value. For example, summing or finding the largest element.


// ??????????,sum???0,????? (sum, p) -> sum = sum + p.age,?????sum?????????
// ???? (sum1, sum2) -> sum1 + sum2 ,???????
// (sum1, sum2) -> sum1 + sum2,???????,parallelStream(),????????

private static void test3(List<Person> persons) {
    Integer ageSum = persons.parallelStream().reduce(0, (sum, p) -> sum += p.age, (sum1, sum2) -> sum1 + sum2);
    System.out.println(ageSum);
}

### Count
Count is a terminating operation that returns the total number of elements of a long type.

long startsWithB =
    stringCollection
        .stream()
        .filter((s) -> s.startsWith("b"))
        .count();
        
System.out.println(startsWithB);    // 3

### Match
Various matching operations are used to determine whether the stream condition is met. After all operations are completed, a boolean result is returned.


List<String> stringCollection = new ArrayList<>();
stringCollection.add("ddd2");
stringCollection.add("aaa2");
stringCollection.add("bbb1");
stringCollection.add("aaa1");
stringCollection.add("bbb3");
stringCollection.add("ccc");
stringCollection.add("bbb2");
stringCollection.add("ddd1");

boolean anyStartsWithA = stringCollection.stream().anyMatch((s) -> s.startsWith("a"));
System.out.println("anyMatch:" + anyStartsWithA); // true

boolean allStartsWithA = stringCollection.stream().allMatch((s) -> s.startsWith("a"));
System.out.println("allMatch:" + allStartsWithA); // false

boolean noneStartsWithZ = stringCollection.stream().noneMatch((s) -> s.startsWith("z"));
System.out.println("noneMatch:" + noneStartsWithZ); // true

Optional<String> anyE = stringCollection.stream().findAny();
System.out.println("findAny:" + anyE.get());


Optional<String> firstE = stringCollection.stream().findFirst();
System.out.println("findFirst:" + firstE.get());
Skip
Returns a stream throwing away the first n elements


stringCollection
    .stream()
    .skip(3)
    .forEach(System.out::println);

Output limit
Take only the first N results


stringCollection
    .stream()
    .limit(3)
    .forEach(System.out::println);

Output collect
Accepts various practices as parameters and accumulates elements in the stream into a summary result

Common examples:

Group a transaction list by currency to get the sum of all transactions in that currency (returns a Map <Currency, Integer>)
Divide the transaction list into two groups, expensive and inexpensive (return a Map <Boolean, List <Transaction >>)
Create multiple levels of grouping, such as grouping transactions by city, and then further group by expensive or not expensive
Collectors common methods:

Collectors.toList, get List list
Collectors.toSet, get the Set collection
Collectors.joining by ???joining strings
Collectors.groupingBy (Function <? Super T ,? extends K>), group by K value, return Map <K, List>
Collectors.groupingBy (Function <? Super T ,? extends K>, Collector <? Super T, A, D>), two-level grouping to get two-level Map
Collectors.partitioningBy (Predicate <? Super T> predicate). Partitioning is a special case of grouping. A Boolean value is returned, which means that the key of the grouped Map can only be Boolean, so it can be divided into two groups at most.
Collectors.maxBy, to find the maximum value, you need to pass a custom Comparator
Collectors.reducing, generalized reduction summary.
Collectors.toMap to get the Map collection. Note: If the key is repeated, an exception will be thrown. Special treatment is required.

List<String> citys = Arrays.asList("USA", "Japan", "France");
String cityS = citys.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        

Map<String, List<Student>> maps = studentList.stream().collect(Collectors.groupingBy(Student::getSex));

Map<String, Map<String, List<Student>>> maps = studentList.stream()
   .collect(Collectors.groupingBy(Student::getSex,
      Collectors.groupingBy(s -> {
          if (s.getAge() < 20) {
              return "?age";
          } else {
              return "?age";
          }
      })));

Map<Boolean, List<Student>> maps = studentList.stream().collect(Collectors.partitioningBy(s -> {
    if (s.getAge() < 25) {
        return true;
    } else {
        return false;
    }
}));

Optional<Student> optional1 = studentList.stream().collect(Collectors.maxBy(Comparator.comparing(Student::getAge)));
optional1.ifPresent(System.out::println);


// reducing
int sum = studentList.stream().collect(Collectors.reducing(0, Student::getAge, Integer::sum));


private static void test8(List<Student> studentList) {
    Map<String, String> sexNameMap = studentList.stream().collect(Collectors.toMap(p -> {
        return p.getSex();
    }, p2 -> {
        return p2.getName();
    }, (oldValue, newValue) -> newValue));
    System.out.println(sexNameMap);
}


## Parallel Streams
As described below, streams can be executed serially or in parallel. The serial execution of a stream is done by a single thread. Parallel stream processing is performed simultaneously on multiple threads.

The following example will demonstrate how to significantly improve performance through parallel stream processing.

First we create a large collection of List elements:

int max = 1000000;
List<String> values = new ArrayList<>(max);
for (int i = 0; i < max; i++) {
    UUID uuid = UUID.randomUUID();
    values.add(uuid.toString());
}
Now we measure the time it takes to sort the streams of this collection.

Serial Sort
long t0 = System.nanoTime();

long count = values.stream().sorted().count();
System.out.println(count);

long t1 = System.nanoTime();

long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
System.out.println(String.format("sequential sort took: %d ms", millis));

// sequential sort took: 899 ms
Code: com.winterbe.java8.samples.stream.Streams3

Parallel Sort
long t0 = System.nanoTime();

long count = values.parallelStream().sorted().count();
System.out.println(count);

long t1 = System.nanoTime();

long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
System.out.println(String.format("parallel sort took: %d ms", millis));

// parallel sort took: 472 ms
Code: com.winterbe.java8.samples.stream.Streams3

As you can see, the two code snippets are almost the same. But parallel sorting is about 50% faster. So what you have to do is stream()replace it with parallelStream().

## Collection Maps
As mentioned earlier, Maps does not directly support streams. Map interface does not provide stream()related methods. However, you can by means of map.keySet().stream(), map.values().stream()and map.entrySet().stream()create flow-based keys, values, or the entries.

In addition, maps provides some new and useful methods to support routine tasks.

Code: com.winterbe.java8.samples.misc.Maps1

Map<Integer, String> map = new HashMap<>();

for (int i = 0; i < 10; i++) {
    map.putIfAbsent(i, "val" + i);
}

map.forEach((id, val) -> System.out.println(val));
putIfAbsentIf it is empty, execute put, otherwise return the value corresponding to the key, which can avoid the redundancy of the code for an empty judgment. forEachOperates internally via BiConsumer.

The following example shows how map is calculated using functions:

map.computeIfPresent(3, (num, val) -> val + num);
map.get(3);             // val33

map.computeIfPresent(9, (num, val) -> null);
map.containsKey(9);     // false

map.computeIfAbsent(23, num -> "val" + num);
map.containsKey(23);    // true

map.computeIfAbsent(3, num -> "bam");
map . get ( 3 );              // val33 (execute if missing)
Next, we learn how to delete entries by a given key, provided that it currently has a kv mapping:

// The value corresponding to 3 is equal to "val3" before the delete action 
map is performed . Remove ( 3 , " val3 " );
map.get(3);             // val33

map.remove(3, "val33");
map.get(3);             // null
Other useful methods:

// Return the value associated with the key, otherwise return the default 
map . GetOrDefault ( 42 , " not found " );   // not found
Merging the entries in the map is also very easy:

map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
map.get(9);             // val9

map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
map.get(9);             // val9concat
If the key-value key-value pair does not exist, it is merged into the map. Otherwise execute the function to change its value.

## Date API
Java 8 provides a new date and time API, located in java.timethe package below. The new date API is comparable to the Joda-Time library, but it's not the same . The following example covers the most important parts of this new API.

### clock
The clock provides access to the current date and time. The clock knows the time zone and can be used instead System.currentTimeMillis()to retrieve the current time (in milliseconds) since Unix EPOCH. It is indicated at a certain time on the time axis Instant. InstantYou can create legacy java.util.Dateobjects.

Clock provides access to the current date and time. Clocks are aware of a timezone and may be used instead of System.currentTimeMillis() to retrieve the current time in milliseconds since Unix EPOCH. Such an instantaneous point on the time-line is also represented by the class Instant. Instants can be used to create legacy java.util.Date objects.

Clock clock = Clock.systemDefaultZone();
long millis = clock.millis();

Instant instant = clock.instant();
Date legacyDate = Date.from(instant);   // legacy java.util.Date
??:com.winterbe.java8.samples.time.LocalTime1

Time zone
Time zone through ZoneIdto represent, it provides a lot of static methods. Time zones define important offsets that translate between instants and local dates and times.

System.out.println(ZoneId.getAvailableZoneIds());
// prints all available timezone ids

ZoneId zone1 = ZoneId.of("Europe/Berlin");
ZoneId zone2 = ZoneId.of("Brazil/East");
System.out.println(zone1.getRules());
System.out.println(zone2.getRules());

// ZoneRules[currentStandardOffset=+01:00]
// ZoneRules[currentStandardOffset=-03:00]
??:com.winterbe.java8.samples.time.LocalTime1

local time
LocalTime represents time without time zone, such as 10pm or 17:30:15. The following example creates two local times with time zones. We compare these two times and calculate the hour or minute difference between the two.

LocalTime now1 = LocalTime.now(zone1);
LocalTime now2 = LocalTime.now(zone2);

System.out.println(now1.isBefore(now2));  // false

long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

System.out.println(hoursBetween);       // -3
System.out.println(minutesBetween);     // -239
LocalTime provides many factory methods for creating various new instances, including parsing time strings.

LocalTime late = LocalTime.of(23, 59, 59);
System.out.println(late);       // 23:59:59

DateTimeFormatter germanFormatter =
    DateTimeFormatter
        .ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(Locale.GERMAN);

LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
System.out.println(leetTime);   // 13:37

Local date
LocalDate represents a different date, such as 2014-03-11. It is immutable and is completely similar to LocalTime. The example demonstrates adding or subtracting days, months, and years to calculate a new date. Remember that each operation returns a new instance.

LocalDate today = LocalDate.now();
LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
LocalDate yesterday = tomorrow.minusDays(2);

LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
System.out.println(dayOfWeek);    // FRIDAY
Parsing a LocalDate from a string is as simple as parsing a LocalTime:

DateTimeFormatter germanFormatter =
    DateTimeFormatter
        .ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.GERMAN);

LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter);
System.out.println(xmas);   // 2014-12-24
??:com.winterbe.java8.samples.time.LocalDate1

Local datetime
LocalDateTime represents the date and time. It contains an example of the date and time above. LocalDateTimeIt is immutable and it works similarly to LocalTime and LocalDate. We can retrieve some fields using datetime:


LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
System.out.println(dayOfWeek);      // WEDNESDAY

Month month = sylvester.getMonth();
System.out.println(month);          // DECEMBER

long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
System.out.println(minuteOfDay);    // 1439
With the additional information of the time zone, it can be converted into time. Easily convert instances to java.util.Dateold dates of type.

Instant instant = sylvester
        .atZone(ZoneId.systemDefault())
        .toInstant ();

Date legacyDate = Date.from(instant);
System.out.println(legacyDate);     // Wed Dec 31 23:59:59 CET 2014
Formatting a date-time is just like formatting a date or time. Instead of using a predefined format, we can create a formatter using a custom pattern.

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd, yyyy - HH:mm");
        LocalDateTime parsed = LocalDateTime.parse("07 25, 2017 - 14:00", formatter);
        System.out.println(parsed); // 2017-07-25T14:00
And java.text.NumberFormatdifferent, DateTimeFormatter is immutable, thread-safe .

For more syntax details, refer here .

## annotation
Annotations in Java 8 are repeatable. Let us illustrate with an example.

First, we define a wrapper annotation that contains an array of actual annotations:



@interface Hints {
    Hint[] value();
}

@Repeatable(Hints.class)
@interface Hint {
    String value();
}
Java 8 by declaring annotation allows us @Repeatableto use multiple annotations of the same type.

Form 1: using container annotations (old way)
@Hints({@Hint("hint1"), @Hint("hint2")})
class Person {}
Form 2: using repeatable annotations (new method)
@Hint("hint1")
@Hint("hint2")
class Person {}
Form 2, the java compiler implicitly sets @Hintsannotations. This is important for reading annotation information via reflection.

Hint hint = Person.class.getAnnotation(Hint.class);
System.out.println(hint);                   // null

Hints hints1 = Person.class.getAnnotation(Hints.class);
System.out.println(hints1.value().length);  // 2

Hint[] hints2 = Person.class.getAnnotationsByType(Hint.class);
System.out.println(hints2.length);          // 2
Although we have never in Personthe definition of the class @Hintsnotes, but it can still getAnnotation(Hints.class)be read. However, more convenient getAnnotationsByTypeaccess to all with @Hintcomments.

In addition, Java 8 annotations extend two new targets:

@Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
@interface MyAnnotation {}
