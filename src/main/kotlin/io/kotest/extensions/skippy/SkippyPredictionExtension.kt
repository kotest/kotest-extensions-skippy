package io.kotest.extensions.skippy

import io.kotest.core.extensions.SpecExtension
import io.kotest.core.spec.Spec
import io.skippy.core.SkippyTestApi

/**
 * An extension that will skip the Spec if Skippy determines that it does not need to be executed.
 */
class SkippyPredictionExtension(
   private val skippy: SkippyTestApi = SkippyTestApi.INSTANCE
) : SpecExtension{

   override suspend fun intercept(spec: Spec, execute: suspend (Spec) -> Unit) {
      if (spec.shouldExecute()) {
         execute(spec)
      }
   }


   private fun Spec.shouldExecute() = skippy.testNeedsToBeExecuted(this@shouldExecute::class.java)
}
