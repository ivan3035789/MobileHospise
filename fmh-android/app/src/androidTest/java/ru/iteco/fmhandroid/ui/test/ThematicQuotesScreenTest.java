package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.Rand.random;
import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;
import ru.iteco.fmhandroid.ui.step.ThematicQuotesScreenStep;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class ThematicQuotesScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    MainScreenStep mainScreenStep = new MainScreenStep();
    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    ThematicQuotesScreenStep thematicQuotesScreenStep = new ThematicQuotesScreenStep();

    int position = random(0);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenStep.checkNameMainScreen();
        } catch (NoMatchingViewException e) {
            authorizationScreenStep.validLoginPassword(authInfo());
        } finally {
            mainScreenStep.pressingTheButtonInTheFormOfButterfly();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("The screen should have a name")
    @Description("В этом тест кейсе мы проверяем название экрана Love is all")
    public void theScreenShouldHaveName() {
        thematicQuotesScreenStep.checkingTheScreenName();
    }

    @Test
    @DisplayName("The description should open")
    @Description("В этом тест кейсе мы проверяем что при нажатии на кнопку ввиде галочки разворачивается описание цитаты")
    public void theDescriptionShouldOpen() {
        thematicQuotesScreenStep.checkingTheScreenName();
        thematicQuotesScreenStep.quoteSelection(position);

        String title = thematicQuotesScreenStep.title(position);
        ViewInteraction title2 = thematicQuotesScreenStep.title2(position);
        String description = thematicQuotesScreenStep.description(position);
        ViewInteraction description2 = thematicQuotesScreenStep.description2(position);

        thematicQuotesScreenStep.checkingTheText(title, title2, description, description2);

    }
}
