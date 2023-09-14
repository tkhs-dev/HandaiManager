import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.github.hierynomus.license") version "0.16.1"
    id("com.mikepenz.aboutlibraries.plugin") version "10.8.3"
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))
                implementation("io.insert-koin:koin-core:3.4.3")
                implementation("io.github.xxfast:decompose-router:0.2.1")
                implementation("com.arkivanov.decompose:decompose:2.0.0-compose-experimental-beta-01")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.0.0-compose-experimental-beta-01")
                implementation("com.bybutter.compose:compose-jetbrains-expui-theme:2.0.0")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Exe, TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "HandaiManager"
            packageVersion = "1.0.0"
            modules("jdk.unsupported")
            modules("jdk.accessibility")

            windows{
                menu = true
                shortcut = true
                upgradeUuid = "18159995-d967-4CD2-8885-77BFA97CFA9F"
            }
        }
    }
}
