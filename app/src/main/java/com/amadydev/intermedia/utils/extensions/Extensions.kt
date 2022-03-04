package com.amadydev.intermedia.utils.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern

fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

// Check email
fun String.isEmailValid() =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()

// Check password
fun String.isPasswordValid() =
    Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=\\S+\$).{8,}")
        .matcher(this).matches()

fun String.isSamePassword(confirmPass: String) =
    this == confirmPass

// Disable Scroll vertical
fun appearancesLinearLayoutManager(context: Context) = object : LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean = false
}

fun String.toYear() = this.substringBefore("-")

