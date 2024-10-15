import Lib.Networking

/** This file contains versions of all the dependencies used in the module  */

object BuildPlugins {
  const val TOOLS_BUILD_GRADLE = "com.android.tools.build:gradle:8.6.1"
  const val DAGGER_HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:2.52"
  const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21"
  const val COMPOSE_GRADLE_PLUGIN = "org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.0.21"
  const val KOTLIN_KSP_GRADLE_PLUGIN = "com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.0.21-1.0.25"
  const val ANDROID_APPLICATION_PLUGIN = "com.android.application"
  const val ANDROID_LIBRARY_PLUGIN = "com.android.library"
  const val ANDROID_TEST_PLUGIN = "com.android.test"
  const val KOTLIN_ANDROID_PLUGIN = "kotlin-android"
  const val KOTLIN_PARCELABLE_PLUGIN = "kotlin-parcelize"
  const val KOTLIN_KSP = "com.google.devtools.ksp"
  const val DAGGER_HILT = "dagger.hilt.android.plugin"
  const val COMPOSE_COMPILER = "org.jetbrains.kotlin.plugin.compose"
  const val SPOTLESS = "com.diffplug.spotless:spotless-plugin-gradle:6.3.0"
}

object Lib {
  object Kotlin {
    const val KOTLIN_VERSION = "2.0.21"
    private const val KTX_CORE_VERSION = "1.2.0"
    const val KT_STD = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION"
    const val KTX_CORE = "androidx.core:core-ktx:$KTX_CORE_VERSION"
  }

  object Android {
    const val COMPOSE_VERSION = "1.7.1"
    private const val MATERIAL_DESIGN_VERSION = "1.4.0"
    private const val COMPOSE_ACTIVITY_VERSION = "1.4.0"
    private const val COMPOSE_CONSTRAINT_LAYOUT_VERSION = "1.0.0"
    private const val COMPOSE_NAVIGATION_VERSION = "2.8.2"
    private const val LANDSCAPIST_GLIDE_VERSION = "2.4.1"
    private const val ACCOMPANIST_VERSION = "0.36.0"
    private const val SPLASH_SCREEN_VERSION = "1.0.0-beta01"

    const val APP_COMPAT = "androidx.appcompat:appcompat:1.6.1"

    // Compose
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:$COMPOSE_ACTIVITY_VERSION"
    const val CONSTRAINT_LAYOUT_COMPOSE =
      "androidx.constraintlayout:constraintlayout-compose:$COMPOSE_CONSTRAINT_LAYOUT_VERSION"
    const val COMPOSE_UI = "androidx.compose.ui:ui:$COMPOSE_VERSION"
    const val ACCOMPANIST_SYSTEM_UI_CONTROLLER =
      "com.google.accompanist:accompanist-systemuicontroller:$ACCOMPANIST_VERSION"
    const val LANDSCAPIST_GLIDE = "com.github.skydoves:landscapist-glide:$LANDSCAPIST_GLIDE_VERSION"
    const val LANDSCAPIST_PLACEHOLDER = "com.github.skydoves:landscapist-placeholder:$LANDSCAPIST_GLIDE_VERSION"
    const val COMPOSE_LIVEDATA = "androidx.compose.runtime:runtime-livedata:$COMPOSE_VERSION"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:$COMPOSE_VERSION"
    const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation:$COMPOSE_VERSION"
    const val COMPOSE_TOOLING = "androidx.compose.ui:ui-tooling-preview:$COMPOSE_VERSION"
    const val COMPOSE_DEBUG_TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"
    const val COMPOSE_NAVIGATION =
      "androidx.navigation:navigation-compose:$COMPOSE_NAVIGATION_VERSION"
    const val COMPOSE_ICON =
      "androidx.compose.material:material-icons-extended:$COMPOSE_VERSION"
    const val MATERIAL_DESIGN = "com.google.android.material:material:$MATERIAL_DESIGN_VERSION"
    const val SPLASH_SCREEN_API = "androidx.core:core-splashscreen:$SPLASH_SCREEN_VERSION"

    // App startup
    const val APP_STARTUP = "androidx.startup:startup-runtime:1.1.1"
  }

