plugins {
    id("com.android.application")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.compendiumofmateriamedica"
    compileSdk = 34
    //    useLibrary("org.apache.http.legacy")

    defaultConfig {
        applicationId = "com.example.compendiumofmateriamedica"
        minSdk = 27
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
//    packagingOptions {
//        resources.excludes.add("META-INF/*")
//    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.github.chrisbanes:PhotoView:2.3.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.greenrobot:eventbus:3.3.1")

//    implementation("net.sourceforge.htmlunit:htmlunit-android:2.63.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    androidTestImplementation("org.testng:testng:6.9.6")
//    implementation("org.apache.httpcomponents:httpmime:4.5.6")

}