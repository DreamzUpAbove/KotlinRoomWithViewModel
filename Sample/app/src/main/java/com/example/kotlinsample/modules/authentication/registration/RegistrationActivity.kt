package com.example.kotlinsample.modules.authentication.registration

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.core.extensions.VALIDATION_RULE_EMAIL_ADDRESS
import com.example.core.extensions.VALIDATION_RULE_PASSWORD_NOT_EMPTY
import com.example.core.extensions.VALIDATION_RULE_USERNAME_NOT_EMPTY
import com.example.core.extensions.validationRule
import com.example.kotlinsample.R
import com.example.kotlinsample.databinding.ActivityRegisterationBinding
import kotlinx.android.synthetic.main.activity_registeration.*

class RegistrationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        // hiding the action bar
        supportActionBar!!.hide()
        val binding: ActivityRegisterationBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_registeration)

        val viewModel = ViewModelProviders.of(this)
            .get(RegistrationViewModel::class.java)
        binding.registerModel = viewModel

        textInputLayoutName.validationRule(VALIDATION_RULE_USERNAME_NOT_EMPTY)
        textInputLayoutEmail.validationRule(VALIDATION_RULE_EMAIL_ADDRESS)
        textInputLayoutPassword.validationRule(VALIDATION_RULE_PASSWORD_NOT_EMPTY)
        textInputLayoutConfirmPassword.validationRule(VALIDATION_RULE_PASSWORD_NOT_EMPTY)

        appCompatButtonRegister.setOnClickListener {

            if ((TextUtils.isEmpty(textInputLayoutEmail.error)) && (TextUtils.isEmpty(textInputLayoutPassword.error)) && (TextUtils.isEmpty(
                    textInputLayoutConfirmPassword.error
                )) && (TextUtils.isEmpty(textInputLayoutName.error))
            ) {
                if (textInputEditTextPassword.text.toString().equals(textInputEditTextConfirmPassword.text.toString())) {
                    viewModel.onRegistrationAction(
                        textInputEditTextName.text?.trim().toString(),
                        textInputEditTextEmail.text?.trim().toString(),
                        textInputEditTextPassword.text?.trim().toString(),
                        textInputEditTextConfirmPassword.text?.trim().toString()
                    )
                }else{
                    Toast.makeText(applicationContext, "Password and confirm Password not matching !", Toast.LENGTH_SHORT).show()
                }
            }
        }
        appCompatTextViewLoginLink.setOnClickListener {
            finish()
        }

        // update the error when the [LoginModel.username] changes
        viewModel.actionStatus.observe(this, Observer { value ->
            value?.let { status ->
                if (status)
                    finish()
            }
        })
    }


}
