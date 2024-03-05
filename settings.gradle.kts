enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        gradlePluginPortal()
    }
}

rootProject.name = "ITBookStore"
include(":app")
include(":resource")

include(":core:data")
include(":core:network")
include(":core:domain")
include(":core:ui")
include(":core:model")

include(":feature:book-search")
include(":feature:book-detail")
include(":feature:navigation")