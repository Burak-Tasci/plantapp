plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.tsci.plantapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tsci.plantapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(project(":presentation:core"))
    implementation(project(":presentation:sample"))
    implementation(project(":presentation:onboarding"))
    implementation(project(":presentation:paywall"))
    implementation(project(":presentation:home"))
    implementation(project(":domain"))
    implementation(project(":data"))

}