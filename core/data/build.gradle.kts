plugins {
    id("itbookstore.module")
}

android {
    namespace = "com.itbookstore.data"
}

dependencies {
    implementation(libs.bundles.retrofit)
    implementation(projects.core.model)
}