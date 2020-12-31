# Install norswap.utils

The project's artifacts are hosted on [Bintray].

It's also possible to use [JitPack] as an alternative (detailed instructions not provided).

[Bintray]: https://bintray.com/norswap/maven/utils
[JitPack]: https://jitpack.io/#norswap/utils

## Using Gradle

With the Kotlin DSL (`build.gradle.kts`):

```kotlin
repositories {
    // ...
    maven {
        url =  uri("https://dl.bintray.com/norswap/maven")
    }
}

dependencies {
    // ...
    implementation("com.norswap:utils:1.0.4")
}
```

With the Groovy DSL (`build.gradle`):

```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/norswap/maven"
    }
}

dependencies {
    // ...
    implementation 'com.norswap:utils:1.0.4'
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
      <id>norswap-maven</id>
      <url>https://dl.bintray.com/norswap/maven</url>
    </repository>
  </repositories>
  <dependencies>
    ...
    <dependency>
      <groupId>com.norswap</groupId>
      <artifactId>utils</artifactId>
      <version>1.0.4</version>
    </dependency>  
  </dependencies>
</project>
```