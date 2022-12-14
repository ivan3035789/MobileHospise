package ru.iteco.fmhandroid.ui.test;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.step.ApplicationLoadingScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class ApplicationLoadingScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    ApplicationLoadingScreenStep applicationLoadingScreenStep = new ApplicationLoadingScreenStep();

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("The picture should be displayed")
    @Description("В этом тест кейсе мы проверяем, что  во время загрузки приложения появляется картинка (картинки каждый раз при загрузке меняются)")
    public void thePictureShouldBeDisplayed() {
        applicationLoadingScreenStep.checkSplashscreenImageView();
    }

    @Test
    @DisplayName("The text should be displayed")
    @Description("В этом тест кейсе мы проверяем, что  во время загрузки приложения появляется  цитата (цитаты при входе каждый раз меняются)")
    public void theTextShouldBeDisplayed() {
        applicationLoadingScreenStep.checkSplashscreenTextView();
    }

    @Test
    @DisplayName("The download icon should be displayed")
    @Description("В этом тест кейсе мы проверяем, что  во время загрузки приложения появляется индекатор загрузки")
    public void theDownloadIconShouldBeDisplayed() throws InterruptedException {
        try {
            applicationLoadingScreenStep.checkProgressIndicator();
        } catch (NoMatchingViewException ignore) {
            SystemClock.sleep(1000);
        } finally {
            applicationLoadingScreenStep.checkProgressIndicator();
        }
    }
}
