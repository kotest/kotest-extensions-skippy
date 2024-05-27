package io.kotest.extensions.skippy

import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.skippy.core.SkippyTestApi

object SkippyPredictionExtension : TestCaseExtension {
    private val skippy = SkippyTestApi.INSTANCE

    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult =
        if (testCase.spec.shouldExecute()) {
            execute(testCase)
        } else {
            TestResult.Ignored
        }

    private fun Spec.shouldExecute() = skippy.testNeedsToBeExecuted(this::class.java)
}
