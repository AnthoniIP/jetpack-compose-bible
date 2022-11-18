/**
 * To define plugins
 */
object BuildPlugins {
    val android by lazy { "com.android.application" }
    val androidGradle by lazy { "com.android.tools.build:gradle:${Versions.gradleTools}" }
    val kotlinGradle by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:$${Versions.kotlin}" }
    val kotlinXSerializationGradle by lazy { "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val googleServices by lazy { "com.google.gms:google-services:${Versions.googleServices}" }
    val firebaseCrashlytics by lazy {
        "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlytics}"
    }
    val playPublisher by lazy {
        "com.github.triplet.gradle:play-publisher:${Versions.playPuplisher}"
    }
    val firebaseAppDistribution by lazy {
        "com.google.firebase:firebase-appdistribution-gradle:${Versions.firebaseAppDistribution}"
    }
    val firebasePerf by lazy { "com.google.firebase:perf-plugin:${Versions.firebasePerf}" }
    val klintGradle by lazy { "org.jlleitschuh.gradle:ktlint-gradle:${Versions.klintGradle}" }
    val klint by lazy { "org.jlleitschuh.gradle.ktlint" }
    val hilt by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }
    val hiltPlugin by lazy { "com.google.dagger.hilt.android" }
    val kotlinAndroidGradle by lazy { "org.jetbrains.kotlin.android" }
    val kapt by lazy { "kapt" }
    val tripletPlay by lazy { "com.github.triplet.play" }
    val gmsGooglePlayServices by lazy { "com.google.gms.google-services" }
    val crashlytics by lazy { "com.google.firebase.crashlytics" }
    val kotlinAndroid by lazy { "kotlin-android" }
    val appDistribution by lazy { "com.google.firebase.appdistribution" }
    val daggerHilt by lazy { "com.google.dagger.hilt.android" }
    val firebasePerformance by lazy { "com.google.firebase.firebase-perf" }
    val kotlinXSerialization by lazy { "kotlinx-serialization" }
    val kotlinParcelize by lazy { "kotlin-parcelize" }
}

/**
 * To define dependencies
 */
object Deps {
    val workRuntime by lazy{ "androidx.work:work-runtime-ktx:${Versions.workRuntime}" }
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }
    val firebaseCrashlytics by lazy {
        "com.google.firebase:firebase-crashlytics:${Versions.firebaseCrashlytics}"
    }
    val firebasePerformance by lazy { "com.google.firebase:firebase-perf-ktx" }
    val kotlinSerialization by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltWorker by lazy { "androidx.hilt:hilt-work:${Versions.hiltWorker}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }
    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val converterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val loggingInterceptor by lazy {
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    }
    val lifecycleRuntime by lazy {
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    }
    val coroutinesCore by lazy {
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    }
    val coroutinesAndroid by lazy {
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }
    val coroutinesPlayservice by lazy {
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}"
    }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val sqliteJdbc by lazy { "org.xerial:sqlite-jdbc:${Versions.sqliteJdbc}" }
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigationCompose}" }
    val accompanist by lazy { "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}" }
    val composeRuntime by lazy { "androidx.compose.runtime:runtime-livedata:${Versions.compose}" }
    val composeUi by lazy { "androidx.compose.ui:ui:${Versions.compose}" }
    val composeMaterial by lazy { "androidx.compose.material:material:${Versions.compose}" }
    val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.compose}" }
    val composeUiTooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.compose}" }
    val composeUiTest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.compose}"}
    val dataStore by lazy {"androidx.datastore:datastore-preferences:${Versions.dataStore}" }
    val window by lazy { "androidx.window:window:${Versions.window}" }
}

object Test {
    val jUnit by lazy { "junit:junit:${Versions.jUnit}" }
    val testExt by lazy { "androidx.test.ext:junit:${Versions.testExt}" }
}
