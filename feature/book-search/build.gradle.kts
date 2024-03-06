plugins {
    id("itbookstore.feature")
}

android {
    namespace = "com.itbookstore.search"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.domain)
    implementation(projects.resource)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.feature.common)
}