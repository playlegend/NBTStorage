<a href="https://playlegend.net"><img src="assets/full-logo-stone-highres.png" width="60%"></a>

# NBTStorage
NBTStorage is an API for editing all kinds of Nbt related data in Minecraft **Bukkit**.
The original version has been developed by [@RewisServer](https://github.com/RewisServer).

## Maven Repository
NBTStorage is available through our Maven [repository](https://repository.playlegend.net).
You have to replace **version** with your desired values. 

### Gradle (Kotlin)
```kotlin
repositories {
    maven("https://repository.playlegend.net/artifactory/opensource/")
}

dependencies {
    compileOnly("net.playlegend:nbtstorage:VERSION")
}
```

### Maven
```xml
<repositories>
    <!-- This adds the Legend Maven repository to the build -->
    <repository>
        <id>legend-repo</id>
        <url>https://repository.playlegend.net/artifactory/opensource</url>
    </repository>
</repositories>

<dependency>
    <groupId>net.playlegend</groupId>
    <artifactId>nbtstorage</artifactId>
    <version>VERSION</version>
</dependency>
```

## Build a custom version
To build your own version of NBTStorage just execute the following command in project root.
```shell script
./gradlew shadowJar
```
You can find your artifacts in `/build/libs/`.

## A quick example
WIP
