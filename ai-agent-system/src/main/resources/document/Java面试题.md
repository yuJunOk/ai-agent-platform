# <font style="color:rgba(0, 0, 0, 0.88);background-color:rgb(250, 250, 250);">接口和抽象类有什么区别？</font>
抽象类和接口类都是面向对象编程中两种常见的抽象概念，有以下几种区别：

1. 定义的关键字不同，abstract 和 interface。
2. 继承或实现的关键字不同，extend 和 implement。
3. 子类扩展的数量不同，抽象类只能被单继承，接口可以被多实现。
4. 抽象类中的属性访问控制符无限制，接口中属性的访问控制符只能是public（接口中的属性默认是public static final 修饰的）。

# <font style="color:rgba(0, 0, 0, 0.88);">JDK 动态代理和 CGLIB 动态代理有什么区别？</font>
Java中代理分为静态代理和动态代理，静态代理需要手写代理类，比较麻烦。动态代理会自动生成代理类。动态代理分为JDK和CGLIB。

JDK动态代理通过反射调用对象，调用类必须实现一个或多个接口

CGLIB通过建立索引来调用对象，调用类必须可以被继承的

Spring就会根据这两个特性去选择使用哪个代理实现。

# <font style="color:rgba(0, 0, 0, 0.88);">你使用过 Java 的反射机制吗？如何应用反射？</font>
反射就是Java可以给我们在运行时获取类的信息，调用类的方法以及属性。反射（Reflection）允许程序在运行时访问和修改类的结构，包括类、接口、字段、方法等。通过反射，我们可以在运行时动态地获取类的信息，比如类的名称、接口、超类、字段和方法。反射在很多场景下非常有用，比如动态代理、依赖注入、序列化、IDE 插件开发等。

反射的基本应用包括：

1. 获取类的信息：
+ 使用 Class 类的方法，如 getName()、getFields()、getMethods()。
+ 使用 getClass() 方法获取一个对象的类信息。
2. 创建对象：
+ 使用 newInstance() 方法或 getDeclaredConstructor() 创建对象。
3. 访问和修改字段：
+ 使用 getField() 或 getDeclaredField() 获取字段。
+ 使用 set() 方法设置字段的值，使用 get() 方法获取字段的值。
4. 调用方法：
+ 使用 getMethod() 或 getDeclaredMethod() 获取方法。
+ 使用 invoke() 方法调用该方法。
5. 处理数组和枚举：
+ 使用反射来创建和操作数组。
+ 使用反射来访问枚举的常量。
6. 处理异常：
+ 反射操作可能会抛出各种异常，如 NoSuchMethodException 和 InvocationTargetException。
+ 需要使用 try-catch 块来处理这些异常。



