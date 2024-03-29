package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomNews;
import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;
import static ru.iteco.fmhandroid.ui.data.Helper.createNews;
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

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.ControlPanelScreenStep;
import ru.iteco.fmhandroid.ui.step.CreatingNewsScreenStep;
import ru.iteco.fmhandroid.ui.step.EditingNewsScreenStep;
import ru.iteco.fmhandroid.ui.step.FilterNewsScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class ControlPanelScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    MainScreenStep mainScreenStep = new MainScreenStep();
    ControlPanelScreenStep controlPanelScreenStep = new ControlPanelScreenStep();
    CreatingNewsScreenStep creatingNewsScreenStep = new CreatingNewsScreenStep();
    FilterNewsScreenStep filterNewsScreenStep = new FilterNewsScreenStep();
    EditingNewsScreenStep editingNewsScreenStep = new EditingNewsScreenStep();

    int position = randomNews(0);

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
    @Description("В этом тест кейсе мы проверяем название экрана Control Panel")
    public void theScreenShouldHaveName() {
        controlPanelScreenStep.checkingTheNameOfTheControlPanelScreen();
    }

    @Test
    @DisplayName("News blocks should be swapped")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"стрелки вверх и вниз\" в \"Control panel\"" +
            " должны поменяться местами новостные блоки исходя из более поздней даты по возростанию (самая позняя дата" +
            " должна оказаться на самом верху  новостной ленты), на более раннюю (по убыванию, самая раняя дата должна " +
            "оказаться в самом низу новостной ленты) и наоборот")
    public void newsBlocksShouldBeSwapped() {
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        String firstNews = controlPanelScreenStep.nameNewsPosition(position);
        String firstDescription = controlPanelScreenStep.descriptionNewsPosition(position);

        controlPanelScreenStep.changeOfSorting();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        String lastNews = controlPanelScreenStep.nameNewsPosition(position);
        String lastDescription = controlPanelScreenStep.descriptionNewsPosition(position);

        controlPanelScreenStep.changeOfSorting();

        String firstNewsAgain = controlPanelScreenStep.nameNewsPosition(position);
        String firstDescriptionAgain = controlPanelScreenStep.descriptionNewsPosition(position);
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);

        controlPanelScreenStep.checkingTheNewsBeforeSortingAndAfter(
                firstNews, firstDescription, lastDescription, firstNewsAgain, lastNews, firstDescriptionAgain);
    }

    @Test
    @DisplayName("must go to the Filter news")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"три полоски с кружками\" в  Control panel, пользователь попадает в \"Filter news\" ")
    public void mustGoToTheNewsFilter() {
        controlPanelScreenStep.pressingTheButtonToGoToTheAdvancedNewsSearchScreen();
        filterNewsScreenStep.checkingTheScreenNameForNewsSearch();
    }

    @Test
    @DisplayName("should go to the news creation section")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"+\" в  Control panel, пользователь попадает в  \"Creating News\"")
    public void shouldGoToTheNewsCreationSection() {
        controlPanelScreenStep.clickingOnTheButtonToGoToTheNewsCreationScreen();
        creatingNewsScreenStep.checkingTheNameOfTheCreatingNewsScreen();
    }

    @Test
    @DisplayName("must delete the news")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку с иконкой \"мусорный бачок\" в  Control panel," +
            " после появления всплывающего окна с надписью \"Are you sure you wont to permanently delete the document? " +
            "These changes cannot be reversed in the future.\" при нажатии на кнопку  \"ок\"  происходит удаление" +
            " новостного блока ")
    public void mustDeleteTheNews() {
        String category = randomCategory();
        String text = Helper.Text.textSymbol(5);

        createNews(text, category);

        String nameNewsItWas = controlPanelScreenStep.nameNews();
        String publicationDateNewsItWas = controlPanelScreenStep.publicationDateNews();
        String creationDateNewsItWas = controlPanelScreenStep.creationDateNews();
        String authorNewsItWas = controlPanelScreenStep.authorNews();
        String descriptionNewsItWas = controlPanelScreenStep.descriptionNews();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.clickingOnTheDeleteNewsButton();
        controlPanelScreenStep.clickingOnTheConfirmationButtonToDeleteTheNews();
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);

        String nameNewsItWasHasBecomes = controlPanelScreenStep.nameNews();
        String publicationDateNewsItWasHasBecomes = controlPanelScreenStep.publicationDateNews();
        String creationDateNewsItWasHasBecomes = controlPanelScreenStep.creationDateNews();
        String authorNewsItWasHasBecomes = controlPanelScreenStep.authorNews();
        String descriptionNewsItWasHasBecomes = controlPanelScreenStep.descriptionNews();

        controlPanelScreenStep.checkingTheDataOfTheFirstNewsInTheListBeforeAndAfterDeletingTheNews(
                nameNewsItWas, nameNewsItWasHasBecomes, publicationDateNewsItWas, publicationDateNewsItWasHasBecomes,
                creationDateNewsItWas, creationDateNewsItWasHasBecomes, authorNewsItWas, authorNewsItWasHasBecomes,
                descriptionNewsItWas, descriptionNewsItWasHasBecomes);
    }

    @Test
    @DisplayName("Must not delete a news block from the news feed while in the Control panel")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку с иконкой \"мусорный бачок\" в  Control panel," +
            " после появления всплывающего окна с надписью \"Are you sure you wont to permanently delete the document?" +
            " These changes cannot be reversed in the future.\" при нажатии на кнопку  \"CANCEL\" не происходит удаление" +
            " новостного блока")
    public void mustNotDeleteNewsBlockFromTheNewsFeedWhileInTheControlPanel() {
        int position = randomNews(1);

        String nameNewsItWas = controlPanelScreenStep.nameNews();
        String publicationDateNewsItWas = controlPanelScreenStep.publicationDateNews();
        String creationDateNewsItWas = controlPanelScreenStep.creationDateNews();
        String authorNewsItWas = controlPanelScreenStep.authorNews();
        String descriptionNewsItWas = controlPanelScreenStep.descriptionNews();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.clickingOnTheDeleteNewsButton();
        controlPanelScreenStep.clickingOnTheCancelConfirmationButtonToDeleteTheNews();

        String nameNewsItWasHasBecomes = controlPanelScreenStep.nameNews();
        String publicationDateNewsItWasHasBecomes = controlPanelScreenStep.publicationDateNews();
        String creationDateNewsItWasHasBecomes = controlPanelScreenStep.creationDateNews();
        String authorNewsItWasHasBecomes = controlPanelScreenStep.authorNews();
        String descriptionNewsItWasHasBecomes = controlPanelScreenStep.descriptionNews();

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.checkingTheDataOfTheFirstNewsInTheListBeforeAndAfterCancelingTheDeletionOfTheNews(
                nameNewsItWas, nameNewsItWasHasBecomes, publicationDateNewsItWas, publicationDateNewsItWasHasBecomes,
                creationDateNewsItWas, creationDateNewsItWasHasBecomes, authorNewsItWas, authorNewsItWasHasBecomes,
                descriptionNewsItWas, descriptionNewsItWasHasBecomes);
    }

    @Test
    @DisplayName("A description should appear in the news block in the Control panel")
    @Description(" В этом тест кейсе мы проверяем, что при нажатии на кнопку \"стрелка вниз\" или нажатии на новость, появляется описание")
    public void aDescriptionShouldAppearInTheNewsBlockInTheControlPanel() {
        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.checkingTheVisibilityOfTheNewsDescription();

    }

    @Test
    @DisplayName("must go to editing news")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку с иконкой \"блокнот с карандаошом\" в новостном блоке, в  Control panel, происходит переход в раздел \"Editing News\"")
    public void mustGoToEditingNews() {
        int position = randomNews( 0, 1, 2);

        controlPanelScreenStep.clickingOnRandomlySelectedNewsItem(position);
        controlPanelScreenStep.clickingOnTheButtonToGoToTheEditingNewsScreen(position);
        editingNewsScreenStep.checkingTheNameOfTheEditingNewsScreen();
    }
}

