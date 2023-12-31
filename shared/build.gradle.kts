plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlin-parcelize")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.8.21"
    id("com.google.devtools.ksp") version "1.8.20-1.0.11"
    id("de.jensklingenberg.ktorfit") version "1.0.0"
    id("dev.icerock.mobile.multiplatform-resources")
    id("com.palantir.git-version") version "3.0.0"
    id("com.github.gmazzo.buildconfig") version "4.1.2"
    id("com.mikepenz.aboutlibraries.plugin") version "10.8.3"
    id("io.realm.kotlin") version "1.11.1"
}

val ktorVersion = "2.3.1"
val ktorfitVersion = "1.4.3"
val voyagerVersion = "1.0.0-rc07"

configure<de.jensklingenberg.ktorfit.gradle.KtorfitGradleConfiguration> {
    version = ktorfitVersion
}

val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()

buildConfig {
    buildConfigField("String", "APP_NAME", "\"${project.name}\"")
    buildConfigField("String", "APP_VERSION", provider { "\"${version}\"" })
    buildConfigField("long", "BUILD_TIME", "${System.currentTimeMillis()}L")
}

kotlin {
    android()

    jvm("desktop")

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")

                implementation("io.insert-koin:koin-core:3.4.3")
                implementation("io.insert-koin:koin-test:3.4.3")

                implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.22")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:atomicfu:0.22.0")

                implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.18")
                implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfitVersion")

                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-mock:$ktorVersion")

                implementation("org.drewcarlson:ktsoup-core:0.3.0")

                implementation("dev.icerock.moko:resources:0.23.0")
                implementation("dev.icerock.moko:resources-compose:0.23.0")

                implementation("io.realm.kotlin:library-base:1.11.1")

                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")
                implementation("commons-codec:commons-codec:1.16.0")
                implementation("com.google.guava:guava:32.1.2-android")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation("ch.qos.logback:logback-classic:1.4.11")
                implementation("commons-codec:commons-codec:1.16.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")

                //for Desktop Preview
                implementation("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.7.80")
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "dev.tkhs.handaimanager.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
dependencies {
    add("kspCommonMainMetadata", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspDesktop", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspAndroid", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspIosX64", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    commonTestImplementation("dev.icerock.moko:resources-test:0.23.0")
}

multiplatformResources {
    multiplatformResourcesPackage = "dev.tkhs.handaimanager" // required
}
