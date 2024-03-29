package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomNews;
import static ru.iteco.fmhandroid.ui.data.Helper.Text.textSymbol;
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

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.ControlPanelScreenStep;
import ru.iteco.fmhandroid.ui.step.EditingNewsScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;
import ru.iteco.fmhandroid.ui.step.NewsScreenStep;
import ru.iteco.fmhandroid.ui.step.WatchScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class EditingNewsScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    MainScreenStep mainScreenStep = new MainScreenStep();
    NewsScreenStep newsScreenStep = new NewsScreenStep();
    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    ControlPanelScreenStep controlPanelScreenStep = new ControlPanelScreenStep();
    EditingNewsScreenStep editingNewsScreenStep = new EditingNewsScreenStep();
    WatchScreenStep watchScreenStep = new WatchScreenStep();

    String messageEmpty = "Fill empty fields";
    String messageSaving = "Saving failed. Try again later.";
    int position = randomNews(1);

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
            newsScreenStep.clickingOnTheButtonToGoToTheControlPanel();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(5000);
    }

    @Test
    @DisplayName("The screen should have a name")
    @Description("В этом тест кейсе мы проверяем название экрана Editing News")
    public void theScreenShouldHaveName() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();
        editingNewsScreenStep.checkingTheNameOfTheEditingNewsScreen();
    }

    @Test
    @DisplayName("The fields must contain data")
    @Description("В этом тест кейсе мы проверяем, что поля имеют данные заполненые ранее")
    public void theFieldsMustContainData() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();

        String category = editingNewsScreenStep.categoryField();
        String titleTextNews = editingNewsScreenStep.titleTextNewsField();
        String publishDate = editingNewsScreenStep.publishDateField();
        String time = editingNewsScreenStep.timeField();
        String description = editingNewsScreenStep.descriptionField();

        editingNewsScreenStep.checkingWhetherTheFieldsAreFilledWithData(
                category, titleTextNews, publishDate, time, description);
    }

    @Test
    @DisplayName("A drop-down list with categories should appear")
    @Description("В этом тест кейсе мы проверяем, что при клике на поле \"Category\" появляется выпадающий список с доспупными категориями")
    public void aDropDownListWithCategoriesShouldAppear() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();
        editingNewsScreenStep.clickingOnTheCategoryField();
        editingNewsScreenStep.checkingTheAppearanceOfTheDropDownList(ActivityTestRule.getActivity());
    }

    @Test
    @DisplayName("A calendar should appear")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на поле \"Publication date\" появляется календарь ")
    public void aCalendarShouldAppear() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();
        editingNewsScreenStep.clickingOnThePublicationDateField();
        editingNewsScreenStep.checkingTheCalendarAppearance(ActivityTestRule.getActivity());
    }

    @Test
    @DisplayName("A clock of the arrow type should appear")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на поле \"Time\" появляется часы стрелочного типа")
    public void aClockOfTheArrowTypeShouldAppear() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();
        editingNewsScreenStep.clickingOnTheTimeField();
        editingNewsScreenStep.checkingTheAppearanceOfClockOfTheArrowType(ActivityTestRule.getActivity());
    }

    @Test
    @DisplayName("The type of watch should change")
    @Description("В этом тест кейсе мы проверяем возможность выбора типа часов. что при нажатии на кнопку с иконкой \"клавиатура\" должен поменяться вид часов")
    public void theTypeOfWatchShouldChange() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();
        editingNewsScreenStep.clickingOnTheTimeField();
        watchScreenStep.pressingTheButtonToChangeTheWatchType();
        watchScreenStep.checkingTheTypeOfDigitalClock();
    }

    @Test
    @DisplayName("The news should be edited")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"SAVE\" после заполнения полей \"Title," +
            " Time, Description, Publication date\" валидными значениями, должна создаться отредактированная новость (появиться в ленте" +
            " новостей на странице \"News, Main\", а так же в \"Control panel\")")
    public void theNewsShouldBeEdited() {
        String text = textSymbol(5);
        String Category = randomCategory();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);

        String nameNewsItWas = editingNewsScreenStep.nameNews();
        String publicationDateNewsItWas = editingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWas = editingNewsScreenStep.creationDateNews();
        String descriptionNewsItWas = editingNewsScreenStep.descriptionNews();

        editingNewsScreenStep.clickingOnTheButtonToEnterTheNewsEditing(nameNewsItWas);
        editingNewsScreenStep.fillingInTheNewsFieldsWithNewData(text, Category);

        String nameNewsInput = editingNewsScreenStep.titleTextNewsField();
        String publicationDateNewsInput = editingNewsScreenStep.publishDateField();
        String timeNewsInput = editingNewsScreenStep.timeField();
        String descriptionNewsInput = editingNewsScreenStep.descriptionField();

        editingNewsScreenStep.clickingOnTheSaveButton();
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);

        String nameNewsItWasHasBecomes = editingNewsScreenStep.nameNews();
        String publicationDateNewsItWasHasBecomes = editingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWasHasBecomes = editingNewsScreenStep.creationDateNews();
        String descriptionNewsItWasHasBecomes = editingNewsScreenStep.descriptionNews();

        editingNewsScreenStep.checkingTheInitialDataOfTheNewsWithTheFillingDataAndTheFinal(
                nameNewsItWas, nameNewsInput, publicationDateNewsItWas, publicationDateNewsInput,
                creationDateNewsItWas, timeNewsInput, descriptionNewsItWas, descriptionNewsInput,
                nameNewsItWasHasBecomes, publicationDateNewsItWasHasBecomes, creationDateNewsItWasHasBecomes,
                descriptionNewsItWasHasBecomes);
    }

    @Test
    @DisplayName("Canceling news editing")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"CANCEL\" и после появления окна с " +
            "предупреждающей надписью и кнопками  \"CANCEL\", \"ок\" при нажатии на кнопку  \"ок\" пользователь выходит " +
            "из Еditing News в \"Control panel\", новость не отредактирована")
    public void cancelingNewsEditing() {
        String text = textSymbol(5);
        String Category = randomCategory();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);

        String nameNewsItWas = editingNewsScreenStep.nameNews();
        String publicationDateNewsItWas = editingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWas = editingNewsScreenStep.creationDateNews();
        String descriptionNewsItWas = editingNewsScreenStep.descriptionNews();

        editingNewsScreenStep.clickingOnTheButtonToEnterTheNewsEditing(nameNewsItWas);
        editingNewsScreenStep.fillingInTheNewsFieldsWithNewData(text, Category);

        String nameNewsInput = editingNewsScreenStep.titleTextNewsField();
        String publicationDateNewsInput = editingNewsScreenStep.publishDateField();
        String timeNewsInput = editingNewsScreenStep.timeField();
        String descriptionNewsInput = editingNewsScreenStep.descriptionField();

        editingNewsScreenStep.clickingOnTheCancelNewsEditingButton();
        editingNewsScreenStep.clickingOnTheButtonToConfirmTheCancellationOfNewsEditing();

        String nameNewsItWasHasBecomes = editingNewsScreenStep.nameNews();
        String publicationDateNewsItWasHasBecomes = editingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWasHasBecomes = editingNewsScreenStep.creationDateNews();
        String descriptionNewsItWasHasBecomes = editingNewsScreenStep.descriptionNews();
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);

        editingNewsScreenStep.checkingNewsDataBeforeEditingAndAfterCancelingEditing(
                nameNewsItWas, nameNewsInput, publicationDateNewsItWas, publicationDateNewsInput,
                creationDateNewsItWas, timeNewsInput, descriptionNewsItWas, descriptionNewsInput,
                nameNewsItWasHasBecomes, publicationDateNewsItWasHasBecomes, creationDateNewsItWasHasBecomes,
                descriptionNewsItWasHasBecomes);
    }

    @Test
    @DisplayName("A warning message should appear if the fields are empty when clicking on the save button")
    @Description("В этом тест кейсе мы проверяем, что при незаполненном, незаполненных полях появляется предупреждающее сообщение, после нажатия на кнопку \"SAVE\"  \"fill empty fields\" ")
    public void aWarningMessageShouldAppearIfTheFieldsAreEmptyWhenClickingOnTheSaveButton() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();
        editingNewsScreenStep.deletingTheNewsTitle();
        editingNewsScreenStep.clickingOnTheSaveButton();
        editingNewsScreenStep.checkingTheFillEmptyFields(ActivityTestRule.getActivity(), messageEmpty);
    }

    @Test
    @DisplayName("A warning message should appear when filling in the Category field")
    @Description("В этом тест кейсе мы проверяем, что при заполненнии поле Category категорией не из списка, после нажатия на кнопку \"SAVE\" появляется предупреждающая надпись \"Saving failed. Try again later.\"  ")
    public void aWarningMessageShouldAppearWhenFillingInTheCategoryField() {
        String text = textSymbol(5);

        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();
        editingNewsScreenStep.enteringTextInTheCategoryField(text);
        editingNewsScreenStep.clickingOnTheSaveButton();
        editingNewsScreenStep.checkingTheSavingFailedTryAgainLater(ActivityTestRule.getActivity(), messageSaving);
    }

    @Test
    @DisplayName("The fields must be filled in with English letters")
    @Description("В этом тест кейсе мы проверяем, что поля заполняются латинскими буквами ")
    public void theFieldsMustBeFilledInWithEnglishLetters() {
        String validLanguageText = "Hello world";

        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();
        editingNewsScreenStep.validLanguage(validLanguageText);
        editingNewsScreenStep.checkingForThePresenceOfWordsFromEnglishLettersInTheFields(validLanguageText);
    }

    @Test
    @DisplayName("The fields must be filled in with Russian letters")
    @Description("В этом тест кейсе мы проверяем, что поля незаполняются нелатинскими буквами")
    public void fieldsShouldNotBeFilledInWithRussianLetters() {
        String invalidLanguageText = "Привет мир";

        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();
        editingNewsScreenStep.invalidLanguage(invalidLanguageText);
        editingNewsScreenStep.checkingForTheAbsenceOfWordsFromRussianLettersInTheFields();
    }

    @Test
    @DisplayName("Undo Undo Edit")
    @Description("В этом тест кейсе мы проверяем что при нажатии на кнопку Cancel в всплывшем окне (для подтверждения выхода или отмены выхода) пользователь остается на экране Editing News")
    public void undoUndoEdit() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsEditingScreen();
        editingNewsScreenStep.clickingOnTheCancelNewsEditingButton();
        editingNewsScreenStep.clickingOnTheCancelButtonToExitEditing();
        editingNewsScreenStep.checkingTheNameOfTheEditingNewsScreen();
    }

    @Test
    @DisplayName("The status in the news block in the Control panel should change")
    @Description("В этом тест кейсе мы проверяем что при переключении чек бокса со статусм Not Active на Active в новостной ленте статус меняется с Not Active на Active")
    public void theStatusInTheNewsBlockInTheControlPanelShouldChange() {
        Helper.setUpStatusNewsNotActive(position);

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);

        String nameNewsItWas = editingNewsScreenStep.nameNews();
        ViewInteraction statusBefore = editingNewsScreenStep.statusNews(nameNewsItWas);

        editingNewsScreenStep.clickingOnTheNews(nameNewsItWas);
        editingNewsScreenStep.clickingOnTheCheckBox();
        editingNewsScreenStep.clickingOnTheSaveButton();

        String statusAfter = editingNewsScreenStep.statusNewsText(nameNewsItWas);
        ViewInteraction statusAfter2 = editingNewsScreenStep.statusNewsPosition(position);

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.checkingTheStatusChange(statusBefore.toString(), statusAfter, statusAfter2);
    }
}
