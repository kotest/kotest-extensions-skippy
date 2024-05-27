plugins {
   `java-library`
   `maven-publish`
   signing
   alias(libs.plugins.skippy)
   alias(libs.plugins.kotlin.jvm)
   `jvm-test-suite`
}

group = "io.kotest.extensions"
version = Ci.version

repositories {
   mavenLocal()
   mavenCentral()
   maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
   implementation(libs.kotest.framework.api)
   implementation(libs.skippy.core)
   testImplementation(libs.mockk)
}

java {
   withJavadocJar()
   withSourcesJar()
   toolchain {
      languageVersion.set(JavaLanguageVersion.of(17))
   }
}

apply("./publish.gradle.kts")

@Suppress("UnstableApiUsage")
testing {
   suites {
      val test by getting(JvmTestSuite::class) {
         useJUnitJupiter()
         dependencies {
            implementation(libs.kotest.runner.junit5)
         }
      }

      val integrationTest by registering(JvmTestSuite::class) {
         testType.set(TestSuiteType.INTEGRATION_TEST)

         dependencies {
            implementation(project())
            implementation(libs.kotest.runner.junit5)
            implementation(gradleTestKit())
         }

         useJUnitJupiter()

         targets {
            all {
               testTask.configure {
                  mustRunAfter(test)
               }
            }
         }
      }
   }
}
