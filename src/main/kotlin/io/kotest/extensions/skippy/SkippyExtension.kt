package io.kotest.extensions.skippy

import io.kotest.core.extensions.SpecExtension
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener

private val recordingExtension = SkippyRecordingExtension()

/**
 * Applies both [SkippyPredictionExtension] and [SkippyRecordingExtension].
 *
 * Note that you need to also use the Skippy Gradle plugin and JaCoCo for everything to work.
 */
object SkippyExtension :
   SpecExtension by SkippyPredictionExtension(),
   BeforeSpecListener by recordingExtension,
   AfterSpecListener by recordingExtension {

   // Required since we inherit multiple names from the composed classes
   @Deprecated("Listener names are no longer used. Deprecated since 5.0")
   override val name: String = "SkippyExtension"
}
