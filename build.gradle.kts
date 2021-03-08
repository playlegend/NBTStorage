val branch: String? = System.getenv("GITHUB_REF")
        ?.replace("refs/heads/", "")
        ?.replace("refs/tags/", "")

group = "net.playlegend"
version = if (System.getenv("CI") != null) {
    if (branch.equals("stage") || branch.equals("prod")
            || branch!!.matches(Regex("v\\d+[.]\\d+[.]\\d+"))) {
        branch.toString()
    } else {
        "$branch-SNAPSHOT"
    }
} else {
    "dev-SNAPSHOT"
}.replace("/", "-")

plugins {
    java
    `maven-publish`
    checkstyle
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("com.gorylenko.gradle-git-properties") version "2.2.4"
    id("com.github.spotbugs") version "4.7.0"
}

tasks.create<Copy>("copyHooks") {
    from(file("./hooks/"))
    into(file("./.git/hooks/"))
}

tasks.getByPath("prepareKotlinBuildScriptModel").dependsOn("copyHooks")

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
    mavenCentral()
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.18")
    annotationProcessor("org.projectlombok:lombok:1.18.18")
}

checkstyle {
    toolVersion = "8.40"
    config = project.resources.text.fromUri("https://assets.playlegend.net/checkstyle.xml")
}

spotbugs {
    ignoreFailures.set(true)
    showProgress.set(true)
}

gitProperties {
    gitPropertiesName = "git.properties"
    dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
    keys = arrayOf("git.branch", "git.build.host", "git.build.version", "git.commit.id", "git.commit.id.abbrev",
            "git.commit.message.full", "git.commit.message.short", "git.commit.time", "git.commit.user.email",
            "git.commit.user.name", "git.remote.origin.url", "git.total.commit.count").toMutableList()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
}

tasks.javadoc {
    options.encoding = "UTF-8"
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

tasks.withType<JavaCompile> { options.encoding = "UTF-8" }

val tokens = mapOf("VERSION" to project.version)

tasks.withType<ProcessResources> {
    filesMatching("*.yml") {
        filter<org.apache.tools.ant.filters.ReplaceTokens>("tokens" to tokens)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name.toLowerCase()
            version = project.version.toString()

            from(components["java"])
        }
    }
    repositories {
        maven {
            credentials {
                username = System.getenv("repositoryUser")
                password = System.getenv("repositoryPassword")
            }
            url = uri("https://repository.playlegend.net/artifactory/opensource")
        }
    }
}
