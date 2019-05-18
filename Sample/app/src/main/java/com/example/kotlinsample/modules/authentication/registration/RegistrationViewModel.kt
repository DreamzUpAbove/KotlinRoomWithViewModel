/*
 *
 */

package com.example.kotlinsample.modules.authentication.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinsample.database.User
import com.example.kotlinsample.database.UserDataRepository
import com.example.kotlinsample.database.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * LoginViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 */
class RegistrationViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: UserDataRepository


    init {
        val userDao = UserDatabase.getDatabase(application, viewModelScope).userDao()
        repository = UserDataRepository(userDao)
    }

    /**
     * Request a data to display a string.
     *
     * This variable is private because we don't want to expose MutableLiveData
     *
     * MutableLiveData allows anyone to set a value, and LoginViewModel is the only
     * class that should be handling data values in Login page.
     */

    private val _status = MutableLiveData<Boolean>()
    /**
     * Login status of user
     */
    val actionStatus: MutableLiveData<Boolean>
        get() = _status


    /**
     * User triggers Login action
     */
    fun onRegistrationAction(name: String, email: String, password: String, confirmPassword: String) {
        addUserCredentials(email,name,password)

    }
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    private fun addUserCredentials(id: String, name: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        val status = repository.insert(User(id,name,password))
          _status.postValue(status>0)
    }

}

