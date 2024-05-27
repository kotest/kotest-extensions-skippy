package io.kotest.extensions.skippy

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain
import org.gradle.testkit.runner.GradleRunner
import java.io.File
import java.io.StringWriter

class SkippyExtensionTest : FunSpec({
   // Can't seem to get Gradle to load the extension from the included build.
   // Keeping this around to tinker with, for now. Feel free to delete by 2025
   test("!Tests should execute") {
      val stdOut = StringWriter()

      GradleRunner.create()
         .withProjectDir(File(SkippyExtensionTest::class.java.getResource("/sample-project").path))
         .withArguments("check", "--no-configuration-cache", "--no-build-cache")
         .forwardStdOutput(stdOut)
         .build()

      stdOut.toString() shouldContain "SUCCESS: Executed 0 tests"
   }
})
