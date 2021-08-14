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
version = "1.2"

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

    implementation("io.grpc", "grpc-all", "1.26.0")
    implementation("io.grpc", "grpc-services", "1.28.0")

    implementation("io.netty", "netty-tcnative-boringssl-static", "2.0.26.Final")

    implementation("javax.annotation", "javax.annotation-api", "1.2-b01")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.17.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.26.0"
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