package io.kotest.extensions.skippy

import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import io.skippy.core.SkippyTestApi

/**
 * An extension that will record the execution of the Spec to an exec file, which Skippy can analyze
 * to determine test impact.
 */
class SkippyRecordingExtension(
   private val skippy: SkippyTestApi = SkippyTestApi.INSTANCE
) : AfterSpecListener, BeforeSpecListener {

   override suspend fun beforeSpec(spec: Spec) {
      skippy.prepareExecFileGeneration(spec::class.java)
   }

   override suspend fun afterSpec(spec: Spec) {
      skippy.writeExecFile(spec::class.java)
   }
}
