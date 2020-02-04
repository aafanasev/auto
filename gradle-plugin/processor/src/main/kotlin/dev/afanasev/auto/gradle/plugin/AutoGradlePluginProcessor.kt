package dev.afanasev.auto.gradle.plugin

import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation

typealias PluginId = String
typealias ImplementationClass = String

@AutoService(Processor::class)
class AutoGradlePluginProcessor : AbstractProcessor() {

    private val plugins = mutableMapOf<PluginId, ImplementationClass>()

    override fun getSupportedAnnotationTypes(): Set<String> = setOf(AutoGradlePlugin::class.java.name)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        if (roundEnv.processingOver()) {
            generateFiles()
        } else {
            processAnnotations(roundEnv)
        }

        return true
    }

    private fun generateFiles() {
        plugins.forEach { (id, cls) ->
            val resourceName = "META-INF/gradle-plugins/$id.properties"
            val outputFile = processingEnv.filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceName)

            outputFile.openWriter().use {
                it.write("implementation-class=$cls")
            }

            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "Output: ${outputFile.toUri().path}")
        }
    }

    private fun processAnnotations(roundEnv: RoundEnvironment) {
        roundEnv.getElementsAnnotatedWith(AutoGradlePlugin::class.java)
                .filter {
                    it.kind == ElementKind.CLASS
                }
                .map {
                    it as TypeElement
                }
                .forEach {
                    val pluginId = it.getAnnotation(AutoGradlePlugin::class.java).pluginId
                    val implementationClass = processingEnv.elementUtils.getBinaryName(it).toString()
                    plugins[pluginId] = implementationClass
                }
    }
}