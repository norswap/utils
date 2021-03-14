# Install norswap.utils

The project's artifacts are hosted on [Maven Central], and on a public [Artifactory repository].

The only difference is that new releases will land on the artifactory repository a few hours
earlier.

[Maven Central]: https://search.maven.org/artifact/com.norswap/utils/
[Artifactory repository]: https://norswap.jfrog.io/artifactory/maven/

## Using Gradle

With the Kotlin DSL (`build.gradle.kts`):

```kotlin
repositories {
    // ...
    mavenCentral()
    // and/or:
    maven {
        url = uri("https://norswap.jfrog.io/artifactory/maven")
    }
}

dependencies {
    // ...
    implementation("com.norswap:utils:2.1.9")
}
```

With the Groovy DSL (`build.gradle`):

```groovy
repositories {
    // ...
    mavenCentral()
    // and/or:
    maven {
        url 'https://norswap.jfrog.io/artifactory/maven'
    }
}

dependencies {
    // ...
    implementation 'com.norswap:utils:2.1.9'
}
```

## Using Maven

In `pom.xml`:

```xml
<project>
  ...
  <repositories>
    ...
    <!-- no repository declaration needed for using Maven Central -->
    <repository>
        <id>artifactory-norswap</id>
        <url>https://norswap.jfrog.io/artifactory/maven</url>
    </repository>
  </repositories>
  <dependencies>
    ...
    <dependency>
      <groupId>com.norswap</groupId>
      <artifactId>utils</artifactId>
      <version>2.1.9</version>
    </dependency>  
  </dependencies>
</project>
```