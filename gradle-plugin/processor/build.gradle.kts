plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    compileOnly(project(":annotation"))

    compileOnly("com.google.auto.service:auto-service-annotations:1.0-rc5")
    kapt("com.google.auto.service:auto-service:1.0-rc5")
}