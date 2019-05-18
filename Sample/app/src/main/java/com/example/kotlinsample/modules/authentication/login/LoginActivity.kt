package com.example.kotlinsample.modules.authentication.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.core.extensions.VALIDATION_RULE_EMAIL_ADDRESS
import com.example.core.extensions.VALIDATION_RULE_PASSWORD_NOT_EMPTY
import com.example.core.extensions.validationRule
import com.example.kotlinsample.R
import com.example.kotlinsample.database.User
import com.example.kotlinsample.databinding.ActivityLoginBinding
import com.example.kotlinsample.module.authentication.login.LoginViewModel
import com.example.kotlinsample.modules.authentication.registration.RegistrationActivity
import com.example.kotlinsample.modules.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        // hiding the action bar
        supportActionBar?.hide()

        textInputLayoutEmail.validationRule(VALIDATION_RULE_EMAIL_ADDRESS)
        textInputLayoutPassword.validationRule(VALIDATION_RULE_PASSWORD_NOT_EMPTY)
        val viewModel = ViewModelProviders.of(this)
            .get(LoginViewModel::class.java)
        binding.loginModel = viewModel

        // When Login button is clicked call onLogin in ViewModel
        appCompatButtonLogin.setOnClickListener {

            if((TextUtils.isEmpty(textInputLayoutEmail.error)) && (TextUtils.isEmpty(textInputLayoutPassword.error)))
                viewModel.onLoginAction(
                    textInputEditTextEmail.text?.trim().toString(),
                    textInputEditTextPassword.text?.trim().toString()

                )
        }
        // When rootLayout is clicked call onMainViewClicked in ViewModel
        textViewLinkRegister.setOnClickListener {
            navigateToRegisterPage()
        }

        // update the error when the [LoginModel.username] changes
        viewModel.user.observe(this, Observer { value ->
            value?.let { user ->
                navigateToProfilePage(user)
            }
        })
        // update the error when the [LoginModel.username] changes
        viewModel.actionStatus.observe(this, Observer { value ->
            value?.let { status ->
                Toast.makeText(applicationContext, "Invalid Username or Password !", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun navigateToRegisterPage() {
        val intentRegister = Intent(applicationContext, RegistrationActivity::class.java)
        startActivity(intentRegister)
    }

    private fun navigateToProfilePage(user: User) {
        val intentToProfile = Intent(applicationContext, ProfileActivity::class.java)
        intentToProfile.putExtra("userData", user)
        startActivity(intentToProfile)
    }
}
