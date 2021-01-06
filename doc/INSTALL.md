# Install norswap.utils

The project's artifacts are hosted on [Bintray] and available from JCenter.

It's also possible to use [JitPack] as an alternative (detailed instructions not provided).

[Bintray]: https://bintray.com/norswap/maven/utils
[JitPack]: https://jitpack.io/#norswap/utils

## Using Gradle

With the Kotlin DSL (`build.gradle.kts`):

```kotlin
repositories {
    // ...
    jcenter()
}

dependencies {
    // ...
    implementation("com.norswap:utils:2.0.0")
}
```

With the Groovy DSL (`build.gradle`):

```groovy
repositories {
    // ...
    jcenter()
}

dependencies {
    // ...
    implementation 'com.norswap:utils:2.0.0'
}
```

## Using Maven

In `pom.xml`:

```xml
<project>
  ...
  <repositories>
    ...__
    <repository>
      <id>jcenter</id>
      <url>https://jcenter.bintray.com</url>
    </repository>
  </repositories>
  <dependencies>
    ...
    <dependency>
      <groupId>com.norswap</groupId>
      <artifactId>utils</artifactId>
      <version>2.0.0</version>
    </dependency>  
  </dependencies>
</project>
```