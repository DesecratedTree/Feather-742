plugins {
    kotlin("jvm") version "1.9.24"
    application
    java
}

group = "com.feather"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin standard library
    implementation(kotlin("stdlib"))

    // Kotlin Scripting APIs
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm:1.9.24")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host:1.9.24")
    implementation("org.jetbrains.kotlin:kotlin-script-util:1.8.22")

    implementation("io.netty:netty:3.9.9.Final")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("org.yaml:snakeyaml:2.4")

    // Optional: Kotlin reflection if you want to use annotations
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.24")

    // Optional: Logging
    implementation("org.slf4j:slf4j-simple:2.0.12")
}

application {
    mainClass.set("com.feather.Launcher") // Replace with your server's actual entry point
}

kotlin {
    jvmToolchain(8) // or whatever version you are targeting
}
