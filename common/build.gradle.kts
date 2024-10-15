plugins {
  id(BuildPlugins.ANDROID_LIBRARY_PLUGIN)
  id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
  id(BuildPlugins.KOTLIN_KSP)
  id(BuildPlugins.DAGGER_HILT)
}

android {
  compileSdk = ProjectProperties.COMPILE_SDK
  namespace = "io.getstream.slackclone.common"

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
    buildConfig = true
  }
}

dependencies {
  /*Kotlin*/
  api(Lib.Kotlin.KT_STD)

  /*Logger*/
  implementation(Lib.Android.APP_STARTUP)
  api(Lib.Logger.TIMBER)

  /* Dependency Injection */
  api(Lib.Di.hiltAndroid)
  api(Lib.Di.hiltNavigationCompose)

  ksp(Lib.Di.hiltCompiler)
  kspTest(Lib.Di.hiltCompiler)
  ksp(Lib.Di.hiltAndroidCompiler)
  kspTest(Lib.Di.hiltAndroidCompiler)
}