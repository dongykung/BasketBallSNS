plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies{
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    //paging3 - common(안드로이드 의존성 없음)
    implementation("androidx.paging:paging-common:3.2.1")
}
