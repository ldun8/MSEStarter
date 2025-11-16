package com.example.msestarter

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import androidx.test.uiautomator.UiDevice
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UiAutomatorTest {

    private lateinit var device: UiDevice
    private val pkg = "com.example.msestarter"

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        // Go Home first
        device.pressHome()

        // Wait for the launcher
        val launcherPackage = device.launcherPackageName
        require(!launcherPackage.isNullOrEmpty()) { "Launcher package not found" }
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 5_000)

        // Launch the app from its launcher intent (clean task)
        val context: Context = ApplicationProvider.getApplicationContext()
        val intent = context.packageManager.getLaunchIntentForPackage(pkg)!!.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // clear out any previous instances
        }
        context.startActivity(intent)

        // Wait for app to appear
        device.wait(Until.hasObject(By.pkg(pkg).depth(0)), 5_000)
    }

    @Test
    fun startExplicit_and_verifyChallengeOnSecondActivity() {
        // Click the "Start Activity Explicitly" button on MainActivity
        val explicitBtnRes = "$pkg:id/btnExplicit"
        val explicitBtn = device.wait(Until.findObject(By.res(explicitBtnRes)), 5_000)
        assertTrue("Explicit button not found", explicitBtn != null)
        explicitBtn!!.click()

        // We’re now on SecondActivity (list of challenges). Verify one known challenge text.
        // Use partial match in case of formatting bullets.
        val possibleTexts = listOf(
            "Battery",              // "Battery & power constraints"
            "Performance",          // "Performance on limited hardware"
            "Network",              // "Network variability & offline handling"
            "Fragmentation",        // devices/OS versions
            "Security"              // "Security & privacy issues"
        )

        var found = false
        // Give the UI a moment and try to scroll if needed
        device.waitForIdle(2_000)
        repeat(3) {
            for (t in possibleTexts) {
                val node = device.findObject(By.textContains(t))
                if (node != null) {
                    found = true
                    return@repeat
                }
            }
            // Try to scroll a ScrollView if present
            device.findObject(By.scrollable(true))?.scroll(Direction.DOWN, 1.0f)
        }

        assertTrue("No expected challenge text found on SecondActivity", found)
    }
}
