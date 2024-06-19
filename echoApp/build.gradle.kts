plugins {
    id("io.gitlab.arturbosch.detekt")
    kotlin("jvm")
    application
}

application {
    mainClass.set("cl.ravenhill.echo.EchoKt")
}

/* ... */
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/r8vnhill/echo")
        credentials {
            username = System.getenv("GITHUB_USER")
            password = System.getenv("GITHUB_TOKEN")
        }
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
    implementation("cl.ravenhill:echolib:1.0.1")
}

kotlin {
    jvmToolchain(17)
}
