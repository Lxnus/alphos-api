import com.google.protobuf.gradle.*;

plugins {
    id("java-library")
    id("com.google.protobuf") version("0.8.17")
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.google.protobuf", "protobuf-gradle-plugin", "0.8.17")
    }
}

sourceSets {
    create("grpc") {
        proto {
            srcDir("src/grpc/protobuf")
        }
    }
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
    implementation("com.google.protobuf", "protobuf-java", "4.0.0-rc-2")

    implementation("io.grpc", "grpc-netty", "1.16.1")
    implementation("io.grpc", "grpc-protobuf", "1.16.1")
    implementation("io.grpc", "grpc-stub", "1.16.1")

    implementation("javax.annotation", "javax.annotation-api", "1.2-b01")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.17.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.20.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}