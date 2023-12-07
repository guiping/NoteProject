plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")

}

android {
    namespace = "com.odfudndh.mvjsu"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.odfudndh.mvjsu"
        minSdk = 24
        targetSdk = 33
        versionCode = 10
        versionName = "1.1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    val room_version = "2.5.0"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // 协程核心库
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    // 协程Android支持库
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
    // 协程Java8支持库
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.3")
    // lifecycle对于协程的扩展封装
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")



    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    //BaseQuickAdapter
    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper4:4.1.0")

    implementation ("com.appsflyer:af-android-sdk:6.9.0")
    implementation ("com.google.code.gson:gson:2.10.1")
}