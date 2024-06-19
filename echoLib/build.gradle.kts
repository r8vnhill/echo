import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.dokka")
    kotlin("jvm")
    `maven-publish`
}

val dokkaVersion = extra["dokka.version"] as String

dependencies {
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:$dokkaVersion")
}

tasks.jar {
    manifest {
        attributes["Implementation-Title"] = "echoLib"
        attributes["Implementation-Version"] = project.version
    }
    from(sourceSets.main.get().output)
}

val dokkaHtml by tasks.getting(DokkaTask::class)

/* ... */
val dokkaJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

tasks.register("releaseLocal") {
    dependsOn("dokkaJar", "sourcesJar")
}

kotlin {
    jvmToolchain(17)
}

/* ... */
tasks.withType<GenerateModuleMetadata>().configureEach {
    dependsOn(sourcesJar)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "cl.ravenhill"
            artifactId = "echolib"
            version = project.version.toString()
            from(components["kotlin"])
            artifact(sourcesJar.get())
            artifact(dokkaJar.get())
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/r8vnhill/echo")
            credentials {
                username = System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
