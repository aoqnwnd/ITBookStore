plugins {
    id("itbookstore.module")
    id("itbookstore.compose")
}

android {
    namespace = "com.itbookstore.navigation"
}

dependencies {
    implementation(projects.resource)
}