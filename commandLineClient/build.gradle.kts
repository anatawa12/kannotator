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
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    //plume
    implementation("org.plumelib:options:1.0.3")
    compile(project(":"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
