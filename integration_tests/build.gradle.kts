import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

configurations {
    //testImplementation.get().extendsFrom(project(":").configurations.testImplementation.get())
}

sourceSets {
    test {
        withConvention(KotlinSourceSet::class){
            kotlin.srcDir("test")
        }

        compileClasspath += project(":").sourceSets.test.get().output
        runtimeClasspath += project(":").sourceSets.test.get().output
    }
}

repositories {
    mavenCentral()
}

configurations {
    testImplementation.get().extendsFrom(project(":").configurations.testImplementation.get())
    testRuntimeOnly.get().extendsFrom(project(":").configurations.testRuntimeOnly.get())
}

dependencies {
    testImplementation(kotlin("stdlib-jdk8"))
    testCompile(project(":"))
    compile("org.ow2.asm:asm-debug-all:4.0")
    testImplementation("junit:junit:4.10")
}
