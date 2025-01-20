package com.blackcube.rustorepublisher.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class PublishAppPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register<PublishAppTask>("publishRuStoreApp") {
            group="Publishing"
            description="Publish app to RuStore market"
        }

        val extension = project.extensions.create(PublishExtension.EXTENSION_NAME, PublishExtension::class.java)

        project.afterEvaluate {
            println("$extension")
        }
    }

}