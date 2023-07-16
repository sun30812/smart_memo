import org.gradle.kotlin.dsl.support.classFilePathCandidatesFor
import kotlin.script.experimental.jvm.util.classpathFromClass

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

dependencies {
    classFilePathCandidatesFor("com.google.protobuf:protobuf-gradle-plugin:0.9.1")
}