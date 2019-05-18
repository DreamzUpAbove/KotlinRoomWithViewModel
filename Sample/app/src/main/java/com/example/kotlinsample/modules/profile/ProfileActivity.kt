package com.example.kotlinsample.modules.profile
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
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinsample.R
import com.example.kotlinsample.database.User

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val userNameView = findViewById<View>(R.id.text_name) as TextView
        val emailView = findViewById<View>(R.id.text_email) as TextView
        val bundle: Bundle? = intent.extras
        bundle?.let {

            bundle.apply {
                //Intent with data
                val userDetails: User = intent.getParcelableExtra("userData") as User
                if (userDetails != null) {
                    userNameView.text=userDetails.name
                    emailView.text=userDetails.email
                }
            }

        }
    }
}
