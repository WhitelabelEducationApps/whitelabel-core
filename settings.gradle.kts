pluginManagement {
    resolutionStrategy {
        eachPlugin {
            // In composite builds, AGP lands on the classpath from other included builds
            // without version metadata. Route via useModule to bypass the version check.
            if (requested.id.id == "com.android.kotlin.multiplatform.library") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "whitelabel-kmp-core"
include(":core")
