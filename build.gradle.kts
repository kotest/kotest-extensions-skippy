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

val signingKey: String? by project
val signingPassword: String? by project

val publications: PublicationContainer = (extensions.getByName("publishing") as PublishingExtension).publications

signing {
   useGpgCmd()
   if (signingKey != null && signingPassword != null) {
      @Suppress("UnstableApiUsage")
      useInMemoryPgpKeys(signingKey, signingPassword)
   }
   if (Ci.isRelease) {
      sign(publications)
   }
}

java {
   withJavadocJar()
   withSourcesJar()
   toolchain {
      languageVersion.set(JavaLanguageVersion.of(17))
   }
}

publishing {
   repositories {
      maven {
         val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
         val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
         name = "deploy"
         url = if (Ci.isRelease) releasesRepoUrl else snapshotsRepoUrl
         credentials {
            username = System.getenv("OSSRH_USERNAME") ?: ""
            password = System.getenv("OSSRH_PASSWORD") ?: ""
         }
      }
   }

   publications {
      register("mavenJava", MavenPublication::class) {
         from(components["java"])
         pom {
            name.set("kotest-extensions-skippy")
            description.set("Kotest extension for Skippy")
            url.set("https://www.github.com/kotest/kotest-extensions-skippy")

            scm {
               connection.set("scm:git:http://www.github.com/kotest/kotest-extensions-skippy")
               developerConnection.set("scm:git:http://github.com/kantis")
               url.set("https://www.github.com/kotest/kotest-extensions-skippy")
            }

            licenses {
               license {
                  name.set("The Apache 2.0 License")
                  url.set("https://opensource.org/licenses/Apache-2.0")
               }
            }

            developers {
               developer {
                  id.set("sksamuel")
                  name.set("Stephen Samuel")
                  email.set("sam@sksamuel.com")
               }

               developer {
                  id.set("Kantis")
                  name.set("Emil Kantis")
                  email.set("emil.kantis@proton.me")
               }
            }
         }
      }
   }
}

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
