# Auto Gradle Plugin

Annotation processor for creating `META-INF/gradle-plugins/{plugin-id}.properties` files.

### Disclaimer

Use if you're lazy as I am.

### Dependencies

```groovy
dependencies {
    compilyOnly "dev.afanasev:auto-gradle-plugin-annotation:<version>"
    apt "dev.afanasev:auto-gradle-plugin-processor:<version>"
}
```

### Usage

```java
// somewhere in buildSrc

@AutoGradlePlugin("my-plugin-id")
class MyGradlePlugin extends Plugin<Project> {
    // ...
}
```

### Applying

```groovy
// build.gradle

plugins {
    id "my-plugin-id"
}
```