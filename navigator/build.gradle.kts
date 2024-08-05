plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.detekt)
    id("maven-publish")
}

android {
    namespace = "ru.mulledwineapps.composenavigator"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    detekt {
        config.setFrom(file("../config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
    }
    publishing {
        multipleVariants {
            allVariants()
            withJavadocJar()
            withSourcesJar()
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "ru.mulledwineapps"
            artifactId = "compose-navigator"
            version = "0.1.8"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

dependencies {
    // https://developer.android.com/jetpack/androidx/releases/navigation
    implementation(libs.androidx.navigation.compose)

    // https://developer.android.com/jetpack/compose/bom/bom-mapping
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.material3)

    // Timber
    implementation(libs.timber)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}