plugins {
    id("java-library")
}

group = "net.alphos.api"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("com.google.inject", "guice", "4.2.1")
    implementation("com.google.inject.extensions", "guice-assistedinject", "4.2.1")
}