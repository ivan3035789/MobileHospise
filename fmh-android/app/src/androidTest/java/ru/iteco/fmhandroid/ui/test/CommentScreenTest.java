package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomClaims;
import static ru.iteco.fmhandroid.ui.data.Helper.Text.textSymbol;
import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;

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
import ru.iteco.fmhandroid.ui.step.CommentScreenStep;
import ru.iteco.fmhandroid.ui.step.FilteringWindowScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class CommentScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    MainScreenStep mainScreenStep = new MainScreenStep();
    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    ClaimsScreenStep claimsScreenStep = new ClaimsScreenStep();
    CommentScreenStep commentScreenStep = new CommentScreenStep();
    FilteringWindowScreenStep filteringWindowScreenStep = new FilteringWindowScreenStep();

    int position = randomClaims(1);
    int positionComment = 0;
    String message = "The field cannot be empty.";
    String invalidLanguageTextComment = "Привет мир";

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
            filteringWindowScreenStep.clickingOnTheCheckBoxInProgress();
            filteringWindowScreenStep.clickingOnTheOkButton();
            claimsScreenStep.clickingOnRandomlySelectedClaim(position);
            Helper.Swipes.swipeToBottom();
            claimsScreenStep.clickingOnTheAddCommentButton();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }


    @Test
    @DisplayName("Must enter the comment creation section")
    @Description("В этом тест кейсе мы проверяем, что при клике на кнопку \"+\" в поле \"Add comment\" пользователь переходит в раздел создания коментария ")
    public void mustEnterTheCommentCreationSection() {
//        Helper.Swipes.swipeToBottom();
//        SystemClock.sleep(5000);
//        claimsScreenStep.clickingOnTheAddCommentButton();
//        SystemClock.sleep(3000);
        commentScreenStep.checkingTheEntryToTheCommentScreen();
    }

    @Test
    @DisplayName("Fields should not be filled in with Russian letters")
    @Description("В этом тест кейсе мы проверяем, что поле \"comment\", не заполняется русскими буквами")
    public void fieldsShouldNotBeFilledInWithRussianLetters() {

//        Helper.Swipes.swipeToBottom();
//        SystemClock.sleep(3000);
//        claimsScreenStep.clickingOnTheAddCommentButton();
        try {
            commentScreenStep.enteringAnIncorrectLanguageTextComment(invalidLanguageTextComment);
        } catch (RuntimeException e) {

        } finally {
            commentScreenStep.checkTheFieldIsNotFilledWithText();
        }
    }

    @Test
    @DisplayName("The fields must be filled in with English letters")
    @Description("В этом тест кейсе мы проверяем, что поле \"comment\", заполняется английскими буквами")
    public void theFieldsMustBeFilledInWithEnglishLetters() {
        String validTextComment = textSymbol(5);

//        Helper.Swipes.swipeToBottom();
//        claimsScreenStep.clickingOnTheAddCommentButton();
        commentScreenStep.validLanguageTextComment(validTextComment);
        commentScreenStep.checkTheFieldIsFilledWithText(validTextComment);
    }

    @Test
    @DisplayName("A comment should be added")
    @Description("В этом тест кейсе мы проверяем что после заполнения поля \"comment\" коментарием, и после нажатия на кнопку \"SAVE\", должен добавиться коментарий")
    public void commentShouldBeAdded() {
        String validTextComment = textSymbol(5);

//        Helper.Swipes.swipeToBottom();
//        claimsScreenStep.clickingOnTheAddCommentButton();
        commentScreenStep.validLanguageTextComment(validTextComment);
        commentScreenStep.clickingOnTheSaveCommentButton();
        Helper.Swipes.swipeToBottom();

        claimsScreenStep.checkingCommentShouldBeAdded(validTextComment);
    }

    @Test
    @DisplayName("Canceling adding a comment")
    @Description("В этом тест кейсе мы проверяем что после заполнения поля \"comment\" коментарием, и после нажатия на кнопку \"CANCEL\", не должен добавиться коментарий")
    public void cancelingAddingComment() {
        String validTextComment = textSymbol(5);

//        Helper.Swipes.swipeToBottom();
//
//        claimsScreenStep.clickingOnTheAddCommentButton();
//        SystemClock.sleep(3000);
        commentScreenStep.validLanguageTextComment(validTextComment);
        commentScreenStep.clickingOnTheButtonToCancelAddingComment();
        claimsScreenStep.checkingTheCommentShouldNotBeCreated(validTextComment);
    }

    @Test
    @DisplayName("A warning message should appear when the comment field is empty")
    @Description("В этом тест кейсе мы проверяем что при незаполнении поля \"comment\", после нажатия на кнопку \"SAVE\", появляется предупреждающая надпись \"The field cannot be empty\" ")
    public void warningMessageShouldAppearWhenTheCommentFieldIsEmpty() {
//        Helper.Swipes.swipeToBottom();
//        SystemClock.sleep(3000);
//        claimsScreenStep.clickingOnTheAddCommentButton();
        commentScreenStep.clickingOnTheSaveCommentButton();
        commentScreenStep.checkingTheFieldCannotBeEmpty(ActivityTestRule.getActivity(), message);
    }
    @Ignore
    @Test
    @DisplayName("The comment in the claim should be edited")
    @Description("В этом тест кейсе мы проверяем что при нажатии на кнопку с иконкой \"блокнот с карандашом\" пользователь попадает в раздел создания, редактирования коментариев, имеется возможность отредактировать коментарий")
    public void theCommentInTheClaimShouldBeEdited() {
        String validTextComment1 = textSymbol(5);
        String validTextComment2 = textSymbol(5);

        commentScreenStep.validLanguageTextComment(validTextComment1);
        commentScreenStep.clickingOnTheSaveCommentButton();

        claimsScreenStep.clickingOnTheButtonToEnterTheCommentEditingScreen(positionComment);
        commentScreenStep.validLanguageTextComment(validTextComment2);
        commentScreenStep.clickingOnTheSaveCommentButton();
        claimsScreenStep.checkingTheCommentBeforeEditingAndAfter(validTextComment2);
    }

    @Test
    @DisplayName("Two comments should be added")
    @Description("В этом тест кейсе мы проверяем что имеется возможность добавить несколько коментариев в одину \"претензию\" ")
    public void twoCommentsShouldBeAdded() {
        String validTextComment1 = textSymbol(5);
        String validTextComment2 = textSymbol(5);

//        Helper.Swipes.swipeToBottom();
//        SystemClock.sleep(5000);
//        claimsScreenStep.clickingOnTheAddCommentButton();
//        SystemClock.sleep(3000);
        commentScreenStep.validLanguageTextComment(validTextComment1);
        commentScreenStep.clickingOnTheSaveCommentButton();
        Helper.Swipes.swipeToBottom();
        claimsScreenStep.checkingTheAddedComment(validTextComment1);
        claimsScreenStep.clickingOnTheAddCommentButton();
        commentScreenStep.validLanguageTextComment(validTextComment2);
        commentScreenStep.clickingOnTheSaveCommentButton();
        Helper.Swipes.swipeToBottom();
        claimsScreenStep.checkingTheAddedComment(validTextComment2);
    }
}
