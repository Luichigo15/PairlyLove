plugins {
    alias(libs.plugins.android.application)
    //    alias(libs.plugins.crashlytics)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = Configuration.applicationId
    compileSdk {
        version = release(Configuration.compileSdk)
    }

    defaultConfig {
        applicationId = Configuration.applicationId
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.buildVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    sourceSets {
        create("dev") {
            java.directories.add("src/dev/java")
        }
        create("prod") {
            java.directories.add("src/prod/java")
        }
    }
    flavorDimensions += listOf("version")
    productFlavors {
        create("dev") {
            applicationId = Configuration.applicationId.plus(".dev")
            dimension = "version"
            versionNameSuffix = "-dev"
            externalNativeBuild {
                cmake {
                    arguments += listOf("-DVERSION=dev")
                }
            }
        }
        create("prod") {
            applicationId = Configuration.applicationId
            dimension = "version"
            versionNameSuffix = "-prod"
            externalNativeBuild {
                cmake {
                    arguments += listOf("-DVERSION=prod")
                }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    externalNativeBuild {
        cmake { path("CMakeLists.txt") }
    }
    base.archivesName.set(Configuration.getBuildName())
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
}

dependencies {
    //Local utils
    implementation(files("../libs/l15common.aar"))

    //AndroidBase
    implementation(libs.androidx.core)
    implementation(libs.activity.compose)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.compose)
    implementation(libs.lifecycle.runtime)
    implementation(libs.adaptive.navigation)
    implementation(libs.navigation.runtime)
    implementation(libs.navigation.ui)

    //Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.icons.extended)
    implementation(libs.compose.runtime)

    //Injection
    implementation(libs.hilt)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.compiler)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firestore)
//    implementation(libs.crashlytics)
//    implementation(libs.analytics)
    implementation(libs.messaging)

    //Persistence
    implementation(libs.datastore)
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.sqlcipher)

    //Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    //Other
    implementation(libs.coil)
    implementation(libs.lottie)
    implementation(libs.mediaPipe)
    implementation(libs.camerax.core)
    implementation(libs.camerax.camera2)
    implementation(libs.camerax.lifecycle)
    implementation(libs.camerax.compose)

    //Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.test.ui)
    debugImplementation(libs.compose.test.ui.test.manifest)
    debugImplementation(libs.compose.test.ui.tooling)
}