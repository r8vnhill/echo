plugins {
    id("io.gitlab.arturbosch.detekt")
    kotlin("jvm")
    application
}

application {
    mainClass.set("cl.ravenhill.echo.EchoKt")
}

repositories {
    flatDir {
        dirs("libs")
    }
}

tasks.register<Jar>("fatJar") {
    group = "build"
    description = "Assembles a JAR archive with dependencies"
    archiveClassifier.set("fat")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
        attributes["Implementation-Title"] = "Echo"
        attributes["Implementation-Version"] = project.version
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}
dependencies {
    implementation(fileTree("libs") { include("*.jar") })
}

kotlin {
    jvmToolchain(17)
}
