package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.Rand.random;
import static ru.iteco.fmhandroid.ui.data.Helper.Text.textSymbol;
import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;
import static ru.iteco.fmhandroid.ui.data.Helper.createClaim;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.ClaimsScreenStep;
import ru.iteco.fmhandroid.ui.step.CreatingClaimsScreenStep;
import ru.iteco.fmhandroid.ui.step.EditingClaimsScreenStep;
import ru.iteco.fmhandroid.ui.step.FilteringWindowScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class ClaimsScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    MainScreenStep mainScreenStep = new MainScreenStep();
    ClaimsScreenStep claimsScreenStep = new ClaimsScreenStep();
    CreatingClaimsScreenStep creatingClaimsScreenStep = new CreatingClaimsScreenStep();
    FilteringWindowScreenStep filteringWindowScreenStep = new FilteringWindowScreenStep();
    EditingClaimsScreenStep editingClaimsScreenStep = new EditingClaimsScreenStep();

    int position = random(0);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenStep.checkNameMainScreen();
        } catch (NoMatchingViewException e) {
            authorizationScreenStep.validLoginPassword(authInfo());
        } finally {
            mainScreenStep.clickingOnAllClaims();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(5000);
    }

    @Test
    @DisplayName("The screen should have a name")
    @Description("В этом тест кейсе мы проверяем название экрана Claims")
    public void theScreenShouldHaveName() {
        claimsScreenStep.checkScreenNameClaims();
    }


    @Test
    @DisplayName("the filtering window should appear")
    @Description("В этом тест кейсе мы проверяем что при нажатии на кнопку \"три полоски с кружками\" на странице \"Claims\" пользователь попадает в \"Filtering\"")
    public void theFilteringWindowShouldAppear() {
        claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        filteringWindowScreenStep.checkingTheScreenNameFiltering();
    }

    @Test
    @DisplayName("should go to the create claims section")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"+\" пользователь попадает в \"Creating Claims\"")
    public void shouldGoToTheCreateClaimsSection() {
        claimsScreenStep.clickingOnTheButtonToGoToTheClaimCreationScreen();
        creatingClaimsScreenStep.checkingTheNameOfTheClaimCreationScreen();
    }

    @Test
    @DisplayName("Must go to Claims")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на блок \"претензия\" в ленте страницы \"Claims\", пользователь переходит в \"претензию\"")
    public void mustGoToClaims() {
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkClaim();
    }

    @Test
    @DisplayName("The status should change to In progress")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"блокнот с шестиренкой\"  и нажатии в сплывшем окне на \"Take to work\" статус претензии меняется на  \"In progress\" ")
    public void theStatusShouldChangeToInProgress() {
        createClaim();

        claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheOpenStatus();
        Helper.Swipes.swipeToBottom();

        claimsScreenStep.clickingOnTheButtonWithTheNotepadIconWithGear();
        claimsScreenStep.clickingOnTakeToWork();
        Helper.Swipes.swipeToTop();
        claimsScreenStep.checkingTheInProgressStatus();
    }

    @Test
    @DisplayName("Should go to Editing Claims")
    @Description(" В этом тест кейсе мы проверяем что при нажатии на кнопку  \"блокнот с карандаошом\"  в  \"Claims\" пользователь попадает в  \"Editing Claims\" ")
    public void shouldGoToEditingClaims() {
        claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        Helper.Swipes.swipeToBottom();
        claimsScreenStep.clickingOnTheNotepadWithPencilButton();
        editingClaimsScreenStep.checkingTheNameOfTheScreenForEditingClaims();
    }

    @Test
    @DisplayName("The claim must be edited")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"блокнот с карандашем\" пользователь попадает в раздел редактирования претензии претензия редактируется ")
    public void theClaimMustBeEdited() {
        claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheOpenStatus();

        String titleClaimFieldItWas = claimsScreenStep.authorText();
        String executorClaimFieldItWas = claimsScreenStep.executorText();
        String dateClaimFieldItWas = claimsScreenStep.planDateText();
        String timeClaimFieldItWas = claimsScreenStep.timeText();
        String descriptionClaimFieldItWas = claimsScreenStep.descriptionClaimField();

        Helper.Swipes.swipeToBottom();
        claimsScreenStep.clickingOnTheNotepadWithPencilButton();
        editingClaimsScreenStep.fillingInFieldsWithValidData();
        editingClaimsScreenStep.clickingOnTheSaveButton();

        String titleClaimFieldItWasHasBecome = claimsScreenStep.authorText();
        String executorClaimFieldItWasHasBecome = claimsScreenStep.executorText();
        String dateClaimFieldItWasHasBecome = claimsScreenStep.planDateText();
        String timeClaimFieldItWasHasBecome = claimsScreenStep.timeText();
        String descriptionClaimFieldItWasHasBecome = claimsScreenStep.descriptionClaimField();

        editingClaimsScreenStep.checkingDataBeforeEditingAndAfter(
                titleClaimFieldItWas, titleClaimFieldItWasHasBecome, executorClaimFieldItWas, executorClaimFieldItWasHasBecome,
                dateClaimFieldItWas, dateClaimFieldItWasHasBecome, timeClaimFieldItWas, timeClaimFieldItWasHasBecome,
                descriptionClaimFieldItWas, descriptionClaimFieldItWasHasBecome);
    }

    @Test
    @DisplayName("Cancellation of claim editing")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"CANCEL\" происходит отмена редактирования претензии")
    public void cancellationOfClaimEditing() {
        claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheOpenStatus();

        String executorTextItWas = claimsScreenStep.executorText();
        String planDateTextItWas = claimsScreenStep.planDateText();
        String timeTextItWas = claimsScreenStep.timeText();
        String authorTextItWas = claimsScreenStep.authorText();

        Helper.Swipes.swipeToBottom();
        claimsScreenStep.clickingOnTheNotepadWithPencilButton();
        editingClaimsScreenStep.checkingTheNameOfTheScreenForEditingClaims();
        editingClaimsScreenStep.fillingInFieldsWithValidData();
        editingClaimsScreenStep.clickingOnTheUndoEditButton();
        editingClaimsScreenStep.clickingOnTheButtonToConfirmTheCancellationOfEditing();

        claimsScreenStep.checkingTheOpenStatus();
        String executorTextItWasHasBecome = claimsScreenStep.executorText();
        String planDateTextItWasHasBecome = claimsScreenStep.planDateText();
        String timeTextItWasHasBecome = claimsScreenStep.timeText();
        String authorTextItWasHasBecome = claimsScreenStep.authorText();

        claimsScreenStep.comparisonOfDataBeforeAndAfterEditing(
                executorTextItWas, executorTextItWasHasBecome, planDateTextItWas, planDateTextItWasHasBecome,
                timeTextItWas, timeTextItWasHasBecome, authorTextItWas, authorTextItWasHasBecome);
    }

    @Test
    @DisplayName("Canceling the comment field when selecting To execute")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"CANCEL\" происходит отмена заполнения поля для коментария и дальнейшей смены статуса на \" Еxecuted")
    public void cancelingTheCommentFieldWhenSelectingToExecute() {
        String text = textSymbol(5);

        claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheOpenStatus();

        Helper.Swipes.swipeToBottom();
        claimsScreenStep.clickingOnTheButtonWithTheNotepadIconWithGear();
        claimsScreenStep.clickingOnTakeToWork();
        claimsScreenStep.checkingTheInProgressStatus();
        claimsScreenStep.clickingOnTheButtonWithTheNotepadIconWithGear();
        claimsScreenStep.clickingOnReset();
        claimsScreenStep.fillingInTheCommentField(text);
        String commentText = claimsScreenStep.commentText();
        claimsScreenStep.clickingOnTheButtonToCancelAddingComment();

        claimsScreenStep.checkingTheAbsenceOfComment(commentText);
        claimsScreenStep.checkingTheInProgressStatus();
    }

    @Test
    @DisplayName("Canceling the comment field when selecting Throw off")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на \"Throw off\" сбрасывается статус с \"In progress\" на \"Open\"")
    public void cancelingTheCommentFieldWhenSelectingThrowOff() {
        String text = textSymbol(5);

        createClaim();

        claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheOpenStatus();

        Helper.Swipes.swipeToBottom();
        claimsScreenStep.clickingOnTheButtonWithTheNotepadIconWithGear();
        claimsScreenStep.clickingOnTakeToWork();
        claimsScreenStep.checkingTheInProgressStatus();
        claimsScreenStep.clickingOnTheButtonWithTheNotepadIconWithGear();
        claimsScreenStep.clickingOnReset();
        claimsScreenStep.fillingInTheCommentField(text);
        claimsScreenStep.clickingOnTheOkButtonToAddComment();
        claimsScreenStep.checkingTheVisibilityOfTheAddedComment();
        claimsScreenStep.checkingTheOpenStatus();
    }

    @Test
    @DisplayName("The status should change to executed")
    @Description("В этом тест кейсе мы проверяем смену статуса In Progress на статус executed")
    public void theStatusShouldChangeToExecuted() {
        String text = textSymbol(5);

        createClaim();

        claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheOpenStatus();
        Helper.Swipes.swipeToBottom();

        claimsScreenStep.clickingOnTheButtonWithTheNotepadIconWithGear();
        claimsScreenStep.clickingOnTakeToWork();
        claimsScreenStep.checkingTheInProgressStatus();
        Helper.Swipes.swipeToBottom();
        claimsScreenStep.clickingOnTheButtonWithTheNotepadIconWithGear();
        claimsScreenStep.clickingOnToExecute();
        claimsScreenStep.fillingInTheCommentField(text);
        claimsScreenStep.clickingOnTheOkButtonToAddComment();
        claimsScreenStep.checkingTheVisibilityOfTheAddedComment();
        claimsScreenStep.checkingTheExecutedStatus();
    }

    @Test
    @DisplayName("The status should be reset")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на \"Throw off\" сбрасывается статус с \"In progress\" на \"Open\"")
    public void theStatusShouldBeReset() {
        String text = textSymbol(5);

        claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheOpenStatus();
        Helper.Swipes.swipeToBottom();

        claimsScreenStep.clickingOnTheButtonWithTheNotepadIconWithGear();
        claimsScreenStep.clickingOnTakeToWork();
        Helper.Swipes.swipeToTop();
        claimsScreenStep.checkingTheInProgressStatus();
        claimsScreenStep.clickingOnTheButtonWithTheNotepadIconWithGear();
        claimsScreenStep.clickingOnReset();
        claimsScreenStep.fillingInTheCommentField(text);
        claimsScreenStep.clickingOnTheOkButtonToAddComment();
        claimsScreenStep.checkingTheVisibilityOfTheAddedComment();
        claimsScreenStep.checkingTheOpenStatus();
    }

//    @Ignore("TODO")
    @Ignore
    @Test
    @DisplayName("The status should change to cancelled")
    @Description("В этом тест кейсе мы проверяем смену статуса с Open на Canceled")
    public void theStatusShouldChangeToCancelled() {
        claimsScreenStep.pressingOnTheButtonToGoToTheFilteringScreen();
        filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
        filteringWindowScreenStep.clickingOnTheOkButton();
        claimsScreenStep.clickingOnRandomlySelectedClaim(position);
        claimsScreenStep.checkingTheOpenStatus();
        Helper.Swipes.swipeToBottom();

        claimsScreenStep.clickingOnTheButtonWithTheNotepadIconWithGear();
        claimsScreenStep.clickingOnToCancel();
        Helper.Swipes.swipeToTop();
        claimsScreenStep.checkingTheCanceledStatus();
    }
}
