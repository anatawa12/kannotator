import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    kotlin("jvm") version "1.3.61"
}

require(hasProperty("javaHome7")) { "Set the property 'javaHome7' in your your gradle.properties pointing to a Java 6 or 7 installation"}

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
    compile("org.ow2.asm:asm-debug-all:4.0")
    implementation("junit:junit:4.10")
    testImplementation("net.sf.jung:jung-graph-impl:2.0.1")
    testImplementation("net.sf.jung:jung-algorithms:2.0.1")
    testImplementation("net.sf.jung:jung-visualization:2.0.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    implementation("com.goldmansachs:gs-collections:4.1.0")
    implementation("com.google.guava:guava:13.0.1")
    implementation("org.jetbrains:annotations-java5:18.0.0")
    implementation(project(":scene-lib"))
}

val javaHome7: String by project

val javaExecutablesPath = File(javaHome7, "bin")
fun javaExecutable(execName: String): String {
    val executable = File(javaExecutablesPath, execName)
    require(executable.exists()) { "There is no ${execName} executable in ${javaExecutablesPath}" }
    return executable.toString()
}

val rootProjectDir = projectDir

allprojects {
    afterEvaluate {
        tasks {
            java {
                sourceCompatibility = JavaVersion.VERSION_1_7
                targetCompatibility = JavaVersion.VERSION_1_7
            }

            compileKotlin {
                kotlinOptions.jvmTarget = "1.6"
            }
            compileTestKotlin {
                kotlinOptions.jvmTarget = "1.6"
            }

            withType<Test>().configureEach {
                executable = javaExecutable("java")
                workingDir = rootProjectDir
            }

            withType<JavaExec>().configureEach {
                executable = javaExecutable("java")
            }
        }
    }
}

project(":integration_tests") {
    afterEvaluate {
        tasks {
            withType<Test>().configureEach {
                executable = javaExecutable("java")
                workingDir = rootProjectDir
                jvmArgs("-Xmx8192M", "-Xmn1024M")
            }
        }
    }
}
