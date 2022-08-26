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

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AuthorizationScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    MainScreenStep mainScreenStep = new MainScreenStep();

    String MessageEmpty = "Login and password cannot be empty";
    String messageWrong = "Wrong login or password";

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            authorizationScreenStep.checkingTheNameOfTheAuthorizationScreen();
        } catch (NoMatchingViewException e) {
            authorizationScreenStep.clickingTheExitProfileButton();
        } 
    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("There should be a name authorization")
    @Description("В этом тест кейсе мы проверяем название страницы для авторизации пользователя (страница \"Authorization\")")
    public void thereShouldBeNameAuthorization() {
        authorizationScreenStep.checkingTheNameOfTheAuthorizationScreen();
    }

    @Test
    @DisplayName("login and password fields should be displayed")
    @Description("В этом тест кейсе мы проверяем, что в полях для ввода, присудствуют идентифицирующие названия полей  (login,password), соответствующие вводимым данным")
    public void loginAndPasswordFieldsShouldBeDisplayed() {
        authorizationScreenStep.checkingIdentifyingFieldNames();
    }

    @Test
    @DisplayName("The fields must be filled in with English letters")
    @Description("В этом тест кейсе мы проверяем, что поля заполняются латинскими буквами")
    public void theFieldsMustBeFilledInWithEnglishLetters() {
        String loginPassword = "loginAndPassword";

        authorizationScreenStep.validLanguage(loginPassword);
        authorizationScreenStep.checkingThePresenceOfTheEnteredDataInTheFields(loginPassword);
    }

    @Test
    @DisplayName("Fields should not be filled in with Russian letters")
    @Description("В этом тест кейсе мы проверяем, что поля незаполняются нелатинскими буквами")
    public void fieldsShouldNotBeFilledInWithRussianLetters() {
        String invalidLoginPasswordText = "привет мир";

        try {
            authorizationScreenStep.invalidLanguage(invalidLoginPasswordText);
        } catch (RuntimeException ignored) {

        } finally {
            authorizationScreenStep.checkingTheAbsenceOfTheEnteredDataInTheFields(invalidLoginPasswordText);
        }
    }

    @Test
    @DisplayName("Must Log In")
    @Description("В этом тест кейсе мы проверяем, что при вводе правильного логина и пароля пользователь входит в систему")
    public void mustLogIn() {
        authorizationScreenStep.validLoginPassword(authInfo());
        mainScreenStep.checkNameMainScreen();
    }

    @Test
    @DisplayName("a Warning Message Should Appear If The Fields Are Blank")
    @Description("В этом тест кейсе мы проверяем, что при невводе логина или пароля появляется предупреждающая надпись Login and password cannot be empty")
    public void aWarningMessageShouldAppearIfTheFieldsAreBlank() {
        authorizationScreenStep.invalidAuthorization();
        authorizationScreenStep.checkingTheLoginAndPasswordCannotBeEmpty(ActivityTestRule.getActivity(), MessageEmpty);
    }

    @Test
    @DisplayName("Warning messages should appear when entering an incorrect password")
    @Description("В этом тест кейсе мы проверяем, что при вводе неправильного логина или пароля пользователь не входит в систему, появляется надпись Wrong login or password")
    public void warningMessagesShouldAppearWhenEnteringAnIncorrectPassword() {
        authorizationScreenStep.invalidAuthorizationLoginPassword();
        authorizationScreenStep.checkingTheWrongLoginOrPassword(ActivityTestRule.getActivity(), messageWrong);
    }

    @Test
    @DisplayName("Warning Messages Should Appear When You Enter Space")
    @Description("В этом тест кейсе мы проверяем, что при невведенном логине и пароле появлется предупреждающая надпись Login and password cannot be empty")
    public void warningMessagesShouldAppearWhenYouEnterSpace() {
        authorizationScreenStep.invalidAuthorization();
        authorizationScreenStep.checkingTheLoginAndPasswordCannotBeEmpty(ActivityTestRule.getActivity(), MessageEmpty);
    }

    @Test
    @DisplayName("Warning Messages Should Appear When The Password Field Is Blank")
    @Description("В этом тест кейсе мы проверяем, что при невведенном пароле появлется предупреждающая надпись Login and password cannot be empty")
    public void warningMessagesShouldAppearWhenThePasswordFieldIsBlank() {
        authorizationScreenStep.invalidAuthorization();
        authorizationScreenStep.checkingTheLoginAndPasswordCannotBeEmpty(ActivityTestRule.getActivity(), MessageEmpty);
    }

    @Test
    @DisplayName("Warning Messages Should Appear When The Login Field Is Empty")
    @Description("В этом тест кейсе мы проверяем, что при невведенном логине появлется предупреждающая надпись Login and password cannot be empty")
    public void warningMessagesShouldAppearWhenTheLoginFieldIsEmpty() {
        authorizationScreenStep.invalidAuthorization();
        authorizationScreenStep.checkingTheLoginAndPasswordCannotBeEmpty(ActivityTestRule.getActivity(), MessageEmpty);
    }

    @Test
    @DisplayName("Must log out of profile")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку выхода и нажатии log out, пользователь выходит из профиля")
    public void mustLogOutOfProfile() {
        authorizationScreenStep.validLoginPassword(authInfo());
        mainScreenStep.checkNameMainScreen();
        authorizationScreenStep.clickingTheExitProfileButton();
        authorizationScreenStep.checkingTheNameOfTheAuthorizationScreen();
    }
}