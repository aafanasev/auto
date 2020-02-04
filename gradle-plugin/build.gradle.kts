plugins {
    kotlin("jvm") version "1.3.61" apply false
}

allprojects {
    repositories {
        jcenter()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}