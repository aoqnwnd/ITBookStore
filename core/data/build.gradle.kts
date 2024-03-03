plugins {
    id("itbookstore.feature")
}

android {
    namespace = "com.itbookstore.data"
}

dependencies {
    implementation(libs.bundles.retrofit)
    implementation(projects.core.network)
}