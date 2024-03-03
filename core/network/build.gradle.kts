plugins {
    id("itbookstore.module")
}

android {
    namespace = "com.itbookstore.network"
}

dependencies {
    implementation(libs.bundles.retrofit)
}