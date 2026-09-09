# practice1-Maven

Maven、JUnit 单元测试和 Java 企业开发规范的学习代码。

## 项目内容

- 使用 `pom.xml` 配置项目坐标、Java 编译版本和文件编码；
- 使用 Maven 管理 JUnit Jupiter 测试依赖；
- 使用 `@Test` 编写普通单元测试；
- 使用 `@ParameterizedTest` 和参数源编写参数化测试；
- 使用 `@BeforeAll`、`@BeforeEach`、`@AfterEach`、`@AfterAll` 理解测试生命周期；
- 使用断言验证方法的预期结果。

## 目录结构

```text
practice1-Maven
├── pom.xml
└── src
    ├── main/java/org/example/Main.java
    └── test/java/org/example/MainTest.java
```

## 说明

当前代码保留了学习过程中的原始写法。参数化测试中的 `@ValueSource` 只提供一个参数，而测试方法声明了两个参数，后续需要调整参数源或方法签名，并补充断言。
