import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// issue https://youtrack.jetbrains.com/issue/KTIJ-19370
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
   java
   `java-library`
   signing
   `maven-publish`
   alias(libs.plugins.kotlin.jvm)
   jacoco
   alias(libs.plugins.skippy)
}

dependencies {
   implementation(libs.kotest.framework.api)
   implementation(libs.skippy.core)

   testImplementation(libs.kotest.runner.junit5)
   testImplementation(libs.kotest.framework.datatest)
   testImplementation(libs.kotest.property)
}

tasks.withType<Test> {
   useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
   kotlinOptions.jvmTarget = libs.versions.jvm.get()
}

repositories {
   mavenLocal()
   mavenCentral()
   maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
}

