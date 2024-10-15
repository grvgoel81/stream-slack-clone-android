// Manifest version information!

plugins {
  id(BuildPlugins.ANDROID_APPLICATION_PLUGIN)
  id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
  id(BuildPlugins.KOTLIN_PARCELABLE_PLUGIN)
  id(BuildPlugins.KOTLIN_KSP)
  id(BuildPlugins.COMPOSE_COMPILER)
  id(BuildPlugins.DAGGER_HILT)
}

android {
  compileSdk = (ProjectProperties.COMPILE_SDK)
  namespace = "io.getstream.slackclone"

  defaultConfig {
    applicationId = (ProjectProperties.APPLICATION_ID)
    minSdk = (ProjectProperties.MIN_SDK)
    targetSdk = (ProjectProperties.TARGET_SDK)
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    vectorDrawables.useSupportLibrary = true
  }

  buildTypes {
    val debug by getting {
      isDebuggable = true
      versionNameSuffix = "-debug"
      applicationIdSuffix = ".debug"
    }

    val release by getting {
      isDebuggable = false
      versionNameSuffix = "-release"
      isMinifyEnabled = true
      isShrinkResources = true

      proguardFiles(
        getDefaultProguardFile("proguard-android.txt"), "proguard-common.txt",
        "proguard-specific.txt"
      )
    }

    val benchmark by creating {
      initWith(release)
      signingConfig = signingConfigs.getByName("debug")
      matchingFallbacks.add("release")
      proguardFiles("benchmark-rules.pro")
    }
  }

  lint {
    abortOnError = false
  }

  buildFeatures {
    compose = true
  }

  packagingOptions {
    resources.excludes.add("META-INF/LICENSE.txt")
    resources.excludes.add("META-INF/NOTICE.txt")
    resources.excludes.add("LICENSE.txt")
    resources.excludes.add( "/META-INF/{AL2.0,LGPL2.1}")
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  api(project(":feat-onboarding"))
  api(project(":ui-dashboard"))
  api(project(":feat-chat"))

  implementation(project(":navigator"))
  implementation(project(":data"))
  implementation(project(":domain"))
  implementation(project(":common"))
  implementation(project(":commonui"))

  /* Android Designing and layout */
  implementation(Lib.Android.COMPOSE_LIVEDATA)
  implementation(Lib.Android.COMPOSE_NAVIGATION)
  implementation(Lib.Kotlin.KT_STD)
  implementation(Lib.Android.MATERIAL_DESIGN)
  implementation(Lib.Android.CONSTRAINT_LAYOUT_COMPOSE)
  implementation(Lib.Android.SPLASH_SCREEN_API)

  implementation(Lib.Android.APP_COMPAT)

  implementation(Lib.Kotlin.KTX_CORE)

  /*DI*/
  implementation(Lib.Di.hiltAndroid)
  implementation(Lib.Di.hiltNavigationCompose)

  ksp(Lib.Di.hiltCompiler)
  ksp(Lib.Di.hiltAndroidCompiler)

  /* Logger */
  implementation(Lib.Logger.TIMBER)
  /* Async */
  implementation(Lib.Async.COROUTINES)
  implementation(Lib.Async.COROUTINES_ANDROID)

  /* Room */
  implementation(Lib.Room.roomRuntime)
  ksp(Lib.Room.roomCompiler)
  implementation(Lib.Room.roomKtx)
  implementation(Lib.Room.roomPaging)

  /*Testing*/
  testImplementation(TestLib.JUNIT)
  testImplementation(TestLib.CORE_TEST)
  testImplementation(TestLib.ANDROID_JUNIT)
  testImplementation(TestLib.ARCH_CORE)
  testImplementation(TestLib.MOCK_WEB_SERVER)
  testImplementation(TestLib.ROBO_ELECTRIC)
  testImplementation(TestLib.COROUTINES)
  testImplementation(TestLib.MOCKK)

  androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Lib.Android.COMPOSE_VERSION}")
  debugImplementation("androidx.compose.ui:ui-test-manifest:${Lib.Android.COMPOSE_VERSION}")
}
