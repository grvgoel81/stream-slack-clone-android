plugins {
  id(BuildPlugins.ANDROID_LIBRARY_PLUGIN)
  id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
  id(BuildPlugins.KOTLIN_KSP)
  id(BuildPlugins.DAGGER_HILT)
  id(BuildPlugins.COMPOSE_COMPILER)
  id(BuildPlugins.KOTLIN_PARCELABLE_PLUGIN)
}

android {
  compileSdk = ProjectProperties.COMPILE_SDK
  namespace = "io.getstream.slackclone.uionboarding"

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
    resources.excludes.add("META-INF/LICENSE.txt")
    resources.excludes.add("META-INF/NOTICE.txt")
    resources.excludes.add("LICENSE.txt")
    resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
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
  implementation(project(":data"))
  implementation(project(":domain"))
  implementation(project(":common"))
  implementation(project(":navigator"))
  implementation(project(":commonui"))

  api(Lib.Android.COMPOSE_UI)
  api(Lib.Android.LANDSCAPIST_GLIDE)
  api(Lib.Android.COMPOSE_MATERIAL)
  implementation(Lib.Android.ACCOMPANIST_SYSTEM_UI_CONTROLLER)
  api(Lib.Android.COMPOSE_UI)
  implementation(Lib.Android.COMPOSE_TOOLING)
  debugImplementation(Lib.Android.COMPOSE_DEBUG_TOOLING)
  api(Lib.Android.ACTIVITY_COMPOSE)
  api(Lib.Android.CONSTRAINT_LAYOUT_COMPOSE)

  api(Lib.Android.APP_COMPAT)
  api(Lib.Kotlin.KTX_CORE)

  /*Stream Chat SDK*/
  api(Lib.STREAM.STREAM_CHAT_CLIENT)

  /*DI*/
  api(Lib.Di.hiltAndroid)
  api(Lib.Di.hiltNavigationCompose)

  ksp(Lib.Di.hiltCompiler)
  ksp(Lib.Di.hiltAndroidCompiler)

  /* Logger */
  api(Lib.Logger.TIMBER)
  /* Async */
  api(Lib.Async.COROUTINES)
  api(Lib.Async.COROUTINES_ANDROID)

  testImplementation(TestLib.JUNIT)
  testImplementation(TestLib.CORE_TEST)
  testImplementation(TestLib.ANDROID_JUNIT)
  testImplementation(TestLib.ARCH_CORE)
  testImplementation(TestLib.MOCK_WEB_SERVER)
  testImplementation(TestLib.ROBO_ELECTRIC)
  testImplementation(TestLib.COROUTINES)
  testImplementation(TestLib.MOCKK)
  testImplementation(TestLib.TURBINE)
}