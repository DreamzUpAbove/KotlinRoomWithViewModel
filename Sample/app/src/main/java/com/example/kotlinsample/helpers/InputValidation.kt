package com.example.kotlinsample.helpers
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
class InputValidation {

    /**
     * method to check Input is empty.
     *  @param input
     *  @return boolean
     */
    fun isInputFilled(input: String): Boolean {
        return input.isNotEmpty()
    }


    /**
     * method to check Input has valid email .
     *
     * @param userName
     * @return boolean
     */
    fun isInputEditTextEmail( userName: String): Boolean {
        return  ( android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches())
    }

    /**
     * method to check password fields matches or not .
     *
     * @param password
     * @param passwprdConfirmation
     * @return boolean
     */
    fun isInputEditTextMatches(password: String, passwordConfirmation: String): Boolean {
        return password.contentEquals(passwordConfirmation)
    }


}
