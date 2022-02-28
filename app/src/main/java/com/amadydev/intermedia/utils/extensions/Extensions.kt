package com.amadydev.intermedia.utils.extensions

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
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
fun isEmailValid(email: String) =
    Patterns.EMAIL_ADDRESS.matcher(email).matches()

// Check password
fun isPasswordValid(password: String) =
    Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=\\S+\$).{8,}")
        .matcher(password).matches()

