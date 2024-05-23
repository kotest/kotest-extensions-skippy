package io.kotest.extensions.skippy

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension

object KotestConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> {
        return listOf(SkippyExtension)
    }
}