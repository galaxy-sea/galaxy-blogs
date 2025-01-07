<!-- TOC -->

- [1. Lombok //TODO  英文太差了懒得翻译了ヽ(*。>Д<)o゜](#1-lombok-todo--英文太差了懒得翻译了ヽдo゜)
    - [1.1. 安装](#11-安装)
    - [1.2. 注解](#12-注解)
        - [1.2.1. @NonNull](#121-nonnull)
        - [1.2.2. @Cleanup](#122-cleanup)
        - [1.2.3. @Getter and @Setter](#123-getter-and-setter)
        - [1.2.4. @ToString](#124-tostring)
        - [1.2.5. @EqualsAndHashCode](#125-equalsandhashcode)
        - [1.2.6. @NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor](#126-noargsconstructor-requiredargsconstructor-and-allargsconstructor)
        - [1.2.7. @Data](#127-data)
        - [1.2.8. @Value](#128-value)
        - [1.2.9. @Builder](#129-builder)
        - [1.2.10. @SneakyThrows](#1210-sneakythrows)
        - [1.2.11. @Synchronized](#1211-synchronized)
        - [1.2.12. @Getter(lazy=true)](#1212-getterlazytrue)
        - [1.2.13. @Log (and friends)](#1213-log-and-friends)

<!-- /TOC -->

# 1. Lombok //TODO  英文太差了懒得翻译了ヽ(*。>Д<)o゜
在项目中使用Lombok可以减少很多重复代码的书写。比如说getter/setter/toString等方法的编写。
## 1.1. 安装
官网:https://www.projectlombok.org/
>ide插件下载  ``不下载插件ide会疯狂的~~~///(^v^)\\\~~~``

>maven导包  ``官网自己look``,反正我是没有选择版本呀哈哈
```xml
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
```

## 1.2. 注解
| 注解                                                                  | 使用对象   | 描述                                                                                                                                                      |
| --------------------------------------------------------------------- | ---------- | --------------------------------------------------------------------------------------------------------------------------------------------------------- |
| val                                                                   | 123        | Finally! Hassle-free final local variables.                                                                                                               |
| var                                                                   | 123        | Mutably! Hassle-free local variables.                                                                                                                     |
| @NonNull                                                              | 方法和参数 | or: How I learned to stop worrying and love the NullPointerException.                                                                                     |
| @Cleanup                                                              | 123        | Automatic resource management: Call your close() methods safely with no hassle.                                                                           |
| @Getter/@Setter                                                       | 123        | Never write public int getFoo() {return foo;} again.                                                                                                      |
| @ToString                                                             | 123        | No need to start a debugger to see your fields: Just let lombok generate a toString for you!                                                              |
| @EqualsAndHashCode                                                    | 123        | Equality made easy: Generates hashCode and equals implementations from the fields of your object..                                                        |
| @NoArgsConstructor<br>@RequiredArgsConstructor<br>@AllArgsConstructor | 123        | Constructors made to order: Generates constructors that take no arguments, one argument per final / non-nullfield, or one argument for every field.       |
| @Data                                                                 | 123        | All together now: A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, and @Setter on all non-final fields, and @RequiredArgsConstructor! |
| @Value                                                                | 123        | Immutable classes made very easy.                                                                                                                         |
| @Builder                                                              | 123        | ... and Bob's your uncle: No-hassle fancy-pants APIs for object creation!                                                                                 |
| @SneakyThrows                                                         | 123        | To boldly throw checked exceptions where no one has thrown them before!                                                                                   |
| @Synchronized                                                         | 123        | synchronized done right: Don't expose your locks.                                                                                                         |
| @Getter(lazy=true)                                                    | 123        | Laziness is a virtue!                                                                                                                                     |
| @Log                                                                  | 123        | Captain's Log, stardate 24435.7: "What was that line again?"                                                                                              |  |
### 1.2.1. @NonNull

You can use @NonNull on the parameter of a method or constructor to have lombok generate a null-check statement for you.

Lombok has always treated any annotation named @NonNull on a field as a signal to generate a null-check if lombok generates an entire method or constructor for you, via for example @Data. Now, however, using lombok's own @lombok.NonNull on a parameter results in the insertion of just the null-check statement inside your own method or constructor.

The null-check looks like if (param == null) throw new NullPointerException("param is marked @NonNull but is null"); and will be inserted at the very top of your method. For constructors, the null-check will be inserted immediately following any explicit this() or super() calls.

If a null-check is already present at the top, no additional null-check will be generated.

>With Lombok
```java
public class NonNullExample extends Something {
  private String name;
  
  public NonNullExample(@NonNull Person person) {
    super("Hello");
    this.name = person.getName();
  }
}
```
>Vanilla Java
```java
public class NonNullExample extends Something {
  private String name;
  
  public NonNullExample(@NonNull Person person) {
    super("Hello");
    if (person == null) {
      throw new NullPointerException("person is marked @NonNull but is null");
    }
    this.name = person.getName();
  }
}
```
### 1.2.2. @Cleanup

You can use @Cleanup to ensure a given resource is automatically cleaned up before the code execution path exits your current scope. You do this by annotating any local variable declaration with the @Cleanup annotation like so:
@Cleanup InputStream in = new FileInputStream("some/file");
As a result, at the end of the scope you're in, in.close() is called. This call is guaranteed to run by way of a try/finally construct. Look at the example below to see how this works.

If the type of object you'd like to cleanup does not have a close() method, but some other no-argument method, you can specify the name of this method like so:
@Cleanup("dispose") org.eclipse.swt.widgets.CoolBar bar = new CoolBar(parent, 0);
By default, the cleanup method is presumed to be close(). A cleanup method that takes 1 or more arguments cannot be called via @Cleanup.


>With Lombok
```java
public class CleanupExample {
  public static void main(String[] args) throws IOException {
    @Cleanup InputStream in = new FileInputStream(args[0]);
    @Cleanup OutputStream out = new FileOutputStream(args[1]);
    byte[] b = new byte[10000];
    while (true) {
      int r = in.read(b);
      if (r == -1) break;
      out.write(b, 0, r);
    }
  }
}
```
>Vanilla Java
```java
public class CleanupExample {
  public static void main(String[] args) throws IOException {
    InputStream in = new FileInputStream(args[0]);
    try {
      OutputStream out = new FileOutputStream(args[1]);
      try {
        byte[] b = new byte[10000];
        while (true) {
          int r = in.read(b);
          if (r == -1) break;
          out.write(b, 0, r);
        }
      } finally {
        if (out != null) {
          out.close();
        }
      }
    } finally {
      if (in != null) {
        in.close();
      }
    }
  }
}
```
### 1.2.3. @Getter and @Setter


You can annotate any field with @Getter and/or @Setter, to let lombok generate the default getter/setter automatically.
A default getter simply returns the field, and is named getFoo if the field is called foo (or isFoo if the field's type is boolean). A default setter is named setFoo if the field is called foo, returns void, and takes 1 parameter of the same type as the field. It simply sets the field to this value.

The generated getter/setter method will be public unless you explicitly specify an AccessLevel, as shown in the example below. Legal access levels are PUBLIC, PROTECTED, PACKAGE, and PRIVATE.

You can also put a @Getter and/or @Setter annotation on a class. In that case, it's as if you annotate all the non-static fields in that class with the annotation.

You can always manually disable getter/setter generation for any field by using the special AccessLevel.NONE access level. This lets you override the behaviour of a @Getter, @Setter or @Data annotation on a class.

To put annotations on the generated method, you can use onMethod=@__({@AnnotationsHere}); to put annotations on the only parameter of a generated setter method, you can use onParam=@__({@AnnotationsHere}). Be careful though! This is an experimental feature. For more details see the documentation on the onX feature.

NEW in lombok v1.12.0: javadoc on the field will now be copied to generated getters and setters. Normally, all text is copied, and @return is moved to the getter, whilst @param lines are moved to the setter. Moved means: Deleted from the field's javadoc. It is also possible to define unique text for each getter/setter. To do that, you create a 'section' named GETTER and/or SETTER. A section is a line in your javadoc containing 2 or more dashes, then the text 'GETTER' or 'SETTER', followed by 2 or more dashes, and nothing else on the line. If you use sections, @return and @param stripping 

>With Lombok
```java
public class GetterSetterExample {
  /**
   * Age of the person. Water is wet.
   * 
   * @param age New value for this person's age. Sky is blue.
   * @return The current value of this person's age. Circles are round.
   */
  @Getter @Setter private int age = 10;
  
  /**
   * Name of the person.
   * -- SETTER --
   * Changes the name of this person.
   * 
   * @param name The new value.
   */
  @Setter(AccessLevel.PROTECTED) private String name;
  
  @Override public String toString() {
    return String.format("%s (age: %d)", name, age);
  }
}
```
Vanilla Java
```java

 public class GetterSetterExample {
  /**
   * Age of the person. Water is wet.
   */
  private int age = 10;

  /**
   * Name of the person.
   */
  private String name;
  
  @Override public String toString() {
    return String.format("%s (age: %d)", name, age);
  }
  
  /**
   * Age of the person. Water is wet.
   *
   * @return The current value of this person's age. Circles are round.
   */
  public int getAge() {
    return age;
  }
  
  /**
   * Age of the person. Water is wet.
   *
   * @param age New value for this person's age. Sky is blue.
   */
  public void setAge(int age) {
    this.age = age;
  }
  
  /**
   * Changes the name of this person.
   *
   * @param name The new value.
   */
  protected void setName(String name) {
    this.name = name;
  }
}
```
### 1.2.4. @ToString

Any class definition may be annotated with @ToString to let lombok generate an implementation of the toString() method. By default, it'll print your class name, along with each field, in order, separated by commas.

By setting the includeFieldNames parameter to true you can add some clarity (but also quite some length) to the output of the toString() method.

By default, all non-static fields will be printed. If you want to skip some fields, you can annotate these fields with @ToString.Exclude. Alternatively, you can specify exactly which fields you wish to be used by using @ToString(onlyExplicitlyIncluded = true), then marking each field you want to include with @ToString.Include.

By setting callSuper to true, you can include the output of the superclass implementation of toString to the output. Be aware that the default implementation of toString() in java.lang.Object is pretty much meaningless, so you probably don't want to do this unless you are extending another class.

You can also include the output of a method call in your toString. Only instance (non-static) methods that take no arguments can be included. To do so, mark the method with @ToString.Include.

You can change the name used to identify the member with @ToString.Include(name = "some other name"), and you can change the order in which the members are printed via @ToString.Include(rank = -1). Members without a rank are considered to have rank 0, members of a higher rank are printed first, and members of the same rank are printed in the same order they appear in the source file.

>With Lombok
```java
@ToString
public class ToStringExample {
  private static final int STATIC_VAR = 10;
  private String name;
  private Shape shape = new Square(5, 10);
  private String[] tags;
  @ToString.Exclude private int id;
  
  public String getName() {
    return this.name;
  }
  
  @ToString(callSuper=true, includeFieldNames=true)
  public static class Square extends Shape {
    private final int width, height;
    
    public Square(int width, int height) {
      this.width = width;
      this.height = height;
    }
  }
}
```
>Vanilla Java
```java
public class ToStringExample {
  private static final int STATIC_VAR = 10;
  private String name;
  private Shape shape = new Square(5, 10);
  private String[] tags;
  private int id;
  
  public String getName() {
    return this.getName();
  }
  
  @Override public String toString() {
    return "ToStringExample(" + this.getName() + ", " + this.shape + ", " + Arrays.deepToString(this.tags) + ")";
  }

  public static class Square extends Shape {
    private final int width, height;
    
    public Square(int width, int height) {
      this.width = width;
      this.height = height;
    }
    
    @Override public String toString() {
      return "Square(super=" + super.toString() + ", width=" + this.width + ", height=" + this.height + ")";
    }
  }
}
```
### 1.2.5. @EqualsAndHashCode

Any class definition may be annotated with @EqualsAndHashCode to let lombok generate implementations of the equals(Object other) and hashCode() methods. By default, it'll use all non-static, non-transient fields, but you can modify which fields are used (and even specify that the output of various methods is to be used) by marking type members with @EqualsAndHashCode.Include or @EqualsAndHashCode.Exclude. Alternatively, you can specify exactly which fields or methods you wish to be used by marking them with @EqualsAndHashCode.Include and using @EqualsAndHashCode(onlyExplicitlyIncluded = true).

If applying @EqualsAndHashCode to a class that extends another, this feature gets a bit trickier. Normally, auto-generating an equals and hashCode method for such classes is a bad idea, as the superclass also defines fields, which also need equals/hashCode code but this code will not be generated. By setting callSuper to true, you can include the equals and hashCode methods of your superclass in the generated methods. For hashCode, the result of super.hashCode() is included in the hash algorithm, and forequals, the generated method will return false if the super implementation thinks it is not equal to the passed in object. Be aware that not all equals implementations handle this situation properly. However, lombok-generated equals implementations do handle this situation properly, so you can safely call your superclass equals if it, too, has a lombok-generated equals method. If you have an explicit superclass you are forced to supply some value for callSuper to acknowledge that you've considered it; failure to do so results in a warning.

Setting callSuper to true when you don't extend anything (you extend java.lang.Object) is a compile-time error, because it would turn the generated equals() and hashCode() implementations into having the same behaviour as simply inheriting these methods from java.lang.Object: only the same object will be equal to each other and will have the same hashCode. Not setting callSuper to true when you extend another class generates a warning, because unless the superclass has no (equality-important) fields, lombok cannot generate an implementation for you that takes into account the fields declared by your superclasses. You'll need to write your own implementations, or rely on the callSuper chaining facility. You can also use the lombok.equalsAndHashCode.callSuper config key.

NEW in Lombok 0.10: Unless your class is final and extends java.lang.Object, lombok generates a canEqual method which means JPA proxies can still be equal to their base class, but subclasses that add new state don't break the equals contract. The complicated reasons for why such a method is necessary are explained in this paper: How to Write an Equality Method in Java. If all classes in a hierarchy are a mix of scala case classes and classes with lombok-generated equals methods, all equality will 'just work'. If you need to write your own equals methods, you should always override canEqual if you change equals and hashCode.

NEW in Lombok 1.14.0: To put annotations on the other parameter of the equals (and, if relevant, canEqual) method, you can use onParam=@__({@AnnotationsHere}). Be careful though! This is an experimental feature. For more details see the documentation on the onX feature.

>With Lombok
```java
@EqualsAndHashCode
public class EqualsAndHashCodeExample {
  private transient int transientVar = 10;
  private String name;
  private double score;
  @EqualsAndHashCode.Exclude private Shape shape = new Square(5, 10);
  private String[] tags;
  @EqualsAndHashCode.Exclude private int id;
  
  public String getName() {
    return this.name;
  }
  
  @EqualsAndHashCode(callSuper=true)
  public static class Square extends Shape {
    private final int width, height;
    
    public Square(int width, int height) {
      this.width = width;
      this.height = height;
    }
  }
}
```
>Vanilla Java
```java
public class EqualsAndHashCodeExample {
  private transient int transientVar = 10;
  private String name;
  private double score;
  private Shape shape = new Square(5, 10);
  private String[] tags;
  private int id;
  
  public String getName() {
    return this.name;
  }
  
  @Override public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof EqualsAndHashCodeExample)) return false;
    EqualsAndHashCodeExample other = (EqualsAndHashCodeExample) o;
    if (!other.canEqual((Object)this)) return false;
    if (this.getName() == null ? other.getName() != null : !this.getName().equals(other.getName())) return false;
    if (Double.compare(this.score, other.score) != 0) return false;
    if (!Arrays.deepEquals(this.tags, other.tags)) return false;
    return true;
  }
  
  @Override public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final long temp1 = Double.doubleToLongBits(this.score);
    result = (result*PRIME) + (this.name == null ? 43 : this.name.hashCode());
    result = (result*PRIME) + (int)(temp1 ^ (temp1 >>> 32));
    result = (result*PRIME) + Arrays.deepHashCode(this.tags);
    return result;
  }
  
  protected boolean canEqual(Object other) {
    return other instanceof EqualsAndHashCodeExample;
  }
  
  public static class Square extends Shape {
    private final int width, height;
    
    public Square(int width, int height) {
      this.width = width;
      this.height = height;
    }
    
    @Override public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof Square)) return false;
      Square other = (Square) o;
      if (!other.canEqual((Object)this)) return false;
      if (!super.equals(o)) return false;
      if (this.width != other.width) return false;
      if (this.height != other.height) return false;
      return true;
    }
    
    @Override public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = (result*PRIME) + super.hashCode();
      result = (result*PRIME) + this.width;
      result = (result*PRIME) + this.height;
      return result;
    }
    
    protected boolean canEqual(Object other) {
      return other instanceof Square;
    }
  }
}
```


### 1.2.6. @NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor

This set of 3 annotations generate a constructor that will accept 1 parameter for certain fields, and simply assigns this parameter to the field.

@NoArgsConstructor will generate a constructor with no parameters. If this is not possible (because of final fields), a compiler error will result instead, unless @NoArgsConstructor(force = true) is used, then all final fields are initialized with 0 / false / null. For fields with constraints, such as @NonNull fields, no check is generated,so be aware that these constraints will generally not be fulfilled until those fields are properly initialized later. Certain java constructs, such as hibernate and the Service Provider Interface require a no-args constructor. This annotation is useful primarily in combination with either @Data or one of the other constructor generating annotations.

@RequiredArgsConstructor generates a constructor with 1 parameter for each field that requires special handling. All non-initialized final fields get a parameter, as well as any fields that are marked as @NonNull that aren't initialized where they are declared. For those fields marked with @NonNull, an explicit null check is also generated. The constructor will throw a NullPointerException if any of the parameters intended for the fields marked with @NonNull contain null. The order of the parameters match the order in which the fields appear in your class.

@AllArgsConstructor generates a constructor with 1 parameter for each field in your class. Fields marked with @NonNull result in null checks on those parameters.

Each of these annotations allows an alternate form, where the generated constructor is always private, and an additional static factory method that wraps around the private constructor is generated. This mode is enabled by supplying the staticName value for the annotation, like so: @RequiredArgsConstructor(staticName="of"). Such a static factory method will infer generics, unlike a normal constructor. This means your API users get write MapEntry.of("foo", 5) instead of the much longer new MapEntry<String, Integer>("foo", 5).

To put annotations on the generated constructor, you can use onConstructor=@__({@AnnotationsHere}), but be careful; this is an experimental feature. For more details see the documentation on the onX feature.

Static fields are skipped by these annotations. Also, a @java.beans.ConstructorProperties annotation is added for all constructors with at least 1 argument, which allows bean editor tools to call the generated constructors. @ConstructorProperties is new in Java 1.6, which means that if your code is intended for compilation on Java 1.5, a compiler error will occur. Running on a JVM 1.5 should be no problem (the annotation will be ignored). To suppress the generation of the @ConstructorProperties annotation, add a parameter to your annotation: @AllArgsConstructor(suppressConstructorProperties=true). However, as java 1.5, which has already been end-of-lifed, fades into obscurity, this parameter will eventually be removed. It has also been marked deprecated for this reason.

Unlike most other lombok annotations, the existence of an explicit constructor does not stop these annotations from generating their own constructor. This means you can write your own specialized constructor, and let lombok generate the boilerplate ones as well. If a conflict arises (one of your constructors ends up with the same signature as one that lombok generates), a compiler error will occur.

>With Lombok
```java
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ConstructorExample<T> {
  private int x, y;
  @NonNull private T description;
  
  @NoArgsConstructor
  public static class NoArgsExample {
    @NonNull private String field;
  }
}
```
>Vanilla Java
```java

 public class ConstructorExample<T> {
  private int x, y;
  @NonNull private T description;
  
  private ConstructorExample(T description) {
    if (description == null) throw new NullPointerException("description");
    this.description = description;
  }
  
  public static <T> ConstructorExample<T> of(T description) {
    return new ConstructorExample<T>(description);
  }
  
  @java.beans.ConstructorProperties({"x", "y", "description"})
  protected ConstructorExample(int x, int y, T description) {
    if (description == null) throw new NullPointerException("description");
    this.x = x;
    this.y = y;
    this.description = description;
  }
  
  public static class NoArgsExample {
    @NonNull private String field;
    
    public NoArgsExample() {
    }
  }
}
```


### 1.2.7. @Data 

@Data is a convenient shortcut annotation that bundles the features of @ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor together: In other words, @Data generates all the boilerplate that is normally associated with simple POJOs (Plain Old Java Objects) and beans: getters for all fields, setters for all non-final fields, and appropriate toString, equals and hashCode implementations that involve the fields of the class, and a constructor that initializes all final fields, as well as all non-final fields with no initializer that have been marked with @NonNull, in order to ensure the field is never null.

@Data is like having implicit @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor annotations on the class (except that no constructor will be generated if any explicitly written constructors already exist). However, the parameters of these annotations (such as callSuper, includeFieldNames and exclude) cannot be set with @Data. If you need to set non-default values for any of these parameters, just add those annotations explicitly; @Data is smart enough to defer to those annotations.

All generated getters and setters will be public. To override the access level, annotate the field or class with an explicit @Setter and/or @Getter annotation. You can also use this annotation (by combining it with AccessLevel.NONE) to suppress generating a getter and/or setter altogether.

All fields marked as transient will not be considered for hashCode and equals. All static fields will be skipped entirely (not considered for any of the generated methods, and no setter/getter will be made for them).

If the class already contains a method with the same name and parameter count as any method that would normally be generated, that method is not generated, and no warning or error is emitted. For example, if you already have a method with signature equals(AnyType param), no equals method will be generated, even though technically it might be an entirely different method due to having different parameter types. The same rule applies to the constructor (any explicit constructor will prevent @Data from generating one), as well as toString, equals, and all getters and setters. You can mark any constructor or method with @lombok.experimental.Tolerate to hide them from lombok.

@Data can handle generics parameters for fields just fine. In order to reduce the boilerplate when constructing objects for classes with generics, you can use the staticConstructor parameter to generate a private constructor, as well as a static method that returns a new instance. This way, javac will infer the variable name. Thus, by declaring like so: @Data(staticConstructor="of") class Foo<T> { private T x;} you can create new instances of Foo by writing: Foo.of(5); instead of having to write: new Foo<Integer>(5);.

>With Lombok
```java
@Data public class DataExample {
  private final String name;
  @Setter(AccessLevel.PACKAGE) private int age;
  private double score;
  private String[] tags;
  
  @ToString(includeFieldNames=true)
  @Data(staticConstructor="of")
  public static class Exercise<T> {
    private final String name;
    private final T value;
  }
}
```
>Vanilla Java
```java
public class DataExample {
  private final String name;
  private int age;
  private double score;
  private String[] tags;
  
  public DataExample(String name) {
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }
  
  void setAge(int age) {
    this.age = age;
  }
  
  public int getAge() {
    return this.age;
  }
  
  public void setScore(double score) {
    this.score = score;
  }
  
  public double getScore() {
    return this.score;
  }
  
  public String[] getTags() {
    return this.tags;
  }
  
  public void setTags(String[] tags) {
    this.tags = tags;
  }
  
  @Override public String toString() {
    return "DataExample(" + this.getName() + ", " + this.getAge() + ", " + this.getScore() + ", " + Arrays.deepToString(this.getTags()) + ")";
  }
  
  protected boolean canEqual(Object other) {
    return other instanceof DataExample;
  }
  
  @Override public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof DataExample)) return false;
    DataExample other = (DataExample) o;
    if (!other.canEqual((Object)this)) return false;
    if (this.getName() == null ? other.getName() != null : !this.getName().equals(other.getName())) return false;
    if (this.getAge() != other.getAge()) return false;
    if (Double.compare(this.getScore(), other.getScore()) != 0) return false;
    if (!Arrays.deepEquals(this.getTags(), other.getTags())) return false;
    return true;
  }
  
  @Override public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final long temp1 = Double.doubleToLongBits(this.getScore());
    result = (result*PRIME) + (this.getName() == null ? 43 : this.getName().hashCode());
    result = (result*PRIME) + this.getAge();
    result = (result*PRIME) + (int)(temp1 ^ (temp1 >>> 32));
    result = (result*PRIME) + Arrays.deepHashCode(this.getTags());
    return result;
  }
  
  public static class Exercise<T> {
    private final String name;
    private final T value;
    
    private Exercise(String name, T value) {
      this.name = name;
      this.value = value;
    }
    
    public static <T> Exercise<T> of(String name, T value) {
      return new Exercise<T>(name, value);
    }
    
    public String getName() {
      return this.name;
    }
    
    public T getValue() {
      return this.value;
    }
    
    @Override public String toString() {
      return "Exercise(name=" + this.getName() + ", value=" + this.getValue() + ")";
    }
    
    protected boolean canEqual(Object other) {
      return other instanceof Exercise;
    }
    
    @Override public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof Exercise)) return false;
      Exercise<?> other = (Exercise<?>) o;
      if (!other.canEqual((Object)this)) return false;
      if (this.getName() == null ? other.getValue() != null : !this.getName().equals(other.getName())) return false;
      if (this.getValue() == null ? other.getValue() != null : !this.getValue().equals(other.getValue())) return false;
      return true;
    }
    
    @Override public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = (result*PRIME) + (this.getName() == null ? 43 : this.getName().hashCode());
      result = (result*PRIME) + (this.getValue() == null ? 43 : this.getValue().hashCode());
      return result;
    }
  }
}
```


### 1.2.8. @Value

@Value is the immutable variant of @Data; all fields are made private and final by default, and setters are not generated. The class itself is also made final by default, because immutability is not something that can be forced onto a subclass. Like @Data, useful toString(), equals() and hashCode() methods are also generated, each field gets a getter method, and a constructor that covers every argument (except final fields that are initialized in the field declaration) is also generated.

In practice, @Value is shorthand for: final @ToString @EqualsAndHashCode @AllArgsConstructor @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE) @Getter, except that explicitly including an implementation of any of the relevant methods simply means that part won't be generated and no warning will be emitted. For example, if you write your own toString, no error occurs, and lombok will not generate a toString. Also, any explicit constructor, no matter the arguments list, implies lombok will not generate a constructor. If you do want lombok to generate the all-args constructor, add @AllArgsConstructor to the class. You can mark any constructor or method with @lombok.experimental.Tolerate to hide them from lombok.

It is possible to override the final-by-default and private-by-default behavior using either an explicit access level on a field, or by using the @NonFinal or @PackagePrivate annotations.
It is possible to override any default behavior for any of the 'parts' that make up @Value by explicitly using that 


>With Lombok
```java
@Value public class ValueExample {
  String name;
  @Wither(AccessLevel.PACKAGE) @NonFinal int age;
  double score;
  protected String[] tags;
  
  @ToString(includeFieldNames=true)
  @Value(staticConstructor="of")
  public static class Exercise<T> {
    String name;
    T value;
  }
}
```
>Vanilla Java
```java
public final class ValueExample {
  private final String name;
  private int age;
  private final double score;
  protected final String[] tags;
  
  @java.beans.ConstructorProperties({"name", "age", "score", "tags"})
  public ValueExample(String name, int age, double score, String[] tags) {
    this.name = name;
    this.age = age;
    this.score = score;
    this.tags = tags;
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getAge() {
    return this.age;
  }
  
  public double getScore() {
    return this.score;
  }
  
  public String[] getTags() {
    return this.tags;
  }
  
  @java.lang.Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof ValueExample)) return false;
    final ValueExample other = (ValueExample)o;
    final Object this$name = this.getName();
    final Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    if (this.getAge() != other.getAge()) return false;
    if (Double.compare(this.getScore(), other.getScore()) != 0) return false;
    if (!Arrays.deepEquals(this.getTags(), other.getTags())) return false;
    return true;
  }
  
  @java.lang.Override
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    result = result * PRIME + this.getAge();
    final long $score = Double.doubleToLongBits(this.getScore());
    result = result * PRIME + (int)($score >>> 32 ^ $score);
    result = result * PRIME + Arrays.deepHashCode(this.getTags());
    return result;
  }
  
  @java.lang.Override
  public String toString() {
    return "ValueExample(name=" + getName() + ", age=" + getAge() + ", score=" + getScore() + ", tags=" + Arrays.deepToString(getTags()) + ")";
  }
  
  ValueExample withAge(int age) {
    return this.age == age ? this : new ValueExample(name, age, score, tags);
  }
  
  public static final class Exercise<T> {
    private final String name;
    private final T value;
    
    private Exercise(String name, T value) {
      this.name = name;
      this.value = value;
    }
    
    public static <T> Exercise<T> of(String name, T value) {
      return new Exercise<T>(name, value);
    }
    
    public String getName() {
      return this.name;
    }
    
    public T getValue() {
      return this.value;
    }
    
    @java.lang.Override
    public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof ValueExample.Exercise)) return false;
      final Exercise<?> other = (Exercise<?>)o;
      final Object this$name = this.getName();
      final Object other$name = other.getName();
      if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
      final Object this$value = this.getValue();
      final Object other$value = other.getValue();
      if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final Object $name = this.getName();
      result = result * PRIME + ($name == null ? 43 : $name.hashCode());
      final Object $value = this.getValue();
      result = result * PRIME + ($value == null ? 43 : $value.hashCode());
      return result;
    }
    
    @java.lang.Override
    public String toString() {
      return "ValueExample.Exercise(name=" + getName() + ", value=" + getValue() + ")";
    }
  }
}
```


### 1.2.9. @Builder
```

```
>With Lombok
```java
@Builder
public class BuilderExample {
  @Builder.Default private long created = System.currentTimeMillis();
  private String name;
  private int age;
  @Singular private Set<String> occupations;
}
```
>Vanilla Java
```java
public class BuilderExample {
  private long created;
  private String name;
  private int age;
  private Set<String> occupations;
  
  BuilderExample(String name, int age, Set<String> occupations) {
    this.name = name;
    this.age = age;
    this.occupations = occupations;
  }
  
  private static long $default$created() {
    return System.currentTimeMillis();
  }
  
  public static BuilderExampleBuilder builder() {
    return new BuilderExampleBuilder();
  }
  
  public static class BuilderExampleBuilder {
    private long created;
    private boolean created$set;
    private String name;
    private int age;
    private java.util.ArrayList<String> occupations;
    
    BuilderExampleBuilder() {
    }
    
    public BuilderExampleBuilder created(long created) {
      this.created = created;
      this.created$set = true;
      return this;
    }
    
    public BuilderExampleBuilder name(String name) {
      this.name = name;
      return this;
    }
    
    public BuilderExampleBuilder age(int age) {
      this.age = age;
      return this;
    }
    
    public BuilderExampleBuilder occupation(String occupation) {
      if (this.occupations == null) {
        this.occupations = new java.util.ArrayList<String>();
      }
      
      this.occupations.add(occupation);
      return this;
    }
    
    public BuilderExampleBuilder occupations(Collection<? extends String> occupations) {
      if (this.occupations == null) {
        this.occupations = new java.util.ArrayList<String>();
      }

      this.occupations.addAll(occupations);
      return this;
    }
    
    public BuilderExampleBuilder clearOccupations() {
      if (this.occupations != null) {
        this.occupations.clear();
      }
      
      return this;
    }

    public BuilderExample build() {
      // complicated switch statement to produce a compact properly sized immutable set omitted.
      Set<String> occupations = ...;
      return new BuilderExample(created$set ? created : BuilderExample.$default$created(), name, age, occupations);
    }
    
    @java.lang.Override
    public String toString() {
      return "BuilderExample.BuilderExampleBuilder(created = " + this.created + ", name = " + this.name + ", age = " + this.age + ", occupations = " + this.occupations + ")";
    }
  }
}
```

### 1.2.10. @SneakyThrows

@SneakyThrows can be used to sneakily throw checked exceptions without actually declaring this in your method's throws clause. This somewhat contentious ability should be used carefully, of course. The code generated by lombok will not ignore, wrap, replace, or otherwise modify the thrown checked exception; it simply fakes out the compiler. On the JVM (class file) level, all exceptions, checked or not, can be thrown regardless of the throws clause of your methods, which is why this works.

Common use cases for when you want to opt out of the checked exception mechanism center around 2 situations:
- A needlessly strict interface, such as Runnable - whatever exception propagates out of your run() method, checked or not, it will be passed to the Thread's unhandled exception handler. Catching a checked exception and wrapping it in some sort of RuntimeException is only obscuring the real cause of the issue.
- An 'impossible' exception. For example, new String(someByteArray, "UTF-8"); declares that it can throw an UnsupportedEncodingException but according to the JVM specification, UTF-8 must always be available. An UnsupportedEncodingException here is about as likely as a ClassNotFoundError when you use a String object, and you don't catch those either!
Be aware that it is impossible to catch sneakily thrown checked types directly, as javac will not let you write a catch block for an exception type that no method call in the try body declares as thrown. This problem is not relevant in either of the use cases listed above, so let this serve as a warning that you should not use the @SneakyThrows mechanism without some deliberation!

You can pass any number of exceptions to the @SneakyThrows annotation. If you pass no exceptions, you may throw any exception sneakily.

>With Lombok
```java
public class SneakyThrowsExample implements Runnable {
  @SneakyThrows(UnsupportedEncodingException.class)
  public String utf8ToString(byte[] bytes) {
    return new String(bytes, "UTF-8");
  }
  
  @SneakyThrows
  public void run() {
    throw new Throwable();
  }
}
```
>Vanilla Java
```java
public class SneakyThrowsExample implements Runnable {
  public String utf8ToString(byte[] bytes) {
    try {
      return new String(bytes, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw Lombok.sneakyThrow(e);
    }
  }
  
  public void run() {
    try {
      throw new Throwable();
    } catch (Throwable t) {
      throw Lombok.sneakyThrow(t);
    }
  }
}
```

### 1.2.11. @Synchronized

@Synchronized is a safer variant of the synchronized method modifier. Like synchronized, the annotation can be used on static and instance methods only. It operates similarly to the synchronized keyword, but it locks on different objects. The keyword locks on this, but the annotation locks on a field named $lock, which is private.
If the field does not exist, it is created for you. If you annotate a static method, the annotation locks on a static field named $LOCK instead.

If you want, you can create these locks yourself. The $lock and $LOCK fields will of course not be generated if you already created them yourself. You can also choose to lock on another field, by specifying it as parameter to the @Synchronized annotation. In this usage variant, the fields will not be created automatically, and you must explicitly create them yourself, or an error will be emitted.

Locking on this or your own class object can have unfortunate side-effects, as other code not under your control can lock on these objects as well, which can cause race conditions and other nasty threading-related bugs.

>With Lombok
```java
public class SynchronizedExample {
  private final Object readLock = new Object();
  
  @Synchronized
  public static void hello() {
    System.out.println("world");
  }
  
  @Synchronized
  public int answerToLife() {
    return 42;
  }
  
  @Synchronized("readLock")
  public void foo() {
    System.out.println("bar");
  }
}
```
>Vanilla Java
```java

 public class SynchronizedExample {
  private static final Object $LOCK = new Object[0];
  private final Object $lock = new Object[0];
  private final Object readLock = new Object();
  
  public static void hello() {
    synchronized($LOCK) {
      System.out.println("world");
    }
  }
  
  public int answerToLife() {
    synchronized($lock) {
      return 42;
    }
  }
  
  public void foo() {
    synchronized(readLock) {
      System.out.println("bar");
    }
  }
}
```
### 1.2.12. @Getter(lazy=true)
You can let lombok generate a getter which will calculate a value once, the first time this getter is called, and cache it from then on. This can be useful if calculating the value takes a lot of CPU, or the value takes a lot of memory. To use this feature, create a private final variable, initialize it with the expression that's expensive to run, and annotate your field with @Getter(lazy=true). The field will be hidden from the rest of your code, and the expression will be evaluated no more than once, when the getter is first called. There are no magic marker values (i.e. even if the result of your expensive calculation is null, the result is cached) and your expensive calculation need not be thread-safe, as lombok takes care of locking.

>With Lombok
```java
public class GetterLazyExample {
  @Getter(lazy=true) private final double[] cached = expensive();
  
  private double[] expensive() {
    double[] result = new double[1000000];
    for (int i = 0; i < result.length; i++) {
      result[i] = Math.asin(i);
    }
    return result;
  }
}
```
>Vanilla Java
```java
public class GetterLazyExample {
  private final java.util.concurrent.AtomicReference<java.lang.Object> cached = new java.util.concurrent.AtomicReference<java.lang.Object>();
  
  public double[] getCached() {
    java.lang.Object value = this.cached.get();
    if (value == null) {
      synchronized(this.cached) {
        value = this.cached.get();
        if (value == null) {
          final double[] actualValue = expensive();
          value = actualValue == null ? this.cached : actualValue;
          this.cached.set(value);
        }
      }
    }
    return (double[])(value == this.cached ? null : value);
  }
  
  private double[] expensive() {
    double[] result = new double[1000000];
    for (int i = 0; i < result.length; i++) {
      result[i] = Math.asin(i);
    }
    return result;
  }
}
```
### 1.2.13. @Log (and friends)

You put the variant of @Log on your class (whichever one applies to the logging system you use); you then have a static final log field, initialized to the name of your class, which you can then use to write log statements.

There are several choices available:
@CommonsLog
Creates private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(LogExample.class);
@Flogger
Creates private static final com.google.common.flogger.FluentLogger log = com.google.common.flogger.FluentLogger.forEnclosingClass();
@JBossLog
Creates private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(LogExample.class);
@Log
Creates private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(LogExample.class.getName());
@Log4j
Creates private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LogExample.class);
@Log4j2
Creates private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(LogExample.class);
@Slf4j
Creates private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);
@XSlf4j
Creates private static final org.slf4j.ext.XLogger log = org.slf4j.ext.XLoggerFactory.getXLogger(LogExample.class);
By default, the topic (or name) of the logger will be the class name of the class annotated with the @Log annotation. This can be customised by specifying the topic parameter. For example: @XSlf4j(topic="reporting").

>With Lombok
```java
@Log
public class LogExample {
  
  public static void main(String... args) {
    log.severe("Something's wrong here");
  }
}

@Slf4j
public class LogExampleOther {
  
  public static void main(String... args) {
    log.error("Something else is wrong here");
  }
}

@CommonsLog(topic="CounterLog")
public class LogExampleCategory {

  public static void main(String... args) {
    log.error("Calling the 'CounterLog' with a message");
  }
}
```
>Vanilla Java
```java
public class LogExample {
  private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(LogExample.class.getName());
  
  public static void main(String... args) {
    log.severe("Something's wrong here");
  }
}

public class LogExampleOther {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExampleOther.class);
  
  public static void main(String... args) {
    log.error("Something else is wrong here");
  }
}

public class LogExampleCategory {
  private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog("CounterLog");

  public static void main(String... args) {
    log.error("Calling the 'CounterLog' with a message");
  }
}
```