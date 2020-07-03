val branch: String? = System.getenv("GITHUB_REF")
        ?.replace("refs/heads/", "")
        ?.replace("refs/tags/", "")

group = "net.playlegend"
version = if (System.getenv("CI") != null) {
    branch.toString()
} else {
    "dev"
}.replace("/", "-")

plugins {
    java
    `maven-publish`
    checkstyle
    id("com.github.johnrengelman.shadow") version "5.1.0"
    id("com.gorylenko.gradle-git-properties") version "2.2.2"
}

tasks.create<Copy>("copyHooks") {
    from(file("./hooks/"))
    into(file("./.git/hooks/"))
}

tasks.getByPath("prepareKotlinBuildScriptModel").dependsOn("copyHooks")

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    mavenCentral()
    jcenter()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.15.2-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.12")
    annotationProcessor("org.projectlombok:lombok:1.18.12")
}

checkstyle {
    toolVersion = "8.34"
    config = project.resources.text.fromUri("https://static.playlegend.net/checkstyle.xml")
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
            artifact(tasks["shadowJar"])
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
