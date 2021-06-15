package com.samir.paypaycodechallenge

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.samir.paypaycodechallenge.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun buttonClickPerform() {
        onView(withId(R.id.btn_get_rate)).perform(setButtonVisibility(true))
        onView(withId(R.id.btn_get_rate)).perform(click())
    }

    private fun setButtonVisibility(value: Boolean): ViewAction {
        return object : ViewAction {

            override fun getConstraints(): org.hamcrest.Matcher<View> {
                return isAssignableFrom(Button::class.java)
            }

            override fun perform(uiController: UiController?, view: View) {
                view.visibility = if (value) View.VISIBLE else View.GONE
            }

            override fun getDescription(): String {
                return "Show / Hide View"
            }
        }
    }

    @Test
    fun checkIfUserCanEnterAmount() {
        onView(withId(R.id.etamount)).perform(setEditTextVisibility(true))
        onView(withId(R.id.etamount))
            .perform(typeText("10"), closeSoftKeyboard())
    }

    private fun setEditTextVisibility(value: Boolean): ViewAction {
        return object : ViewAction {

            override fun getConstraints(): org.hamcrest.Matcher<View> {
                return isAssignableFrom(EditText::class.java)
            }

            override fun perform(uiController: UiController?, view: View) {
                view.visibility = if (value) View.VISIBLE else View.GONE
            }

            override fun getDescription(): String {
                return "Show / Hide View"
            }
        }
    }

    @Test
    fun calculateRate() {
        Thread.sleep(5000)
        onView(withId(R.id.etamount)).perform(setEditTextVisibility(true))
        onView(withId(R.id.etamount))
            .perform(typeText("10"), closeSoftKeyboard())
        onView(withId(R.id.btn_get_rate)).perform(setButtonVisibility(true))
        onView(withId(R.id.btn_get_rate)).perform(click())
    }
}
