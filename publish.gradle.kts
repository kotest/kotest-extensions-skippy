apply(plugin = "java")
apply(plugin = "java-library")
apply(plugin = "maven-publish")
apply(plugin = "signing")

repositories {
   mavenCentral()
}

val signingKey: String? by project
val signingPassword: String? by project

fun Project.publishing(action: PublishingExtension.() -> Unit) =
   configure(action)

fun Project.signing(configure: SigningExtension.() -> Unit): Unit =
   configure(configure)

fun Project.java(configure: JavaPluginExtension.() -> Unit): Unit =
   configure(configure)


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
}

publishing {
   repositories {
      maven {
         val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
         val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
         name = "deploy"
         url = if (Ci.isRelease) releasesRepoUrl else snapshotsRepoUrl
         credentials {
            username = java.lang.System.getenv("OSSRH_USERNAME") ?: ""
            password = java.lang.System.getenv("OSSRH_PASSWORD") ?: ""
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
