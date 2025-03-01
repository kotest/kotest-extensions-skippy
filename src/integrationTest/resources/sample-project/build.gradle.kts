plugins {
   id("io.skippy") version "0.0.25"
   kotlin("jvm") version "1.9.24"
   id("com.adarshr.test-logger") version "4.0.0"
}

repositories {
   mavenCentral()
}

dependencies {
   testImplementation("io.kotest:kotest-runner-junit5:5.9.0")
   testImplementation("io.kotest.extensions:kotest-extensions-skippy:0.0.1")
}

tasks.withType<Test>().configureEach {
   useJUnitPlatform()
}
