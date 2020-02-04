package dev.afanasev.auto.gradle.plugin

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class AutoGradlePlugin(val pluginId: String)