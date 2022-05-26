package io.getstream.slackclone.common.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar

fun Long.calendar(): Calendar {
  return Calendar.getInstance().apply {
    this.timeInMillis = this@calendar
  }
}

@SuppressLint("SimpleDateFormat")
fun Calendar.formattedTime(): String {
  return SimpleDateFormat("hh:mm a").format(this.time)
}