  object Di {
    private const val DAGGER_VERSION = "2.52"
    const val hiltAndroid = "com.google.dagger:hilt-android:$DAGGER_VERSION"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$DAGGER_VERSION"

    const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
  }

  object Paging {
    private const val PAGING_VERSION = "3.1.0"
    const val PAGING_3 = "androidx.paging:paging-runtime:$PAGING_VERSION"
    const val PAGING_COMPOSE = "androidx.paging:paging-compose:1.0.0-alpha14"
  }

  object Room {
    private const val roomVersion = "2.5.0"
    const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    const val roomCompiler = "androidx.room:room-compiler:$roomVersion"

    // optional - Kotlin Extensions and Coroutines support for Room
    const val roomKtx = "androidx.room:room-ktx:$roomVersion"

    // optional - Paging 3 Integration
    const val roomPaging = "androidx.room:room-paging:2.4.1"
  }

  object Async {
    private const val COROUTINES_VERSION = "1.6.0"

    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION"
    const val COROUTINES_ANDROID =
      "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"
  }

  object Networking {
    private const val RETROFIT_VERSION = "2.9.0"
    const val OKHTTP_VERSION = "4.7.2"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"
    const val LOGGING = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"
  }

  object STREAM {
    private const val STREAM_CHAT_VERSION = "6.5.1"
    const val STREAM_CHAT_CLIENT = "io.getstream:stream-chat-android-client:$STREAM_CHAT_VERSION"
    const val STREAM_CHAT_OFFLINE = "io.getstream:stream-chat-android-offline:$STREAM_CHAT_VERSION"
    const val STREAM_CHAT_COMPOSE =
      "io.getstream:stream-chat-android-compose:$STREAM_CHAT_VERSION"
  }

  object Serialization {
    private const val GSON_VERSION = "2.8.8"
    const val GSON = "com.google.code.gson:gson:$GSON_VERSION"
  }

  object Logger {
    private const val TIMBER_VERSION = "4.7.1"
    const val TIMBER = "com.jakewharton.timber:timber:$TIMBER_VERSION"
  }
}

object TestLib {
  private const val ANDROIDX_TEST_VERSION = "1.4.0"
  private const val ANDROIDX_UI_AUTOMATOR_VERSION = "2.2.0"
  private const val COROUTINES_VERSION = "1.3.7"
  private const val ANDROID_JUNIT_VERSION = "1.0.0"
  private const val ROBO_ELECTRIC_VERSION = "4.3"
  private const val ARCH_CORE_VERSION = "2.1.0"
  private const val CORE_TEST_VERSION = "1.2.0"
  private const val JUNIT_VERSION = "4.13"
  private const val BASE_PROFILE_VERSION = "1.2.0-alpha02"
  private const val MACRO_BENCHMARK_VERSION = "1.1.0-rc01"

  const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:$ANDROIDX_TEST_VERSION"
  const val ANDROIDX_TEST_RULES = "androidx.test:rules:$ANDROIDX_TEST_VERSION"
  const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINES_VERSION"
  const val ROBO_ELECTRIC = "org.robolectric:robolectric:$ROBO_ELECTRIC_VERSION"
  const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:${Networking.OKHTTP_VERSION}"
  const val CORE_TEST = "androidx.test:core-ktx:$CORE_TEST_VERSION"
  const val JUNIT = "junit:junit:$JUNIT_VERSION"
  const val ANDROID_JUNIT = "androidx.test.ext:junit:$ANDROID_JUNIT_VERSION"
  const val ARCH_CORE = "androidx.arch.core:core-testing:$ARCH_CORE_VERSION"
  const val MOCKK = "io.mockk:mockk:1.10.5"
  const val TURBINE = "app.cash.turbine:turbine:0.7.0"
  const val BASE_PROFILE = "androidx.profileinstaller:profileinstaller:$BASE_PROFILE_VERSION"
  const val MACRO_BENCHMARK = "androidx.benchmark:benchmark-macro-junit4:$MACRO_BENCHMARK_VERSION"
  const val ANDROIDX_UI_AUTOMATOR = "androidx.test.uiautomator:uiautomator:$ANDROIDX_UI_AUTOMATOR_VERSION"
}
