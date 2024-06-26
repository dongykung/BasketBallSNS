import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.dkproject.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String","GOOGLE_API_KEY",getApiKey("GOOGLE_API_KEY"))
        buildConfigField("String","GOOGLE_LOGIN_KEY",getApiKey("GOOGLE_LOGIN_KEY"))
        buildConfigField("String","KAKAO_API_KEY",getApiKey("KAKAO_API_KEY"))
        resValue("string","kakao_oauth_host",getApiKey("kakao_oauth_host"))


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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

fun getApiKey(propertyKey:String):String{
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {
    implementation(project(":domain"))

    //hilt,dagger
    implementation("com.google.dagger:hilt-android:2.48.1")
    ksp("com.google.dagger:dagger-compiler:2.48.1") // Dagger compiler
    ksp ("com.google.dagger:hilt-compiler:2.48.1")
    ksp("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")


    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    //firebase auth
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    //firebase firestore
    implementation("com.google.firebase:firebase-firestore")

    //icon
    implementation("androidx.compose.material:material-icons-extended-android:1.6.3")

    //coil
    implementation("io.coil-kt:coil:2.6.0")
    implementation("io.coil-kt:coil-compose:2.6.0")

    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //firebase geo
    implementation("com.firebase:geofire-android-common:3.2.0")

    //realtime database
    implementation("com.google.firebase:firebase-database-ktx")


    //threetenabp - java - time
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.7")

    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")


    //googlemap
    implementation("com.google.maps.android:maps-compose:4.3.3")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps.android:maps-compose-widgets:4.3.3")
    //googleplace
    implementation("com.google.android.libraries.places:places:3.4.0")

    //paging3
    implementation("androidx.paging:paging-compose:3.2.1")


    //kakao
    implementation ("com.kakao.sdk:v2-user:2.20.0") // 카카오 로그인 API 모듈
    implementation ("com.kakao.sdk:v2-share:2.20.0") // 카카오톡 공유 API 모듈




    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}