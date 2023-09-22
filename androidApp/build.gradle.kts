import com.android.build.api.dsl.Packaging

plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
    id("com.mikepenz.aboutlibraries.plugin") version "10.8.3"
}

kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation("io.insert-koin:koin-core:3.4.3")
                implementation("io.insert-koin:koin-test:3.4.3")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "dev.tkhs.handaimanager"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "dev.tkhs.handaimanager.HandaiManager"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }

    //ToDo: this packaging option should be removed when kotlin datetime lib is updated
    packaging{
        resources{
            excludes.add("META-INF/versions/9/previous-compilation-data.bin")
        }
    }
}
dependencies {
    implementation("androidx.compose.material3:material3:1.0.0-alpha08")
}
