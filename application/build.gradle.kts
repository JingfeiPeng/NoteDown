import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "cs398.team109.notetaker"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    gradlePluginPortal()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.mockito:mockito-core:3.+")
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("com.mikepenz:multiplatform-markdown-renderer-jvm:0.4.0")
    implementation("com.codewaves.codehighlight:codehighlight:1.0.2")

    val commonMarkVersion = "0.18.2"
    val commonMarkExtensions = setOf("gfm-strikethrough")
    implementation("org.commonmark", "commonmark", commonMarkVersion)
    commonMarkExtensions.forEach { extension ->
        implementation("org.commonmark", "commonmark-ext-$extension", commonMarkVersion)
    }
    implementation("org.jsoup:jsoup:1.14.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "NoteDown"
            packageVersion = "1.0.0"

            val resources = File(project.sourceSets["main"].resources.sourceDirectories.asPath)

            macOS {
                iconFile.set(resources.resolve("icon.icns"))
            }
            windows {
                iconFile.set(resources.resolve("icon.ico"))
                menuGroup = "NoteDown"
                shortcut = true
            }
            linux {
                iconFile.set(resources.resolve("icon.png"))
            }
        }
    }
}