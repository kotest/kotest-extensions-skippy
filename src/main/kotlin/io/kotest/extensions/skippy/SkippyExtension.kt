package io.kotest.extensions.skippy

import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.skippy.core.SkippyTestApi

object SkippyExtension : BeforeSpecListener, AfterSpecListener, TestCaseExtension {
    private val skippy = SkippyTestApi.INSTANCE

    override suspend fun beforeSpec(spec: Spec) {
        if (spec.shouldExecute()) {
            println("Preparing exec file generation for ${spec::class.simpleName}")
            skippy.prepareExecFileGeneration(spec::class.java)
        }
    }

    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult =
        if (testCase.spec.shouldExecute()) {
            execute(testCase)
        } else {
            TestResult.Ignored
        }

    override suspend fun afterSpec(spec: Spec) {
        if (spec.shouldExecute()) {
            println("Writing exec file for ${spec::class.simpleName}")
            skippy.writeExecFile(spec::class.java)
        }
    }

    private fun Spec.shouldExecute() = skippy.testNeedsToBeExecuted(this::class.java)
}