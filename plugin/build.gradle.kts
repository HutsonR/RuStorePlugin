plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

//
//publishing {
//    publications {
//        create<MavenPublication>("pluginMaven") {
//            from(components["java"])
//            groupId = "com.blackcube.rustorepublisher"
//            artifactId = "rustore-plugin"
//            version = "1.0.0"
//        }
//    }
//    repositories {
//        mavenLocal()
//    }
//}

gradlePlugin {
    val ruStoreMarket by plugins.creating {
        id = "com.blackcube.rustore"
        implementationClass = "com.blackcube.rustorepublisher.plugin.PublishAppPlugin"
    }
}
//
//publishing {
//    repositories {
//        mavenLocal()
//    }
//}

dependencies {
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.json)
}