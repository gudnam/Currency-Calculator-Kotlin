package com.magulab.test.common

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter


fun <T> Activity.moveTo(cls: Class<T>) {
    startActivity(Intent(this, cls))
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun Long.convertTimeStampToDateTime(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val output = formatter.format(parser.parse(DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(this))))
    return "$output"
}