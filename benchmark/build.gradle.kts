plugins {
  id(BuildPlugins.ANDROID_TEST_PLUGIN)
  id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

android {
  namespace = "io.getstream.slackclone.benchmark"
  compileSdk = ProjectProperties.COMPILE_SDK

  defaultConfig {
    minSdk = 23
    targetSdk = (ProjectProperties.TARGET_SDK)
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    // This benchmark buildType is used for benchmarking, and should function like your
    // release build (for example, with minification on). It's signed with a debug key
    // for easy local/CI testing.
    val benchmark by creating {
      isDebuggable = true
      signingConfig = signingConfigs.getByName("debug")
      matchingFallbacks.add("release")
    }
  }

  targetProjectPath = ":app"
  experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
  implementation(TestLib.ANDROIDX_TEST_RUNNER)
  implementation(TestLib.ANDROIDX_TEST_RULES)
  implementation(TestLib.CORE_TEST)
  implementation(TestLib.MACRO_BENCHMARK)
  implementation(TestLib.BASE_PROFILE)
  implementation(TestLib.BASE_PROFILE)
  implementation(TestLib.ANDROIDX_UI_AUTOMATOR)
}

androidComponents {
  beforeVariants {
    it.enable = it.buildType == "benchmark"
  }
}