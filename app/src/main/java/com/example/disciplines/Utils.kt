package com.example.disciplines

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import kotlin.math.roundToInt

fun Toast.applyGravity(gravity: Int, xOffset: Int, yOffset: Int) =
    apply { setGravity(gravity, xOffset, yOffset) }

@RequiresApi(Build.VERSION_CODES.P)
fun spanWithBullet(
    str: String,
    resources: Resources,
    bulletSizeRatio: Float = 0.5f,
    gapWidth: Int = 20,
    @ColorInt
    bulletColor: Int = Color.BLACK
): SpannableString {

    val textSize = resources.getDimension(R.dimen.default_text_size)
    val bulletRadius =
        (textSize / resources.displayMetrics.scaledDensity * bulletSizeRatio + 0.5f).roundToInt()
    return spanWithBullet(str, bulletRadius, gapWidth, bulletColor)
}

@RequiresApi(Build.VERSION_CODES.P)
fun spanWithBullet(
    str: String,
    bulletRadius: Int,
    gapWidth: Int,
    @ColorInt
    bulletColor: Int,
): SpannableString {
    val s = SpannableString(str)
    s.setSpan(
        BulletSpan(
            gapWidth,
            bulletColor,
            bulletRadius
        ),
        0,
        s.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return s
}