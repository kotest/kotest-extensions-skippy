package io.kotest.extensions.skippy

import io.kotest.core.spec.style.FunSpec
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import io.skippy.core.SkippyTestApi

class SkippyRecordingExtensionTests : FunSpec({
   val mockSkippy = mockk<SkippyTestApi>(relaxed = true)
   val predictionExtension = SkippyRecordingExtension(mockSkippy)

   afterEach { confirmVerified(mockSkippy) }

   test("BeforeSpec, skippy should be prepared to record execution data") {
      predictionExtension.beforeSpec(DummySpec())

      verify {
         mockSkippy.prepareExecFileGeneration(DummySpec::class.java)
      }
   }

   test("When Skippy says the test should run, it runs") {
      predictionExtension.afterSpec(DummySpec())

      verify {
         mockSkippy.writeExecFile(DummySpec::class.java)
      }
   }
}) {

   private class DummySpec : FunSpec()
}

