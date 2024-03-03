plugins {
    id("itbookstore.feature")
}

android {
    namespace = "com.itbookstore.detail"
}

dependencies {
    implementation(projects.resource)
    implementation(projects.core.ui)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}