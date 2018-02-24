package com.kondratyonok.kondratyonok;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kondratyonok.kondratyonok.greeting.GreetingActivity;
import com.kondratyonok.kondratyonok.greeting.fragment.GreetingFragment;
import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.kondratyonok.kondratyonok.settings.Theme;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Created by NKondratyonok on 24.02.18.
 */
@RunWith(AndroidJUnit4.class)
public class UIEspressoTest {
    private final Context context = InstrumentationRegistry.getTargetContext();
    @Rule
    public ActivityTestRule<GreetingActivity> activityRule = new ActivityTestRule<>(GreetingActivity.class);

    @Test
    public void GreetingActivityContainerExistsTest() {
        onView(withId(R.id.container)).check(matches(isDisplayed()));
    }

    @Test
    public void GreetingFragmentViewTest() {
        onView(withId(R.id.my_photo)).check(matches(isDisplayed()));
        onView(withId(R.id.app_name)).check(matches(isDisplayed()));
    }

    @Test
    public void DescriptionFragmentViewTest() {
        onView(withId(R.id.container)).perform(swipeLeft());
        onView(withId(R.id.icon)).check(matches(isDisplayed()));
    }

    @Test
    public void ThemeFragmentViewTest() {
        onView(withId(R.id.container)).perform(swipeLeft()).perform(swipeLeft());
        onView(withId(R.id.light_theme)).check(matches(isDisplayed()));
        onView(withId(R.id.dark_theme)).check(matches(isDisplayed()));
        onView(withId(R.id.light_radio_button)).check(matches(isDisplayed()));
        onView(withId(R.id.dark_radio_button)).check(matches(isDisplayed()));
    }

    @Test
    public void LayoutFragmentViewTest() {
        onView(withId(R.id.container)).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft());
        onView(withId(R.id.standard_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.dense_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.standard_radio_button)).check(matches(isDisplayed()));
        onView(withId(R.id.dense_radio_button)).check(matches(isDisplayed()));
    }

    @Test
    public void AgreeFragmentViewTest() {
        onView(withId(R.id.container)).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft());
        onView(withId(R.id.icon)).check(matches(isDisplayed()));
        onView(withId(R.id.app_name)).check(matches(isDisplayed()));
        onView(withId(R.id.greeting)).check(matches(isDisplayed()));
        onView(withId(R.id.start)).check(matches(isDisplayed()));
    }

    @Test
    public void ThemeChooserTest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(SettingsActivity.KEY_THEME).commit();

        onView(withId(R.id.container)).perform(swipeLeft()).perform(swipeLeft());
        assertEquals(false, preferences.contains(SettingsActivity.KEY_THEME));

        onView(withId(R.id.container)).perform(swipeLeft());
        assertEquals(false, preferences.contains(SettingsActivity.KEY_THEME));
        onView(withId(R.id.container)).perform(swipeRight());

        onView(withId(R.id.light_theme)).perform(click());
        assertEquals(true, preferences.contains(SettingsActivity.KEY_THEME));
        assertEquals(Theme.LIGHT, preferences.getString(SettingsActivity.KEY_THEME, "default"));

        onView(withId(R.id.dark_theme)).perform(click());
        assertEquals(true, preferences.contains(SettingsActivity.KEY_THEME));
        assertEquals(Theme.DARK, preferences.getString(SettingsActivity.KEY_THEME, "default"));

        preferences.edit().remove(SettingsActivity.KEY_THEME).commit();
    }

    @Test
    public void LayoutChooserTest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(SettingsActivity.KEY_LAYOUT).commit();

        onView(withId(R.id.container)).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft());
        assertEquals(false, preferences.contains(SettingsActivity.KEY_LAYOUT));

        onView(withId(R.id.container)).perform(swipeLeft());
        assertEquals(false, preferences.contains(SettingsActivity.KEY_LAYOUT));
        onView(withId(R.id.container)).perform(swipeRight());

        onView(withId(R.id.standard_layout)).perform(click());
        assertEquals(true, preferences.contains(SettingsActivity.KEY_LAYOUT));
        assertEquals(Layout.STANDARD, preferences.getString(SettingsActivity.KEY_LAYOUT, "default"));

        onView(withId(R.id.dense_layout)).perform(click());
        assertEquals(true, preferences.contains(SettingsActivity.KEY_LAYOUT));
        assertEquals(Layout.DENSE, preferences.getString(SettingsActivity.KEY_LAYOUT, "default"));

        preferences.edit().remove(SettingsActivity.KEY_LAYOUT).commit();
    }

    @Test
    public void NoSettingChosenChooserTest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().clear().commit();

        onView(withId(R.id.container)).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft());
        assertEquals(false, preferences.contains(SettingsActivity.KEY_THEME));
        assertEquals(false, preferences.contains(SettingsActivity.KEY_LAYOUT));

        onView(withId(R.id.start)).perform(click());
        assertEquals(true, preferences.contains(SettingsActivity.KEY_THEME));
        assertEquals(true, preferences.contains(SettingsActivity.KEY_LAYOUT));

        assertEquals(Theme.DEFAULT, preferences.getString(SettingsActivity.KEY_THEME, "default"));
        assertEquals(Layout.DEFAULT, preferences.getString(SettingsActivity.KEY_LAYOUT, "default"));
    }

    @Test
    public void StartLauncherTest() {
        onView(withId(R.id.container)).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft());
        onView(withId(R.id.start)).perform(click());
        onView(withId(R.id.container)).check(matches(isDisplayed()));
    }
}
