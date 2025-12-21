plugins {
  kotlin("jvm") version "2.2.21"
  application
}

kotlin { jvmToolchain(21) }

application { mainClass = "HandlerKt" }

repositories { mavenCentral() }

sourceSets { main { kotlin.srcDir("src") } }

tasks { wrapper { gradleVersion = "9.2.1" } }

dependencies { implementation("com.google.ortools:ortools-java:9.8.3296") }
