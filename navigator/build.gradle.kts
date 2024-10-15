plugins {
  id(BuildPlugins.ANDROID_LIBRARY_PLUGIN)
  id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
  id(BuildPlugins.KOTLIN_KSP)
  id(BuildPlugins.COMPOSE_COMPILER)
}

android {
  compileSdk = ProjectProperties.COMPILE_SDK
  namespace = "io.getstream.slackclone.navigator"

  defaultConfig {
    minSdk = (ProjectProperties.MIN_SDK)
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
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
}

dependencies {
  /*Kotlin*/
  implementation(Lib.Android.APP_COMPAT)
  implementation(Lib.Kotlin.KTX_CORE)
  api(Lib.Async.COROUTINES)
  api(Lib.Async.COROUTINES_ANDROID)

  implementation(Lib.Kotlin.KT_STD)
  implementation(Lib.Android.COMPOSE_NAVIGATION)

  implementation(Lib.Android.COMPOSE_NAVIGATION)
  implementation(Lib.Di.hiltNavigationCompose)
}