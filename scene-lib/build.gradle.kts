import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

sourceSets {
    main {
        withConvention(KotlinSourceSet::class){
            kotlin.srcDir("src")
        }
        java.srcDir("src")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.plumelib:plume-util:1.0.10")
    implementation("org.jetbrains:annotations-java5:18.0.0")
}
