plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

/*
Apply is basically just a class that we can override to customize what we want
 */
apply<StartCounterPlugin>()

class StartCounterPlugin : Plugin<Project> {
    private var startNumber = 0
    private var endNumber = 10
    override fun apply(project: Project) {
        do {
            startNumber++
            println("Still not at 10 yet.. currently at: $startNumber")
        } while (startNumber < endNumber)
    }
}

android {
    namespace = "com.example.newgradle"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.newgradle"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    /*
    Having a combination of product flavors and build types creates
    build variants
     */
    // makes different versions linked together
    flavorDimensions += "paid_status"
    flavorDimensions += "style"
    // useful for having different versions of an application
    productFlavors {
        // paid dimensions
        create("free") {
            applicationIdSuffix = ".free"
            dimension = "paid_status"
        }
        create("paid") {
            applicationIdSuffix = ".paid"
            dimension = "paid_status"
        }

        // style dimensions
        create("blue") {
            dimension = "style"
        }
        create("yellow") {
            dimension = "style"
        }
        create("orange") {
            dimension = "style"
        }
    }

    // stage app is in during development lifecycle
    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            buildConfigField("String", "API_BASE_URL", "\"https://api.example.com\"")
        }
        create("staging") {
            isMinifyEnabled = false
            isDebuggable = false
            buildConfigField("String", "API_BASE_URL", "\"https://api.example.com\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_BASE_URL", "\"https://api.example.com\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    /*
    Implement countstats module
     */
    implementation(project(":countstats"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}