pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "android-based"
include(":app")
include(":core:common")
include(":core:data")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:utilities")
include(":core:nav")
include(":core:ui")

include(":feature:movie")
include(":feature:auth")
include(":feature:splash")
include(":feature:services")
include(":feature:menu")
include(":feature:common")

include(":api:splash")
include(":core:entity")
