import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("multiplatform") version "1.9.21"
    id("com.android.library") version "8.1.4"
    kotlin("plugin.serialization") version "1.9.21"
}

repositories {
    mavenCentral()
    google()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

android {
    namespace = "software.momento.kotlin.sdk"

    compileSdk = 34
    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["TestApiKey"] = System.getenv("TEST_API_KEY") ?: "noApiKeySet"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

kotlin {
    explicitApi()
    androidTarget()
    jvm()
    jvmToolchain(11)
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")
                implementation("io.grpc:grpc-api:1.57.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("software.momento.kotlin:client-protos-jvm:0.1.0-SNAPSHOT")
                runtimeOnly("io.grpc:grpc-netty:1.57.2")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("software.momento.kotlin:client-protos-android:0.1.0-SNAPSHOT")
                runtimeOnly("io.grpc:grpc-okhttp:1.57.2")
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.robolectric:robolectric:4.11.1")
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
                implementation("androidx.test.ext:junit:1.1.5")
                implementation("androidx.test.espresso:espresso-core:3.5.1")
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions.apiVersion.set(KotlinVersion.KOTLIN_1_6)
    compilerOptions.languageVersion.set(KotlinVersion.KOTLIN_1_6)
}
