package io.kotest.extensions.skippy

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.every
import io.mockk.mockk
import io.skippy.core.SkippyTestApi

class SkippyPredictionExtensionTests : FunSpec({
   val mockSkippy = mockk<SkippyTestApi>()
   val predictionExtension = SkippyPredictionExtension(mockSkippy)

   test("When Skippy says the test can be skipped, it is skipped") {
      every { mockSkippy.testNeedsToBeExecuted(DummySpec::class.java) } returns false

      shouldNotThrowAny {
         predictionExtension.intercept(DummySpec()) {
            error("Should not run, as Skippy says it should be skipped")
         }
      }
   }

   test("When Skippy says the test should run, it runs") {
      every { mockSkippy.testNeedsToBeExecuted(DummySpec::class.java) } returns true

      var wasRun = false
      predictionExtension.intercept(DummySpec()) { wasRun = true }

      wasRun.shouldBeTrue()
   }
}) {

   private class DummySpec : FunSpec()
}
