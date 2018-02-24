package com.kondratyonok.kondratyonok;

import com.kondratyonok.kondratyonok.utils.PackageUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {
    @Test
    public void getPackageFromString() {
        String packageName = "com.kondratyonok.kondratyonok";
        String packageUrl = "package:" + packageName;
        assertThat(PackageUtils.getPackageFromDataString(packageUrl), is(packageName));
    }
}