plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.testapi3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.testapi3"
        minSdk = 27
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
}

dependencies {
    implementation("com.android.volley:volley:1.2.1")  // HTTP requesty
    implementation("com.google.code.gson:gson:2.10")  // Práce s JSON
    implementation("androidx.recyclerview:recyclerview:1.2.1")  // RecyclerView pro seznam
    implementation("com.squareup.picasso:picasso:2.8")  // Načítání obrázků
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}