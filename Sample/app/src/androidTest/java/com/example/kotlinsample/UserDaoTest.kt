package com.example.kotlinsample.db_tests

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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.roomwordssample.waitForValue
import com.example.kotlinsample.database.User
import com.example.kotlinsample.database.UserDao
import com.example.kotlinsample.database.UserDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() = runBlocking {
        val user = User("test@abc.com", "Test", "test123")
        userDao.insert(user)
        val retrievedUser = userDao.getUser(user.email, user.password)
        assertEquals(retrievedUser.email, user.email)
        assertEquals(retrievedUser.name, user.name)
        assertEquals(retrievedUser.password, user.password)
    }

    @Test
    @Throws(Exception::class)
    fun getAllUsers() = runBlocking {
        val user1 = User("unknown@abc.com", "Unknown", "forgotpassword")
        userDao.insert(user1)
        val user2 = User("anyone@abc.com", "Anyone", "idontknow")
        userDao.insert(user2)
        val allUsers = userDao.getAllUsers().waitForValue()

        assertEquals(allUsers[0].email, user1.email)
        assertEquals(allUsers[0].name, user1.name)
        assertEquals(allUsers[0].password, user1.password)

        assertEquals(allUsers[1].email, user2.email)
        assertEquals(allUsers[1].name, user2.name)
        assertEquals(allUsers[1].password, user2.password)

    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val user1 = User("unknown@abc.com", "Unknown", "forgotpassword")
        userDao.insert(user1)
        val user2 = User("anyone@abc.com", "Anyone", "idontknow")
        userDao.insert(user2)
        userDao.deleteAll()
        val allUsers = userDao.getAllUsers().waitForValue()
        assertTrue(allUsers.isEmpty())
    }


}
