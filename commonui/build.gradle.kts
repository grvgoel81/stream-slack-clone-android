plugins {
  id(BuildPlugins.ANDROID_LIBRARY_PLUGIN)
  id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
  id(BuildPlugins.COMPOSE_COMPILER)
  id(BuildPlugins.KOTLIN_KSP)
}

android {
  compileSdk = ProjectProperties.COMPILE_SDK
  namespace = "io.getstream.slackclone.commonui"

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
  buildFeatures {
    compose = true
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  /*Kotlin*/
  api(Lib.Kotlin.KT_STD)
  api(Lib.Kotlin.KTX_CORE)
  /* Android Designing and layout */
  api(Lib.Android.MATERIAL_DESIGN)
  api(Lib.Android.COMPOSE_UI)
  implementation(Lib.Android.CONSTRAINT_LAYOUT_COMPOSE)
  implementation(Lib.Android.ACCOMPANIST_SYSTEM_UI_CONTROLLER)
  api(Lib.Android.LANDSCAPIST_GLIDE)
  api(Lib.Android.LANDSCAPIST_PLACEHOLDER)
  api(Lib.Android.COMPOSE_MATERIAL)
  api(Lib.Android.COMPOSE_TOOLING)
  debugApi(Lib.Android.COMPOSE_DEBUG_TOOLING)
  api(Lib.Android.ACTIVITY_COMPOSE)
  api(Lib.Android.COMPOSE_ICON)

  /* Dependency Injection */
  api(Lib.Di.hiltAndroid)
  ksp(Lib.Di.hiltAndroidCompiler)
}