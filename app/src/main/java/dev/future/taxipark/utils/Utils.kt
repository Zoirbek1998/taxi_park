package dev.future.taxipark.utils

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.OpenableColumns
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

import dev.future.taxipark.R
import dev.future.taxipark.base.BaseActivity
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.ui.splash.SplashActivity
import dev.future.taxipark.utils.maskeditText.MaskedEditText
import dev.future.taxipark.utils.sharedPref.SaveTextSize

const val STARTING_PAGE = 1
const val LOAD_SIZE = 20
const val PREF_NAME = "last_query"
var token = ""

fun View.gone() {
    this.visibility = View.GONE
}


private fun getUriList(intent: Intent?): ArrayList<Uri?> {
    val listUri = ArrayList<Uri?>()
    if (intent?.data == null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val clipData = intent?.clipData
            (0 until clipData?.itemCount!!)
                .map { clipData.getItemAt(it) }
                .mapTo(listUri) { it.uri }
        } else {
            listUri.add(intent?.data)
        }
    } else {
        listUri.add(intent.data)
    }
    return listUri
}
fun TextView.changeTextSize(size: Float) {
   // this.textSize = ((size) + SaveTextSize.getTextSize()).toFloat()

    this.setTextSize(TypedValue.COMPLEX_UNIT_SP,size+SaveTextSize.getTextSize());
}

fun TextView.changeTextSize1(size: Float) {

    this.setTextSize(TypedValue.COMPLEX_UNIT_SP,size+SaveTextSize.getTextSize());
  //  this.textSize = (((this.textSize) / 2.0) + SaveTextSize.getTextSize()).toFloat()
}

fun MaterialButton.changeButtonSize(size:Float) {

    this.setTextSize(TypedValue.COMPLEX_UNIT_SP,size+SaveTextSize.getTextSize());
}

fun Button.changeButtonSize() {
    this.setTextSize(TypedValue.COMPLEX_UNIT_SP,(this.textSize/2)+SaveTextSize.getTextSize());
}

fun EditText.changeEditTextSize(size:Float) {

    this.setTextSize(TypedValue.COMPLEX_UNIT_SP,size+SaveTextSize.getTextSize());
}

fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun MaskedEditText.replace(): String {
    return this.text.toString().replace("-", " ")
}

fun List<String?>.getErrorText(): String {
    return if (this[0] == null) {
        ""
    } else {
        this[0].toString()
    }
}


fun Context.toast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun String.getName(): String {
    var name = this.toString()
    var result = ""
    for (i in name.indices) {
        result += name[i].toString()
        if (name[i] == ' ') {
            break
        }
    }

    return result
}

fun Int.toNumberFormatString(): String {
    var result = ""
    var n = 0
    this.toString().reversed().forEach { char ->
        if (n % 3 == 0 && n != 0)
            result = " $result"
        result = char + result
        n++
    }
    return result
}

fun Context.hasPermission(permission: String): Boolean {

    // Background permissions didn't exit prior to Q, so it's approved by default.
    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q
    ) {
        return true
    }
    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}


fun Fragment.requestPermissionWithRationale(
    permission: String,
    requestCode: Int,
    snackbar: Snackbar
) {
    val provideRationale = shouldShowRequestPermissionRationale(permission)

    if (provideRationale) {
        snackbar.show()
    } else {
        requestPermissions(arrayOf(permission), requestCode)
    }
}

fun Context.LoadingShow() {
    var activity = this as SplashActivity
    activity.progressShow()
}

fun Context.LoadingGone() {
    var activity = this as SplashActivity
    activity.progressGone()
}

fun Context.longToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

interface BackPress {
    fun onBackPress()
}

var oldLocationList: ArrayList<Location> = ArrayList()

var runStartTimeInMillis: Long = SystemClock.elapsedRealtimeNanos() / 1000000
var currentSpeed = 0F


fun TextView.goneIfTextEmpty() {
    visibility = if (text.isNullOrEmpty()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}


fun changeColorBackGround(
    layout1: LinearLayoutCompat,

    layout3: LinearLayoutCompat
) {
    layout1.changeGreenBackground()

    layout3.changeGreyBackground()
}

fun changeTextColor(text1: TextView, text3: TextView) {
    text1.changeWhiteColor()

    text3.changeWhiteColor()
}

fun TextView.changeWhiteColor() {
    this.setTextColor(ContextCompat.getColor(context, R.color.white))
}

fun TextView.changeBlackColor() {

    this.setTextColor(ContextCompat.getColor(context, R.color.black))
}

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
     var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
fun LinearLayoutCompat.changeGreyBackground() {
    this.background = ContextCompat.getDrawable(context, R.drawable.language_background)
}

fun LinearLayoutCompat.changeGreenBackground() {
    this.background = ContextCompat.getDrawable(context, R.drawable.language_flat_selector)
}

fun changeLanguage(prefs: PrefsHelper, activity: Activity, baseActivity: BaseActivity<*, *>) {
    var lvRusskiy = activity.findViewById<LinearLayoutCompat>(R.id.lvRusskiy)

    var lvUzbekLotin = activity.findViewById<LinearLayoutCompat>(R.id.lvUzbekLotin)
    var tvRusskiy = activity.findViewById<TextView>(R.id.tvRusskiy)

    var tvUzbekLotin = activity.findViewById<TextView>(R.id.tvUzbekLotin)

    if (prefs.language == "ru") {
        changeColorBackGround(lvRusskiy, lvUzbekLotin)
        changeTextColor(tvRusskiy, tvUzbekLotin)
    } else if (prefs.language == "uz") {
        changeColorBackGround(lvUzbekLotin, lvRusskiy)
        changeTextColor(tvUzbekLotin, tvRusskiy)
    } else

        lvRusskiy.setOnClickListener {
            changeColorBackGround(lvRusskiy, lvUzbekLotin)
            changeTextColor(tvRusskiy, tvUzbekLotin)
            if (prefs.language != "ru") {
                baseActivity.setLanguage("ru")
                activity.recreate()
            }
        }

    lvUzbekLotin.setOnClickListener {
        changeColorBackGround(lvUzbekLotin, lvRusskiy)
        changeTextColor(tvUzbekLotin, tvRusskiy)

        if (prefs.language != "uz") {
            baseActivity.setLanguage("uz")
            // baseActivity.recreate()

        }
    }
}

fun Int.pxToDp(): Float {
    return (this / Resources.getSystem().displayMetrics.density) as Float
}

fun Int.dpToPx(): Float {
    return (this * Resources.getSystem().displayMetrics.density) as Float
}

fun SharedPreferences.put(key: String, value: String?) {
    edit().putString(key, value).apply()
}

fun SharedPreferences.put(key: String, value: Int) {
    edit().putInt(key, value).apply()
}

fun SharedPreferences.put(key: String, value: Long) {
    edit().putLong(key, value).apply()
}

fun SharedPreferences.put(key: String, value: Boolean) {
    edit().putBoolean(key, value).apply()
}



