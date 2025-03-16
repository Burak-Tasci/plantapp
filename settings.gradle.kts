pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "plantapp"
include(":app")
include(":data")
include(":domain")
include(":presentation")
include(":presentation:home")
include(":presentation:onboarding")
include(":presentation:paywall")
include(":presentation:core")
include(":presentation:sample")
