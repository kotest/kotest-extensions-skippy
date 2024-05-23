Kotest wrapper for using [Skippy](https://www.skippy.io) for test execution prediction.

Skippy analyzes JaCoCo coverage during test execution to determine what affects test which classes.

## Usage

In your `build.gradle.kts`:

```kotlin
plugins {
   id("io.skippy") version "0.0.19"
}
```

Create (or add) to a Kotest configuration file:
```kotlin
object KotestConfig : AbstractProjectConfig() {
   override val extensions = listOf(SkippyExtension)
}
```

Now, when you run tests using Gradle, Skippy will automatically skip tests that have not been affected by any change 
since they were last executed successfully.