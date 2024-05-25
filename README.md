Kotest wrapper for using [Skippy](https://www.skippy.io) for test execution prediction.

Skippy uses JaCoCo coverage from past test executions to determine the tests that cover any modified code, and runs only the relevant tests.

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

Currently the resolution of Skippy is entire files/classes. So every Spec is instrumented as one unit, and if any class it touches during testing changes, all tests will re-run.
