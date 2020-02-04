plugins {
    kotlin("jvm")
    kotlin("kapt")

    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    compileOnly(project(":annotation"))

    compileOnly("com.google.auto.service:auto-service-annotations:1.0-rc5")
    kapt("com.google.auto.service:auto-service:1.0-rc5")
}

val sourcesJar by tasks.creating(Jar::class) {
    dependsOn(JavaPlugin.CLASSES_TASK_NAME)
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

publishing {
    publications {
        create("maven", MavenPublication::class.java) {
            from(components["java"])
            artifact(sourcesJar)
            artifactId = "auto-gradle-plugin-processor"
        }
    }
}

bintray {
    user = project.findProperty("bintrayUser") as? String ?: System.getenv("BINTRAY_USER")
    key = project.findProperty("bintrayApiKey") as? String ?: System.getenv("BINTRAY_API_KEY")

    setPublications("maven")

    pkg(delegateClosureOf<com.jfrog.bintray.gradle.BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "auto-gradle-plugin-processor"
        vcsUrl = "https://github.com/aafanasev/auto.git"
        publicDownloadNumbers = true

        setLabels("gradle", "plugin", "autoservice")
        setLicenses("MIT")
    })
}