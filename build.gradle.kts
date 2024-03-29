// === PLUGINS =====================================================================================

plugins {
    java
    idea
    signing
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

// === MAIN BUILD DETAILS ==========================================================================

group = "com.norswap"
version = "2.1.12"
description = "A collection of Java (8+) utilities"
java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

val website = "https://github.com/norswap/${project.name}"
val vcs = "https://github.com/norswap/${project.name}.git"

sourceSets.main.get().java.srcDir("src")

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test.get().useTestNG()

tasks.javadoc.get().options {
    // https://github.com/gradle/gradle/issues/7038
    this as StandardJavadocDocletOptions
    addStringOption("Xdoclint:none", "-quiet")
    if (JavaVersion.current().isJava9Compatible)
        addBooleanOption("html5", true) // nice future proofing
}

// === IDE =========================================================================================

idea.module {
    isDownloadJavadoc = true
    isDownloadSources = true
}

// === PUBLISHING ==================================================================================

// Publication definition
publishing.publications.create<MavenPublication>(project.name) {
    from(components["java"])
    pom.withXml {
        val root = asNode()
        root.appendNode("name", project.name)
        root.appendNode("description", project.description)
        root.appendNode("url", website)
        root.appendNode("scm").apply {
            appendNode("url", website)
            val connection = "scm:git:git@github.com:norswap/${project.name}.git"
            appendNode("connection", connection)
            appendNode("developerConnection", connection)
        }
        root.appendNode("licenses").appendNode("license").apply {
            appendNode("name", "The BSD 3-Clause License")
            appendNode("url", "$website/blob/master/LICENSE")
        }
        root.appendNode("developers").appendNode("developer").apply {
            appendNode("id", "norswap")
            appendNode("name", "Nicolas Laurent")
        }
    }
}

signing {
    // Create a 'gradle.properties' file at the root of the project, containing the next two lines,
    // replacing the values as needed:
    // signing.gnupg.keyName=<KEY_ID>
    // signing.gnupg.passphrase=<PASSWORD_WITHOUT_QUOTES>
    // (You'll need to setup and/or import that key with gpg --generate-key / --import)

    // You'll need to have GnuPG installed, and it should be aliased to "gpg2"
    // (homebrew on mac links it to only "gpg" by default).

    // You are forced to use the agent, because otherwise Gradle wants a private keyring, which
    // gnupg doesn't create by default since version 2.x.y. An alternative is to export the
    // private keys to a keyring file.

    useGpgCmd()
    sign(publishing.publications[project.name])
}

// Use `gradle publishToSonatype closeAndReleaseSonatypeStagingDirectory` to deploy to Maven Central.
nexusPublishing {
    repositories {
        sonatype {
            // Create a 'gradle.properties' file at the root of the project, containing the next two
            // lines, replacing the values as needed:
            // mavenCentralUsername=<USERNAME>
            // mavenCentralPassword=<PASSWORD>
            username.set(property("mavenCentralUsername") as String)
            password.set(property("mavenCentralPassword") as String)
        }
    }
}

// Deploy to all locations.
tasks.register("deploy") {
    // NOTE: must be changed if we only want to publish a single publications.
    val publishToSonatype = tasks["publishToSonatype"]
    val closeAndReleaseSonatype = tasks["closeAndReleaseSonatypeStagingRepository"]
    dependsOn(publishToSonatype)
    dependsOn(closeAndReleaseSonatype)
    closeAndReleaseSonatype.mustRunAfter(publishToSonatype)
}

// === DEPENDENCIES ================================================================================

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.testng:testng:6.14.3")
}

// =================================================================================================
