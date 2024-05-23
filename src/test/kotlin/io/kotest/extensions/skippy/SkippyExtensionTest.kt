package io.kotest.extensions.skippy

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SkippyExtensionTest : FunSpec({
    test("DO the thing...") {
        Foo.foo() shouldBe "foo"
    }
})