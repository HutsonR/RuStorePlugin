// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false

    // Needed to support publishing all modules atomically
    alias(libs.plugins.gradlePublish) apply false
    alias(libs.plugins.nexusPublish)
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}