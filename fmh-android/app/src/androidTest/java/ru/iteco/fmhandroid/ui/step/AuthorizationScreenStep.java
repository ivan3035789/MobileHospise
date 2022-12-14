package ru.iteco.fmhandroid.ui.step;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static ru.iteco.fmhandroid.ui.data.Helper.invalidAuthInfo;
import static ru.iteco.fmhandroid.ui.data.Helper.invalidLoginPasswordAuthInfo;

import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.screenElements.AuthorizationScreenElements;

public class AuthorizationScreenStep {

    AuthorizationScreenElements authorizationScreenElements = new AuthorizationScreenElements();

    public void clickingTheExitProfileButton() {
        Allure.step("Нажатие кнопки выхода из профиля");
        ViewInteraction user = onView((withId(R.id.authorization_image_button)));
        user.perform(click());
        ViewInteraction exitButton = onView(withText("Log out"));
        exitButton.perform(click());
        SystemClock.sleep(5000);
    }

    public void checkingTheNameOfTheAuthorizationScreen() {
        Allure.step("Проверка названия Экрана Авторизации");
        authorizationScreenElements.getAuthorization().check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingIdentifyingFieldNames() {
        Allure.step("Проверка идентифицирующих названий полей");
        authorizationScreenElements.getLoginField().check(matches(isDisplayed()));
        authorizationScreenElements.getPasswordField().check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void validLoginPassword(Helper.AuthInfo info) {
        Allure.step("Ввод валидного Login Password");
        checkingTheNameOfTheAuthorizationScreen();
        authorizationScreenElements.getLoginField().perform(typeText(info.getLogin()));
        authorizationScreenElements.getPasswordField().perform(typeText(info.getPassword())).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
        authorizationScreenElements.getButton().perform(click());
        SystemClock.sleep(5000);
    }

    public void invalidAuthorization() {
        Allure.step("Попытка авторизации при вводе невалидного login или Password");
        checkingTheNameOfTheAuthorizationScreen();
        authorizationScreenElements.getLoginField().perform(typeText(invalidAuthInfo().getLogin()));
        authorizationScreenElements.getPasswordField().perform(typeText(invalidAuthInfo().getPassword())).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
        authorizationScreenElements.getButton().perform(click());
    }

    public void invalidAuthorizationLoginPassword() {
        Allure.step("Попытка авторизации при вводе невалидного login и Password");
        checkingTheNameOfTheAuthorizationScreen();
        authorizationScreenElements.getLoginField().perform(typeText(invalidLoginPasswordAuthInfo().getLogin()));
        authorizationScreenElements.getPasswordField().perform(typeText(invalidLoginPasswordAuthInfo().getPassword())).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
        authorizationScreenElements.getButton().perform(click());
    }

    public void validLanguage(String loginPassword) {
        Allure.step("Ввод английских букв");
        authorizationScreenElements.getLoginField().perform(typeText(loginPassword));
        authorizationScreenElements.getPasswordField().perform(typeText(loginPassword)).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
    }

    public void invalidLanguage(String loginPassword) {
        Allure.step("Ввод русских букв");
        authorizationScreenElements.getLoginField().perform(typeText(loginPassword));
        authorizationScreenElements.getPasswordField().perform(typeText(loginPassword)).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
    }

    public void checkingThePresenceOfTheEnteredDataInTheFields(String loginPassword) {
        Allure.step("Проверка присутствия в полях введенных данных");
        authorizationScreenElements.getLoginField().check(matches(isDisplayed()));
        authorizationScreenElements.getPasswordField().check(matches(isDisplayed()));
        authorizationScreenElements.getLoginField().check(matches(withText(loginPassword)));
        authorizationScreenElements.getPasswordField().check(matches(withText(loginPassword)));
        SystemClock.sleep(3000);
    }

    public void checkingTheAbsenceOfTheEnteredDataInTheFields(String loginPassword) {
        Allure.step("Проверка отсутствия в полях введенных данных");
        authorizationScreenElements.getLoginField().check(matches(isDisplayed()));
        authorizationScreenElements.getPasswordField().check(matches(isDisplayed()));
        authorizationScreenElements.getLoginField().check(matches(not(withText(loginPassword))));
        authorizationScreenElements.getPasswordField().check(matches(not(withText(loginPassword))));
        SystemClock.sleep(3000);
    }

    public void checkingTheLoginAndPasswordCannotBeEmpty(@NonNull AppActivity activity, String text) {
        Allure.step("Проверка появления предупреждающего сообщения Login and password cannot be empty");
        onView(withText(text)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingTheWrongLoginOrPassword(@NonNull AppActivity activity, String text) {
        Allure.step("Проверка появления предупреждающего сообщения Wrong login or password");
        onView(withText(text)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }
}