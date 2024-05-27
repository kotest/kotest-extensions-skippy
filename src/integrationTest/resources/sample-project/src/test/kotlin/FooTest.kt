import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class FooTest: FunSpec({
    test("Test the foo") {
        Foo.foo() shouldBe "foo"
    }
})
