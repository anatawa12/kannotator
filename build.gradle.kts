import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    kotlin("jvm") version "1.3.61"
}

group = "org.example"
version = "1.0-SNAPSHOT"

sourceSets {
    main {
        withConvention(KotlinSourceSet::class){
            kotlin.srcDir("src")
            kotlin.srcDir("kotlinlib")
        }
        java.srcDir("src")
        java.srcDir("kotlinlib")
    }
    test {
        withConvention(KotlinSourceSet::class){
            kotlin.srcDir("test")
            kotlin.srcDir("testData")
        }
        java.srcDir("test")
        java.srcDir("testData")
        resources.srcDir("testData")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("org.ow2.asm:asm-debug-all:5.2")
    implementation("junit:junit:4.10")
    // jung
    implementation("com.goldmansachs:gs-collections:4.1.0")
    implementation("com.google.guava:guava:13.0.1")
    implementation("org.jetbrains:annotations:18.0.0")
    implementation(project(":scene-lib"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
