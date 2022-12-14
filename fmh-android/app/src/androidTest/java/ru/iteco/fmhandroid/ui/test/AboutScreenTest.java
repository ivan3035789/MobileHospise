package ru.iteco.fmhandroid.ui.test;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.step.AboutScreenStep;
import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AboutScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    MainScreenStep mainScreenStep = new MainScreenStep();
    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    AboutScreenStep aboutScreenStep = new AboutScreenStep();

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenStep.checkNameMainScreen();
        } catch (NoMatchingViewException e) {
            authorizationScreenStep.validLoginPassword(Helper.authInfo());
        } finally {
            mainScreenStep.clickingOnTheActionMenuButton();
            mainScreenStep.clickingOnTheAboutName();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Should display information about the version of the application")
    @Description("В этом тест кейсе мы проверяем наличие информации на странице о текущей версии приложения")
    public void ShouldDisplayInformationAboutTheVersionOfTheApplication() {
        aboutScreenStep.checkScreenNameAbout();
    }

    @Test
    @DisplayName("Must follow the link in Privacy Policy")
    @Description("В этом тест кейсе мы проверяем переход пользователя на страницу по ссылке указанной в Privacy Policy (страница About)")
    public void mustFollowTheLinkInPrivacyPolicy() {
        aboutScreenStep.checkNamePrivacyPolicy();
        aboutScreenStep.checkNameLinkPrivacyPolicy();
        aboutScreenStep.checkNameLinkPrivacyPolicy();
        aboutScreenStep.checkNameLinkTermsOfUse();
    }

    @Test
    @DisplayName("Must follow the link in Terms of use")
    @Description("В этом тест кейсе мы проверяем переход пользователя на страницу по ссылке указанной в Terms of use  (страница About) ")
    public void mustFollowTheLinkInTermsOfUse() {
        aboutScreenStep.checkNameTermsOfUse();
        aboutScreenStep.checkNameLinkTermsOfUse();
        aboutScreenStep.checkingTheLinksClickabilityLinkTermsOfUse();
        aboutScreenStep.checkingTheLinksClickabilityLinkPrivacyPolicy();
    }

    @Test
    @DisplayName("must be the name of the manufacturer")
    @Description("В этом тест кейсе мы проверяем наличие информации на странице о производителе")
    public void mustBeTheNameOfTheManufacturer() {
        aboutScreenStep.checkingTheManufacturersName();
    }

    @Test
    @DisplayName("Should go back to the previous page")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку назад пользователь попадает на предъидущую страницу")
    public void shouldGoBackToThePreviousPage() {
        aboutScreenStep.clickAboutExitButton();
    }
}
