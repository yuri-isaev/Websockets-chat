@file:Suppress("DEPRECATION", "UNUSED_EXPRESSION")

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-kapt")
	id("kotlin-android")
}

android {
	namespace = "com.isaev.chat"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.isaev.chat"
		minSdk = 24
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}

	kotlinOptions {
		jvmTarget = "1.8"
	}

	testOptions {
		animationsDisabled = true
	}

	packagingOptions {
		exclude("META-INF/LICENSE.md")
		exclude("META-INF/NOTICE.md")
	}

	packagingOptions {
		resources.excludes.add("META-INF/LICENSE-notice.md")
		resources.excludes.add("META-INF/LICENSE.md")
		resources.excludes.add("META-INF/LICENSE")
		resources.excludes.add("META-INF/NOTICE")
		resources.excludes.add("META-INF/NOTICE.txt")
	}

	buildFeatures {
		viewBinding = true
	}
}

dependencies {
	// Standard libs
	implementation ("androidx.core:core-ktx:1.13.1")
	implementation ("androidx.test.ext:junit-ktx:1.2.1")
	implementation ("androidx.appcompat:appcompat:1.7.0")
	implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation ("androidx.legacy:legacy-support-v4:1.0.0")
	implementation ("androidx.test:runner:1.5.2")
	implementation ("com.google.android.material:material:1.12.0")
	implementation ("org.jetbrains.kotlin:kotlin-reflect:1.8.0")

	// Lifecycle libs
	implementation ("androidx.lifecycle:lifecycle-viewmodel-android:2.8.1")
	implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")
	implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")

	// Firebase libs
	implementation ("com.google.firebase:firebase-messaging-ktx:24.0.0")

	// Unit testing dependencies
	testImplementation ("androidx.arch.core:core-testing:2.2.0")
	testImplementation ("junit:junit:4.13.2")
	testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

	// AndroidX Test - Instrumented testing
	androidTestImplementation ("androidx.test.ext:junit:1.1.1")
	androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation ("androidx.test:runner:1.5.2")
	androidTestImplementation ("androidx.test:rules:1.5.0")

	// Mockito
	androidTestImplementation ("org.mockito:mockito-android:3.3.3")
	testImplementation ("org.mockito:mockito-core:3.3.3")
	testImplementation ("org.mockito:mockito-inline:5.0.0")

	// REST API libs
	implementation ("com.squareup.retrofit2:retrofit:2.9.0")
	implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

	// Navigation fragments libs
	implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
	implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")

	// Coroutines Test
	testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")

	// AndroidX and Fragment Testing
	debugImplementation ("androidx.fragment:fragment-testing:1.4.0")
	implementation ("androidx.fragment:fragment-ktx:1.4.1")
	testImplementation ("androidx.test:core:1.4.0")

	// Testing
	testImplementation ("junit:junit:4.13.2")
	androidTestImplementation ("androidx.test.ext:junit:1.1.3")
	androidTestImplementation ("androidx.test:core-ktx:1.4.0")
	androidTestImplementation ("androidx.arch.core:core-testing:2.1.0")

	// Mockito for mocking in tests
	testImplementation ("org.mockito:mockito-core:4.0.0")
	androidTestImplementation ("org.mockito:mockito-android:4.0.0")

	// MockK for Kotlin
	testImplementation ("io.mockk:mockk:1.12.2")
	androidTestImplementation ("io.mockk:mockk-android:1.12.2")

	// For LiveData testing
	testImplementation ("androidx.arch.core:core-testing:2.1.0")
}