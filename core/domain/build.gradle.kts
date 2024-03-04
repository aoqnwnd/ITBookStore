plugins {
    id("itbookstore.feature")
}

android {
    namespace = "com.itbookstore.domain.image"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.network)
    implementation(projects.core.model)
}