plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
}

allprojects {
    group = "cl.ravenhill.echo"
    version = extra["echo.version"] as String
}

subprojects {
    repositories {
        mavenCentral()
    }
}

tasks.register<Copy>("copyLib") {
    group = "build"
    description = "Copies the library JAR to the application"
    dependsOn(":echoLib:jar")
    from("echoLib/build/libs")
    into("echoApp/libs")
}
