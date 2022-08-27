package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;
import static ru.iteco.fmhandroid.ui.data.Helper.Text.textSymbol;
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

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.ControlPanelScreenStep;
import ru.iteco.fmhandroid.ui.step.CreatingNewsScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;
import ru.iteco.fmhandroid.ui.step.NewsScreenStep;
import ru.iteco.fmhandroid.ui.step.WatchScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class CreatingNewsScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    MainScreenStep mainScreenStep = new MainScreenStep();
    NewsScreenStep newsScreenStep = new NewsScreenStep();
    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    ControlPanelScreenStep controlPanelScreenStep = new ControlPanelScreenStep();
    CreatingNewsScreenStep creatingNewsScreenStep = new CreatingNewsScreenStep();
    WatchScreenStep watchScreenStep = new WatchScreenStep();

    String messageSaving = "Saving failed. Try again later.";
    String messageEmpty = "Fill empty fields";

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
//            controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(5000);
    }

    @Test
    @DisplayName("The screen should have a name")
    @Description("В этом тест кейсе мы проверяем название экрана Creating News")
    public void theScreenShouldHaveName() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.checkingTheNameOfTheCreatingNewsScreen();
    }

    @Test
    @DisplayName("the fields must have names")
    @Description("В этом тест кейсе мы проверяем названия полей")
    public void FieldsMustHaveNames() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.checkNameFieldInCreatingNews();
    }

    @Test
    @DisplayName("A drop-down list with categories should appear")
    @Description("В этом тест кейсе мы проверяем, что при клике на поле \"Category\" появляется выпадающий список с доспупными категориями ")
    public void aDropDownListWithCategoriesShouldAppear() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.clickingOnTheCategoryField();
        creatingNewsScreenStep.checkingTheAppearanceOfTheDropDownList(ActivityTestRule.getActivity());
    }

    @Test
    @DisplayName("A calendar should appear")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на поле \"Publication date\" появляется календарь ")
    public void aCalendarShouldAppear() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.clickingOnTheDateField();
        creatingNewsScreenStep.checkingTheCalendarAppearance(ActivityTestRule.getActivity());
    }

    @Test
    @DisplayName("A clock of the arrow type should appear")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на поле \"Time\" появляется часы стрелочного типа")
    public void aClockOfTheArrowTypeShouldAppear() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.clickingOnTheTimeField();
        creatingNewsScreenStep.checkingTheAppearanceOfClockOfTheArrowType(ActivityTestRule.getActivity());
    }

    @Test
    @DisplayName("The type of watch should change")
    @Description("В этом тест кейсе мы проверяем возможность выбора типа часов. что при нажатии на кнопку с иконкой \"клавиатура\" должен поменяться вид часов")
    public void theTypeOfWatchShouldChange() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.clickingOnTheTimeField();
        watchScreenStep.pressingTheButtonToChangeTheWatchType();
        watchScreenStep.checkingTheTypeOfDigitalClock();
    }

    @Test
    @DisplayName("News should be created")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"SAVE\" после заполнения полей \"Title, Time, Description, Publicftion date\" валидными значениями, должна создаться новость (появиться в ленте новостей на странице \"News, Main\", а так же в \"Control panel\"")
    public void newsShouldBeCreated() {
        int position = 0;
        String text = textSymbol(5);
        String validCategory = randomCategory();

        creatingNewsScreenStep.choosingNews(position);

        String nameNewsItWas = creatingNewsScreenStep.nameNews();
        String publicationDateNewsItWas = creatingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWas = creatingNewsScreenStep.creationDateNews();
        String authorNewsItWas = creatingNewsScreenStep.authorNews();
        String descriptionNewsItWas = creatingNewsScreenStep.descriptionNews();

        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.fillingInFieldsWithValidData(text, validCategory);
        creatingNewsScreenStep.clickingOnTheSaveNewsButton();
        int positionNews = Helper.Search.searchNews(text);
        creatingNewsScreenStep.choosingNews(positionNews);

        String nameNewsItWasHasBecomes = creatingNewsScreenStep.nameNewsPosition(positionNews);
        String publicationDateNewsItWasHasBecomes = creatingNewsScreenStep.publicationDateNewsPosition(positionNews);
        String creationDateNewsItWasHasBecomes = creatingNewsScreenStep.creationDateNewsPosition(positionNews);
        String authorNewsItWasHasBecomes = creatingNewsScreenStep.authorNewsPosition(positionNews);
        String descriptionNewsItWasHasBecomes = creatingNewsScreenStep.descriptionNewsPosition(positionNews);

        creatingNewsScreenStep.comparingTheDataOfTheCreatedNewsWithTheDataOfTheFirstNewsFromTheList(
                nameNewsItWas, nameNewsItWasHasBecomes, publicationDateNewsItWas, publicationDateNewsItWasHasBecomes,
                creationDateNewsItWas, creationDateNewsItWasHasBecomes, authorNewsItWas, authorNewsItWasHasBecomes,
                descriptionNewsItWas, descriptionNewsItWasHasBecomes);
    }


    @Test
    @DisplayName("Canceling news creation")
    @Description("В этом тест кейсе мы проверяем отмену создания новости при нажатии на кнопку \"CANCEL\"")
    public void cancelingNewsCreation() {
        int position = 0;
        String text = textSymbol(5);
        String validCategory = randomCategory();

        creatingNewsScreenStep.choosingNews(position);

        String nameNewsItWas = creatingNewsScreenStep.nameNews();
        String publicationDateNewsItWas = creatingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWas = creatingNewsScreenStep.creationDateNews();
        String authorNewsItWas = creatingNewsScreenStep.authorNews();
        String descriptionNewsItWas = creatingNewsScreenStep.descriptionNews();

        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.fillingInFieldsWithValidData(text, validCategory);
        creatingNewsScreenStep.clickingOnTheExitButtonFromNewsCreation();
        creatingNewsScreenStep.clickingOnTheConfirmationButtonToCancelTheCreationOfTheNews();

        creatingNewsScreenStep.choosingNews(position);

        String nameNewsItWasHasBecomes = creatingNewsScreenStep.nameNews();
        String publicationDateNewsItWasHasBecomes = creatingNewsScreenStep.publicationDateNews();
        String creationDateNewsItWasHasBecomes = creatingNewsScreenStep.creationDateNews();
        String authorNewsItWasHasBecomes = creatingNewsScreenStep.authorNews();
        String descriptionNewsItWasHasBecomes = creatingNewsScreenStep.descriptionNews();

        creatingNewsScreenStep.checkingTheDataOfTheFirstNewsFromTheListMustMatchAfterCancelingTheCreationOfTheNews(
                nameNewsItWas, nameNewsItWasHasBecomes, publicationDateNewsItWas, publicationDateNewsItWasHasBecomes,
                creationDateNewsItWas, creationDateNewsItWasHasBecomes, authorNewsItWas, authorNewsItWasHasBecomes,
                descriptionNewsItWas, descriptionNewsItWasHasBecomes);
    }

    @Test
    @DisplayName("A warning message should appear if the fields are empty when you click on the SAVE button")
    @Description("В этом тест кейсе мы проверяем, что при незаполненном, незаполненных полях появляется предупреждающее сообщение, после нажатия на кнопку \"SAVE\"  \"fill empty fields\" ")
    public void aWarningMessageShouldAppearIfTheFieldsAreEmptyWhenYouClickOnTheSaveButton() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.clickingOnTheSaveNewsButton();
        creatingNewsScreenStep.checkingTheFillEmptyFields(ActivityTestRule.getActivity(), messageEmpty);
    }

    @Test
    @DisplayName("A warning message should appear when filling in the Category field")
    @Description("В этом тест кейсе мы проверяем, что при заполнении поля Category и последующим нажатием на кнопку сохранения появляется предупреждающая надпись")
    public void aWarningMessageShouldAppearWhenFillingInTheCategoryField() {
        String text = textSymbol(5);

        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.fillingInTheCategoryField(text);
        creatingNewsScreenStep.clickingOnTheSaveNewsButton();
        creatingNewsScreenStep.checkingTheSavingFailedTryAgainLater(ActivityTestRule.getActivity(), messageSaving);
    }

    @Test
    @DisplayName("The fields must be filled in with English letters")
    @Description("В этом тест кейсе мы проверяем, что поля заполняются латинскими буквами")
    public void theFieldsMustBeFilledInWithEnglishLetters() {
        String validLanguageText = "hello world";

        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.validLanguage(validLanguageText);
        creatingNewsScreenStep.checkingForThePresenceOfWordsFromEnglishLettersInTheFields(validLanguageText);
    }

    @Test
    @DisplayName("Fields should not be filled in with Russian letters")
    @Description("В этом тест кейсе мы проверяем, что поля незаполняются нелатинскими буквами")
    public void fieldsShouldNotBeFilledInWithRussianLetters() {
        String invalidLanguageText = "привет мир";

        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
//        try {
            creatingNewsScreenStep.invalidLanguage(invalidLanguageText);
//        } catch (RuntimeException e) {
//
//        } finally {
            creatingNewsScreenStep.checkingForTheAbsenceOfWordsFromRussianLettersInTheFields();
//        }
    }
}
