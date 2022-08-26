package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomClaims;
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
import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.ClaimsScreenStep;
import ru.iteco.fmhandroid.ui.step.FilteringWindowScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class FilteringScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    MainScreenStep mainScreenStep = new MainScreenStep();
    ClaimsScreenStep claimsScreenStep = new ClaimsScreenStep();
    FilteringWindowScreenStep filteringWindowScreenStep = new FilteringWindowScreenStep();

    int position = randomClaims(0, 1, 2);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenStep.checkNameMainScreen();
        } catch (NoMatchingViewException e) {
            authorizationScreenStep.validLoginPassword(authInfo());
        } finally {
            mainScreenStep.clickingOnTheActionMenuButton();
            mainScreenStep.clickingOnTheClaimsName();
            claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(5000);
    }

    @Test
    @DisplayName("The screen should have a name")
    @Description("В этом тест кейсе мы проверяем название экрана Filtering")
    public void theScreenShouldHaveName() {
        filteringWindowScreenStep.checkingTheScreenNameFiltering();
    }

    @Test
    @DisplayName("Must find a claim using Filtering when using a single check-box Open")
    @Description("В этом тест кейсе мы проверяем поиск \"Претензии\" с помощью Filtering, устанавливая галочку,  на нужном параметре чек-бокса для поиска \"Open\"")
    public void mustFindClaimUsingFilteringWhenUsingSingleCheckBoxOpen() {
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheCheckBoxOpen();
        filteringWindowScreenStep.clickingOnRandomlySelectedCheckBox();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        filteringWindowScreenStep.checkingTheStatus();
    }

    @Test
    @DisplayName("Must find a claim using Filtering when using a single checkbox In progress")
    @Description("В этом тест кейсе мы проверяем поиск \"Topic\" с помощью Filtering, устанавливая галочку,  на нужном параметре чек-бокса для поиска  \"In progress\"")
    public void mustFindClaimUsingFilteringWhenUsingSingleCheckboxInProgress() {
        filteringWindowScreenStep.clickingOnTheCheckBoxOpen();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheInProgressStatus();
    }

    @Test
    @DisplayName("Must find a claim using the Executed checkbox")
    @Description("В этом тест кейсе мы проверяем поиск \"Topic\" с помощью Filtering, устанавливая галочку,  на нужном параметре чек-бокса для поиска \"Executed\"")
    public void mustFindClaimUsingTheExecutedCheckBox() {
        filteringWindowScreenStep.clickingOnTheCheckBoxOpen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheCheckBoxExecuted();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheExecutedStatus();
    }

    @Test
    @DisplayName("Must find a claim using Filtering when using a single Cancelled checkbox")
    @Description("В этом тест кейсе мы проверяем поиск \"Topic\" с помощью Filtering, устанавливая галочку,  на нужном параметре чек-бокса для поиска \"Cancelled\"")
    public void mustFindClaimUsingFilteringWhenUsingSingleCancelledCheckbox() {
        filteringWindowScreenStep.clickingOnTheCheckBoxOpen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheCheckBoxCancelled();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheCanceledStatus();
    }

    @Test
    @DisplayName("Must find a claim using Filtering when using all checkboxes")
    @Description("В этом тест кейсе мы проверяем поиск \"претензии\" с помощью Filtering, устанавливая  галочку,  на всех имеющихся критериях чек-боксов для поиска ")
    public void mustFindClaimUsingFilteringWhenUsingAllCheckBoxes() {
        filteringWindowScreenStep.clickingOnTheCheckBoxExecuted();
        filteringWindowScreenStep.clickingOnTheCheckBoxCancelled();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        filteringWindowScreenStep.checkingTheStatus();
    }

    @Test
    @DisplayName("An inscription should appear about the absence of claims found using Filtering when checking the boxes")
    @Description("В этом тест кейсе мы проверяем, что при отсутствии установленных галочек в чек-боксах  по критериям поиска \"претензии\", появляется окно с изображением бабочки и надписью \"There is nothing here yet...\"")
    public void anInscriptionShouldAppearAboutTheAbsenceOfClaimsFoundUsingFilteringWhenCheckingTheBoxes() {
        filteringWindowScreenStep.clickingOnTheCheckBoxOpen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheOkButton();
        filteringWindowScreenStep.checkingTheMessageForUndiscoveredClaims();
    }

    @Test
    @DisplayName("Cancellation of the claim search")
    @Description("В этом тест кейсе мы проверяем отмену поиска при нажатии на кнопку \"CANCEL\" ")
    public void cancellationOfTheClaimSearch() {
        filteringWindowScreenStep.checkingTheScreenNameFiltering();
        filteringWindowScreenStep.clickingOnTheExitFilteringButton();
        filteringWindowScreenStep.checkingForMissingScreenNameFiltering();
        claimsScreenStep.checkScreenNameClaims();
    }
}
