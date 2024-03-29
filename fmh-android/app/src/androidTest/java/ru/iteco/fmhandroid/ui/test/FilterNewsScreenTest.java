package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.DateTime.generatorDate;
import static ru.iteco.fmhandroid.ui.data.Helper.DateTime.generatorDate2;
import static ru.iteco.fmhandroid.ui.data.Helper.DateTime.invalidGeneratorDate;
import static ru.iteco.fmhandroid.ui.data.Helper.DateTime.localDate;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomNews;
import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;
import static ru.iteco.fmhandroid.ui.data.Helper.createNewsForCategory;
import static ru.iteco.fmhandroid.ui.data.Helper.deletingNewsUpToTheNumberOfTenControlPanelScreen;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.ControlPanelScreenStep;
import ru.iteco.fmhandroid.ui.step.FilterNewsScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;
import ru.iteco.fmhandroid.ui.step.NewsScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class FilterNewsScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    MainScreenStep mainScreenStep = new MainScreenStep();
    NewsScreenStep newsScreenStep = new NewsScreenStep();
    ControlPanelScreenStep controlPanelScreenStep = new ControlPanelScreenStep();
    FilterNewsScreenStep filterNewsScreenStep = new FilterNewsScreenStep();
    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenStep.checkNameMainScreen();
        } catch (NoMatchingViewException e) {
            authorizationScreenStep.validLoginPassword(authInfo());
        } finally {
            deletingNewsUpToTheNumberOfTenControlPanelScreen(7);
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(5000);
    }

    @Test
    @DisplayName("The screen should have a name")
    @Description("В этом тест кейсе мы проверяем название экрана Filter News")
    public void theScreenShouldHaveName() {
        mainScreenStep.clickingOnTheActionMenuButton();
        mainScreenStep.clickingOnTheNewsName();
        newsScreenStep.clickingOnTheButtonToGoToFilterNews();
        filterNewsScreenStep.checkingTheScreenNameForNewsSearch();
    }

    @Test
    @DisplayName("the fields must have names")
    @Description("В этом тест кейсе мы проверяем названия полей")
    public void theFieldsMustHaveNames() {
        mainScreenStep.clickingOnTheActionMenuButton();
        mainScreenStep.clickingOnTheNewsName();
        newsScreenStep.clickingOnTheButtonToGoToFilterNews();
        filterNewsScreenStep.checkingIdentifyingFieldNames();
    }

    @Test
    @DisplayName("Must search for news in the list of news blocks using FilterNews according to the specified criteria in all fields")
    @Description("В этом тест кейсе мы проверяем поиск новости по всем заданным критериям в полях в разделе \"Filter news\"")
    public void mustSearchForNewsByCriteriaInAllFields() throws ParseException {
        String localDate = localDate();
        String dateStartInput = generatorDate2();
        String dateEndInput = generatorDate();
        String category = randomCategory();
        String text = Helper.Text.textSymbol(5);

        createNewsForCategory(text, category);

        mainScreenStep.clickingOnTheActionMenuButton();
        mainScreenStep.clickingOnTheNewsName();

        newsScreenStep.clickingOnTheButtonToGoToFilterNews();
        filterNewsScreenStep.checkingTheScreenNameForNewsSearch();

        filterNewsScreenStep.enteringSearchData(category, dateStartInput, dateEndInput);
        filterNewsScreenStep.clickingOnTheSearchButton();

        int positionNews = Helper.Search.searchNews(text);
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(positionNews);
        String dateOnCardNews = newsScreenStep.dateOnCardNews(positionNews);

        newsScreenStep.checkingTheFoundDataFromTheNewsWithTheDataEnteredForTheSearch(
                dateOnCardNews, dateStartInput, dateEndInput, category, localDate, positionNews);
    }

    @Test
    @DisplayName("Must search by Category")
    @Description("В этом тест кейсе мы проверяем поиск новости по категории")
    public void mustSearchByCategory() {
        int position = randomNews(0);
        String category = randomCategory();
        String text = Helper.Text.textSymbol(5);
        createNewsForCategory(text, category);

        mainScreenStep.clickingOnTheActionMenuButton();
        mainScreenStep.clickingOnTheNewsName();

        newsScreenStep.clickingOnTheButtonToGoToFilterNews();
        filterNewsScreenStep.checkingTheScreenNameForNewsSearch();

        filterNewsScreenStep.enteringCategory(category);
        filterNewsScreenStep.clickingOnTheSearchButton();

        String categoryText = newsScreenStep.categoryText(position);
        newsScreenStep.checkingTheDataOfTheFoundNewsWithTheEnteredSearchData(category, categoryText);
    }

    @Test
    @DisplayName("Must search by date")
    @Description("В этом тест кейсе мы проверяем поиск новости по дате")
    public void mustSearchByDate() throws ParseException {
        int position = randomNews(0, 1, 2);
        String localDate = localDate();
        String dateStartInput = generatorDate2();
        String dateEndInput = generatorDate();

        mainScreenStep.clickingOnTheActionMenuButton();
        mainScreenStep.clickingOnTheNewsName();
        newsScreenStep.clickingOnTheButtonToGoToFilterNews();
        filterNewsScreenStep.checkingTheScreenNameForNewsSearch();
        filterNewsScreenStep.enteringTheStartDate(dateStartInput);
        filterNewsScreenStep.enteringTheEndOfTheDate(dateEndInput);
        filterNewsScreenStep.clickingOnTheSearchButton();

        newsScreenStep.clickingOnTheNews(position);
        String dateOnCardNews = newsScreenStep.dateOnCardNews(position);

        newsScreenStep.checkingTheDateOfTheFoundNewsWithTheDataEnteredForTheSearch(
                dateOnCardNews, dateStartInput, dateEndInput, localDate);
    }

    @Test
    @DisplayName("Must find all the news when performing a search without entering data")
    @Description("В этом тест кейсе мы проверяем поиск новости без установки критериев для поиска")
    public void mustFindAllTheNewsWhenPerformingSearchWithoutEnteringData() {
        int position = randomNews(0, 1, 2);

        mainScreenStep.clickingOnTheActionMenuButton();
        mainScreenStep.clickingOnTheNewsName();
        newsScreenStep.clickingOnTheButtonToGoToFilterNews();

        filterNewsScreenStep.clickingOnTheSearchButton();
        newsScreenStep.clickingOnTheNews(position);
        try {
            newsScreenStep.checkingTheDisplayOfTheFoundNewsData(position);
        } catch (NoMatchingViewException e) {
            newsScreenStep.checkingTheDisplayOfTheInscriptionInTheAbsenceOfFoundNews();
        }
    }

    @Test
    @DisplayName("Canceling the search")
    @Description("В этом тест кейсе мы проверяем отмену поиска ")
    public void cancelingTheSearch() {
        mainScreenStep.clickingOnTheActionMenuButton();
        mainScreenStep.clickingOnTheNewsName();
        newsScreenStep.clickingOnTheButtonToGoToFilterNews();
        filterNewsScreenStep.checkingTheScreenNameForNewsSearch();
        filterNewsScreenStep.clickingOnTheCancelSearchButton();
        newsScreenStep.checkTheNameOfTheNewsScreen();
    }

    @Test
    @DisplayName("A window should appear with the inscription news not found")
    @Description("В этом тест кейсе мы проверяем, что при отсутствии подходящей по критериям поиска новости, появляется окно с надписью \"There is nothing here yet...\" ")
    public void aWindowShouldAppearWithTheInscriptionNewsNotFound() {
        String dateStartInput = invalidGeneratorDate();
        String dateEndInput = invalidGeneratorDate();

        mainScreenStep.clickingOnTheActionMenuButton();
        mainScreenStep.clickingOnTheNewsName();
        newsScreenStep.clickingOnTheButtonToGoToFilterNews();
        filterNewsScreenStep.checkingTheScreenNameForNewsSearch();
        filterNewsScreenStep.enteringDates(dateStartInput, dateEndInput);
        filterNewsScreenStep.clickingOnTheSearchButton();
        newsScreenStep.checkingTheDisplayOfTheInscriptionInTheAbsenceOfFoundNews();
    }
}
