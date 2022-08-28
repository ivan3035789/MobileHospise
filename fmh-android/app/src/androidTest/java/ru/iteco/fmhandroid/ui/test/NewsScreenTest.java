package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.ControlPanelScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;
import ru.iteco.fmhandroid.ui.step.NewsScreenStep;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NewsScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    MainScreenStep mainScreenStep = new MainScreenStep();
    NewsScreenStep newsScreenStep = new NewsScreenStep();
    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    ControlPanelScreenStep controlPanelScreenStep = new ControlPanelScreenStep();

    int position = 0;

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenStep.checkNameMainScreen();
        } catch (NoMatchingViewException e) {
            authorizationScreenStep.validLoginPassword(authInfo());
        } finally {
            mainScreenStep.clickingOnTheActionMenuButton();
            mainScreenStep.clickingOnTheNewsName();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(5000);
    }

    @Test
    @DisplayName("The screen should have a name")
    @Description("В этом тест кейсе мы проверяем название экрана News")
    public void theScreenShouldHaveName() {
        newsScreenStep.checkTheNameOfTheNewsScreen();
    }

    @Test
    @DisplayName("News blocks should be swapped")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"стрелки вверх и вниз\" на странице  \"News\"" +
            " должны поменяться местами новостные блоки исходя из более поздней даты по возростанию (самая позняя дата " +
            "должна оказаться на самом верху  новостной ленты), на более раннюю (по убыванию, самая раняя дата должна" +
            " оказаться в самом низу новостной ленты) и наоборот ")
    public void newsBlocksShouldBeSwapped() {
        newsScreenStep.clickingOnTheButtonExpandTheNewsFeed(position);

        String firstNews = newsScreenStep.takeTheNameOfTheFirstNews(position);
        String firstNewsDescription = newsScreenStep.takeTheDescriptionOfTheFirstNewsBeforeSorting(position);

        newsScreenStep.clickingOnTheNewsSortingChangeButton();

        newsScreenStep.clickingOnTheButtonExpandTheNewsFeed(position);
        String lastNews = newsScreenStep.takeTheNameOfTheFirstNewsAfterSorting(position);
        String lastNewsDescription = newsScreenStep.takeTheDescriptionOfTheFirstNewsAfterTheFirstSorting(position);

        newsScreenStep.clickingOnTheNewsSortingChangeButton();

        newsScreenStep.clickingOnTheButtonExpandTheNewsFeed(position);
        String firstNewsAgain = newsScreenStep.takeTheNameOfTheFirstNewsAfterTwoSorts(position);
        String firstAgainNewsDescription = newsScreenStep.takeTheDescriptionOfTheFirstNewsAfterTwoSorts(position);

        newsScreenStep.reconciliationOfNewsTitlesAndDescriptionsAfterSorting(
                firstNews, firstNewsDescription, lastNews, lastNewsDescription, firstNewsAgain, firstAgainNewsDescription);
    }

    @Test
    @DisplayName("must go to the News Filter")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку три полоски с кружками пользователь попадает в Filter news")
    public void mustGoToTheNewsFilter() {
        newsScreenStep.clickingOnTheButtonToGoToFilterNews();
        newsScreenStep.checkingTheScreenNameFilterNews();
    }

    @Test
    @DisplayName("should go to the control panel")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку с иконкой блокнот с карандашом пользователь попадает в Control panel ")
    public void shouldGoToTheControlPanel() {
        newsScreenStep.clickingOnTheButtonToGoToTheControlPanel();
        controlPanelScreenStep.checkingTheNameOfTheControlPanelScreen();
    }

    @Test
    @DisplayName("a description should appear at the news")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"свернуть, развернуть\" в новостном блоке, разворачивается, сварачивается (появляется) описание новости")
    public void aDescriptionShouldAppearAtTheNews() {
        newsScreenStep.clickingOnTheExpandNewsDescriptionButton(position);
        newsScreenStep.checkingTheTextOfTheNewsDescriptionIsVisible(position);
    }
}
