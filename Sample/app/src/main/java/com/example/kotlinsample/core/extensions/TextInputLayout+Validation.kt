package com.example.core.extensions

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout


const val VALIDATION_RULE_EMAIL_ADDRESS = "emailValidation"
const val VALIDATION_RULE_PASSWORD_NOT_EMPTY = "PasswordEmpty"
const val VALIDATION_RULE_USERNAME_NOT_EMPTY = "UsernameEmpty"


@BindingAdapter("validationRule")
fun TextInputLayout.validationRule(emailAddress: String){

    when (emailAddress) {

        VALIDATION_RULE_PASSWORD_NOT_EMPTY -> {
            editText?.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    error = if(s!!.length<4) "Please enter valid password with minimum 4 characters !" else null
                }
            })
        }

        VALIDATION_RULE_USERNAME_NOT_EMPTY -> {
            editText?.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    error = if(s!!.length<3) "Please enter valid name !" else null
                }
            })
        }
    VALIDATION_RULE_EMAIL_ADDRESS -> {
            editText?.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    error = if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) "Please enter valid email-Id !" else null
                }
            })
        }
    }

}