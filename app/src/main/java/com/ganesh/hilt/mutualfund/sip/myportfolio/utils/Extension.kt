package com.ganesh.hilt.mutualfund.sip.myportfolio.utils

import android.app.ActivityManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ganesh.hilt.mutualfund.sip.myportfolio.MyApplication.Companion.myApplication
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.pow


fun View.hideKeyboard() {
    val imm = myApplication.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)

    clearFocus()
}

fun Long.formatDate(): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) // 12-hour format with AM/PM
    return sdf.format(Date(this))
}

fun Double.formatIndianCurrency(): String {
    val formatter = NumberFormat.getNumberInstance(Locale("en", "IN")) as DecimalFormat
    return "₹ ${formatter.format(this)}"
}

fun Double.keep2DecimalPoints(): Double {
    return BigDecimal(this).setScale(
        2, RoundingMode.HALF_EVEN
    ).toDouble()
}

fun calculateInterest(
    principal: Double, rate: Double, time: Double, isCompound: Boolean = false
): Double {
    return if (isCompound) {
        principal * (1 + (rate / 100)).pow(time) - principal
    } else {
        (principal * rate * time) / 100
    }
}

fun Class<*>.isMyServiceRunning(context: Context): Boolean {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
    for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
        if (name == service.service.className) {
            return true
        }
    }
    return false
}