import com.google.protobuf.gradle.*;

plugins {
    id("maven-publish")
    id("signing")
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

group = "dev.alphos"
version = "1.2"

fun getSigningProperty(name: String): String? = System.getenv("SIGNING_$name")
fun getAuthenticationProperty(name: String, envName: String): String? =
    if(project.hasProperty(name)) project.property(name) as String? else System.getenv(envName)

repositories {
    mavenCentral()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
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

publishing {
    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            name = "OSSRHostingRepository"

            credentials {
                username = getAuthenticationProperty("ossrhUsername", "OSSRH_USER")
                password = getAuthenticationProperty("ossrhPassword", "OSSRH_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            version = rootProject.version.toString()

            pom {
                url.set("https://github.com/Lxnus/alphos-api")

                developers {
                    developer {
                        id.set("Linus Schmidt")
                        email.set("contact@alphos.dev")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Lxnus/alphos-api.git")
                    developerConnection.set("scm:git:ssh://github.com/Lxnus/alphos-api.git")
                    url.set("https://github.com/Lxnus/alphos-api")
                }
            }

            from(components["java"])
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

if(rootProject.hasProperty("enableSigning")) {
    signing {
        val signingKey = getSigningProperty("KEY")
        val signingPassword = getSigningProperty("PASSWORD")

        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications["mavenJava"])
    }
}
