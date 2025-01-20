package com.blackcube.rustorepublisher.plugin

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ListProperty
import javax.inject.Inject

open class PublishExtension @Inject constructor(objects: ObjectFactory) {
    val credentials: Property<String> = objects.property(String::class.java)
    val credentialsPath: Property<String> = objects.property(String::class.java)

    companion object {
        const val EXTENSION_NAME = "ruStorePublish"
    }

    override fun toString(): String {
        return "PublishExtension(credentials=$credentials, credentialsPath=$credentialsPath)"
    }
}