package tw.khliu.guess

import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MaterialActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MaterialActivity>(MaterialActivity::class.java)

    @Test
    fun guessWrong() {
        var n = 0
        val secret = activityTestRule.activity.secretNumber.secret
        val resources = activityTestRule.activity.resources
        for (n in 1..10) {
            if (n != secret) {
                onView(withId(R.id.et_number)).perform(clearText())
                onView(withId(R.id.et_number)).perform(typeText(n.toString()))
                onView(withId(R.id.btn_ok)).perform(click())
                val message: String = if (n < secret) resources.getString(R.string.bigger)
                else resources.getString(R.string.smaller)
                onView(withText(message)).check(matches(isDisplayed()))
                onView(withText(resources.getString(R.string.ok))).perform(click())
            }
        }
    }

    @Test
    fun replayResetCount() {
        var n = 0
        val sn = activityTestRule.activity.secretNumber
        val resources = activityTestRule.activity.resources
        for (n in 1..10) {
            if (n != sn.secret) {
                onView(withId(R.id.et_number)).perform(clearText())
                onView(withId(R.id.et_number)).perform(typeText(n.toString()))
                onView(withId(R.id.btn_ok)).perform(click())
                onView(withText(resources.getString(R.string.ok))).perform(click())
                if(sn.count>0) {
                    onView(withId(R.id.et_number)).perform(closeSoftKeyboard())
                    onView(withId(R.id.fab)).perform(click())
                    onView(withText(R.string.ok)).perform(click())
                    onView(withId(R.id.tv_count)).check(matches(withText("0")))
                    return
                }
            }
        }
    }
}