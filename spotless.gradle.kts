plugins {
  id("com.diffplug.spotless")
}

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*.kt")
    ktlint().userData(mapOf("android" to "true", "indent_size" to "2", "continuation_indent_size" to 2))
    trimTrailingWhitespace()
    endWithNewline()
  }

  format("kts") {
    target("**/*.kts")
    targetExclude("**/build/**/*.kts")
  }
}