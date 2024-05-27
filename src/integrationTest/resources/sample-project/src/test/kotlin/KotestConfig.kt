import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.skippy.SkippyExtension

object KotestConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> {
        return listOf(SkippyExtension)
    }
}
