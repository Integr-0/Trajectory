import java.net.URI
apply(plugin = "maven-publish")

plugins {
    kotlin("jvm") version "1.9.0"
    application
    `maven-publish`
    signing
}

group = "io.github.integr-0"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
    repositories {
        maven {
            name = "Trajectory" //  optional target repository name
            url = URI("https://s01.oss.sonatype.org/content/repositories/releases/")
            credentials {
                username = "y"
                password = "x"
            }
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}


