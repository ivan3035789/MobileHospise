package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.DateTime.generatorDate;
import static ru.iteco.fmhandroid.ui.data.Helper.DateTime.generatorDate2;
import static ru.iteco.fmhandroid.ui.data.Helper.DateTime.invalidGeneratorDate;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.random;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomClaims;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomNews;
import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;
import static ru.iteco.fmhandroid.ui.data.Helper.createNews;
import static ru.iteco.fmhandroid.ui.data.Helper.createNewsForCategory;
import static ru.iteco.fmhandroid.ui.data.Helper.deletingNewsUpToTheNumberOfTenControlPanelScreen;
import static ru.iteco.fmhandroid.ui.data.Helper.setUpStatusNewsNotActive;

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
import ru.iteco.fmhandroid.ui.step.AdvancedNewsSearchScreenStep;
import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.ControlPanelScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AdvancedNewsSearchTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    MainScreenStep mainScreenStep = new MainScreenStep();
    ControlPanelScreenStep controlPanelScreenStep = new ControlPanelScreenStep();
    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    AdvancedNewsSearchScreenStep advancedNewsSearchScreenStep = new AdvancedNewsSearchScreenStep();

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
    @DisplayName("The screen should have name")
    @Description("В этом тест кейсе мы проверяем название экрана Filter news")
    public void theScreenShouldHaveName() {
        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.checkingTheNameOfTheAdvancedSearchScreen();
    }

    @Test
    @DisplayName("the fields must have names")
    @Description("В этом тест кейсе мы проверяем названия полей")
    public void theFieldsMustHaveNames() {
        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.checkingTheVisibilityOfIdentifyingFieldNames();
    }

    @Test
    @DisplayName("there must be check boxes")
    @Description("В этом тест кейсе мы проверяем названия чек боксов")
    public void thereMustBeCheckBoxes() {
        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.checkingTheVisibilityOfTheNamesOfCheckBoxes();
    }

    @Test
    @DisplayName("Must search for news in the list of news blocks using FilterNews according to the specified criteria in all fields")
    @Description("В этом тест кейсе мы проверяем поиск новости по всем заданным критериям в полях в разделе \"Filter news\"")
    public void mustSearchForNewsByCriteriaInAllFields() throws ParseException {
        String category = randomCategory();
        String dateStartInput = generatorDate2();
        String dateEndInput = generatorDate();
        String text1 = Helper.Text.textSymbol(5);
        String text = text1 + " " + text1;
        createNewsForCategory(text, category);

        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.checkingTheNameOfTheAdvancedSearchScreen();

        advancedNewsSearchScreenStep.checkingTheNameOfTheAdvancedSearchScreen();
        advancedNewsSearchScreenStep.fillingInFieldsWithSearchData(category, dateStartInput, dateEndInput);
        advancedNewsSearchScreenStep.clickingOnTheFilterButtonToSearchForNews();

        int positionNews = Helper.Search.searchNews(text);
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(positionNews);
        String dateOnCardNews = controlPanelScreenStep.dateOnCardNews(positionNews);
        controlPanelScreenStep.comparisonOfSearchDataWithNewsData(dateOnCardNews, dateStartInput, dateEndInput, positionNews, category);
    }

    @Test
    @DisplayName("Must search by Category")
    @Description("В этом тест кейсе мы проверяем поиск новости по категории")
    public void mustSearchByCategory() {
        String category = randomCategory();
        String text = Helper.Text.textSymbol(5);
        createNewsForCategory(text, category);

        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.checkingTheNameOfTheAdvancedSearchScreen();

        advancedNewsSearchScreenStep.fillingInTheCategoryField(category);

        advancedNewsSearchScreenStep.clickingOnTheFilterButtonToSearchForNews();

        int positionNews = Helper.Search.searchNews(text);
        controlPanelScreenStep.checkingTheEnteredDataForTheSearchWithThoseObtainedFromTheNews(category, positionNews);
    }

    @Test
    @DisplayName("Must search by date")
    @Description("В этом тест кейсе мы проверяем поиск новости по дате")
    public void mustSearchByDate() throws ParseException {
        int position = random(1, 2);
        String dateStartInput = generatorDate2();
        String dateEndInput = generatorDate();

        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.checkingTheNameOfTheAdvancedSearchScreen();
        advancedNewsSearchScreenStep.fillingInFieldsForDateSearch(dateStartInput, dateEndInput);
        advancedNewsSearchScreenStep.clickingOnTheFilterButtonToSearchForNews();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.comparisonOfSearchDataByDateWithNewsData(position, dateStartInput, dateEndInput);
    }

    @Test
    @DisplayName("Must find all the news when performing a search without entering data")
    @Description("В этом тест кейсе мы проверяем поиск новости без установки критериев для поиска")
    public void mustFindAllTheNewsWhenPerformingSearchWithoutEnteringData() {
        int position = random( 1, 2);

        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.checkingTheNameOfTheAdvancedSearchScreen();
        advancedNewsSearchScreenStep.clickingOnTheFilterButtonToSearchForNews();
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.checkingTheFoundNews(position);
    }

    @Test
    @DisplayName("Canceling the search")
    @Description("В этом тест кейсе мы проверяем отмену поиска")
    public void cancelingTheSearch() {
        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.checkingTheNameOfTheAdvancedSearchScreen();
        advancedNewsSearchScreenStep.clickingOnTheCancelSearchButton();
        controlPanelScreenStep.checkingTheNameOfTheControlPanelScreen();
    }

    @Test
    @DisplayName("A window should appear with the inscription news not found")
    @Description("В этом тест кейсе мы проверяем, что при отсутствии подходящей по критериям поиска новости, появляется окно с надписью \"There is nothing here yet...\"")
    public void aWindowShouldAppearWithTheInscriptionNewsNotFound() {
        String dateStartInput = invalidGeneratorDate();
        String dateEndInput = invalidGeneratorDate();

        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.checkingTheNameOfTheAdvancedSearchScreen();
        advancedNewsSearchScreenStep.fillingInTheFieldsForTheDate(dateStartInput, dateEndInput);
        advancedNewsSearchScreenStep.clickingOnTheFilterButtonToSearchForNews();
        advancedNewsSearchScreenStep.checkingTheTextWhenNewsIsNotFound();
    }

    @Test
    @DisplayName("must find news with active status")
    @Description("В этом тест кейсе мы проверяем поиск новости по статусу Active")
    public void mustFindNewsWithActiveStatus() {
        int position = randomNews( 1);

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);

        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.checkingTheNameOfTheAdvancedSearchScreen();
        advancedNewsSearchScreenStep.clickingOnTheActiveCheckBox();
        advancedNewsSearchScreenStep.clickingOnTheFilterButtonToSearchForNews();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.CheckingTheStatusActive(position);
    }

    @Test
    @DisplayName("must find news with the status not active")
    @Description("В этом тест кейсе мы проверяем поиск новости по статусу Not active")
    public void mustFindNewsWithTheStatusNotActive() {
        String category = randomCategory();
        String text = Helper.Text.textSymbol(5);
        createNews(text, category);

        int positionNews = Helper.Search.searchNews(text.trim());
        setUpStatusNewsNotActive(positionNews);
        
        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.clickingOnTheNotActiveCheckBox();
        advancedNewsSearchScreenStep.clickingOnTheFilterButtonToSearchForNews();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(positionNews);
        controlPanelScreenStep.CheckingTheStatusNotActive(positionNews);
    }

    @Test
    @DisplayName("Must search by Status and Active Not active")
    @Description("В этом тест кейсе мы проверяем поиск новости по по статусам  Active и Not active")
    public void mustSearchByStatusAndActiveNotActive() {
        int position = randomClaims(0, 1);

        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        advancedNewsSearchScreenStep.clickingOnTheFilterButtonToSearchForNews();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.checkingTheStatusOfTheFoundNews(position);
    }
}

