plugins {
    id("itbookstore.feature")
}

android {
    namespace = "com.itbookstore.base"
}

dependencies {
    implementation(projects.resource)
    implementation(projects.feature.navigation)
    implementation(projects.core.data)
}