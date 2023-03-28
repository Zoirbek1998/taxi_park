package dev.future.taxipark.utils
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Spinner
import dev.future.taxipark.utils.maskeditText.MaskedEditText

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString().trim())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}


fun EditText.validate(message: String, validator: (String) -> Boolean) : Boolean{
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.getString())) null else message
    return validator(this.getString())
}


fun EditText.getString(): String{
    return this.text.toString()
}


fun EditText.getStringTrim(): String{
    return this.getString().trim()
}

fun MaskedEditText.shortText(char:String):String{
  return  this.text.toString().replace(char,"")
}

fun EditText.errorText(errorString:String){
    this.error = if (errorString.isEmpty()){
        this.error =null
        null} else errorString.toString()
}