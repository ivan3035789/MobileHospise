package ru.iteco.fmhandroid.ui.step;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomExecutor;
import static ru.iteco.fmhandroid.ui.data.Helper.Text.textSymbol;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import android.os.SystemClock;

import androidx.annotation.NonNull;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.screenElements.CalendarScreenElements;
import ru.iteco.fmhandroid.ui.screenElements.ClaimsScreenElements;
import ru.iteco.fmhandroid.ui.screenElements.EditingClaimsScreenElements;
import ru.iteco.fmhandroid.ui.screenElements.FilteringWindowScreenElements;
import ru.iteco.fmhandroid.ui.screenElements.WatchScreenElements;

public class EditingClaimsScreenStep {

    EditingClaimsScreenElements editingClaimsScreenElements = new EditingClaimsScreenElements();

    public void goToTheClaimCardWithTheOpenStatus(int position) {
        Allure.step("?????????????? ?? ???????????????? Claim ???? ???????????????? Open");
        ClaimsScreenElements claimsScreenElements = new ClaimsScreenElements();
        FilteringWindowScreenElements filteringWindowScreenElements = new FilteringWindowScreenElements();
        MainScreenStep mainScreenStep = new MainScreenStep();

        mainScreenStep.clickingOnTheActionMenuButton();
        mainScreenStep.clickingOnTheClaimsName();
        claimsScreenElements.getFilteringButton().perform(click());
        filteringWindowScreenElements.getCheckBoxInProgress().perform(click());
        filteringWindowScreenElements.getOkButton().perform(click());
        SystemClock.sleep(3000);
        claimsScreenElements.getBlockClaims().perform(actionOnItemAtPosition(position, click()));
        SystemClock.sleep(3000);
    }

    public void clickingOnTheSaveButton() {
        Allure.step("?????????????? ???? ???????????? ??????????????????");
        editingClaimsScreenElements.getSaveButton().perform(click());
    }

    public void clickingOnTheUndoEditButton() {
        Allure.step("?????????????? ???? ???????????? ???????????? ????????????????????????????");
        editingClaimsScreenElements.getCancelButton().perform(click());
        SystemClock.sleep(3000);
    }

    public void clickingOnRandomlySelectedArtist(@NonNull AppActivity activity, String executor) {
        Allure.step("?????????????? ???? ?????????????????????? ???????????????????? ?????????????????? ??????????????");
        onView(withText(executor))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).perform(click());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheCancelButtonToExitEditingEditing() {
        Allure.step("?????????????? ???? ????????????  ???????????? ???????????? ???? ???????????????????????????? ????????????????????????????");
        editingClaimsScreenElements.getCancelButton2().perform(scrollTo(), click());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheButtonToConfirmTheCancellationOfEditing() {
        Allure.step("?????????????? ???? ???????????? ?????????????????????????? ???????????? ????????????????????????????");
        editingClaimsScreenElements.getOkButton().perform(scrollTo(), click());
        SystemClock.sleep(3000);
    }

    public void deletingTextFromTheTitleField() {
        Allure.step("???????????????? ???????????? ???? ???????? Title");
        editingClaimsScreenElements.getTitleClaimField().perform(replaceText(""), closeSoftKeyboard());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheExecutorField() {
        Allure.step("?????????????? ???? ???????? Executor");
        editingClaimsScreenElements.getExecutorClaimField().perform(click());
        editingClaimsScreenElements.getExecutorClaimField().perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheDateField() {
        Allure.step("?????????????? ???? ???????? Date");
        editingClaimsScreenElements.getDateClaimField().perform(click());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheTimeField() {
        Allure.step("?????????????? ???? ???????? Time");
        editingClaimsScreenElements.getTimeClaimField().perform(click());
        SystemClock.sleep(3000);
    }

    public void invalidLanguage(String title) {
        Allure.step("???????? ?????????????????????? ??????????");
        editingClaimsScreenElements.getTitleClaimField().perform(replaceText("")).perform(closeSoftKeyboard());
        SystemClock.sleep(2000);
        editingClaimsScreenElements.getExecutorClaimField().perform(replaceText("")).perform(closeSoftKeyboard());
        SystemClock.sleep(2000);
        editingClaimsScreenElements.getDescriptionClaimField().perform(replaceText("")).perform(closeSoftKeyboard());
        SystemClock.sleep(2000);
        try {
            editingClaimsScreenElements.getTitleClaimField().perform(typeText(title)).perform(closeSoftKeyboard());
        } catch (RuntimeException e) {
            editingClaimsScreenElements.getTitleClaimField().perform(closeSoftKeyboard());
        }
        SystemClock.sleep(2000);
        try {
            editingClaimsScreenElements.getExecutorClaimField().perform(typeText(title)).perform(closeSoftKeyboard());
        } catch (RuntimeException e) {

        }
        SystemClock.sleep(2000);
        try {
            editingClaimsScreenElements.getDescriptionClaimField().perform(typeText(title)).perform(closeSoftKeyboard());
        } catch (RuntimeException e) {
            editingClaimsScreenElements.getExecutorClaimField().perform(closeSoftKeyboard());
        }
        SystemClock.sleep(3000);
    }

    public void validLanguage(String title) {
        Allure.step("???????? ?????????????????? ??????????");
        editingClaimsScreenElements.getTitleClaimField().perform(replaceText(title));
        editingClaimsScreenElements.getExecutorClaimField().perform(replaceText(title));
        editingClaimsScreenElements.getDescriptionClaimField().perform(replaceText(title)).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
    }

    public void fillingInFieldsWithValidData() {
        Allure.step("???????????????????? ?????????? ?????????????????? ??????????????");
        WatchScreenElements watchScreenElements = new WatchScreenElements();
        CalendarScreenElements calendarScreenElements = new CalendarScreenElements();
        editingClaimsScreenElements.getTitleClaimField().perform(replaceText(textSymbol(5)), closeSoftKeyboard());
        SystemClock.sleep(2000);
        editingClaimsScreenElements.getExecutorClaimField().perform(replaceText(randomExecutor()), closeSoftKeyboard());
        SystemClock.sleep(2000);
        editingClaimsScreenElements.getDateClaimField().perform(click());
        watchScreenElements.getOkButton().perform(scrollTo(), click());
        SystemClock.sleep(2000);
        editingClaimsScreenElements.getTimeClaimField().perform(click());
        calendarScreenElements.getOkButton().perform(scrollTo(), click());
        SystemClock.sleep(2000);
        editingClaimsScreenElements.getDescriptionClaimField().perform(replaceText(textSymbol(5)), closeSoftKeyboard());
        SystemClock.sleep(3000);
    }

    public void fillingInTheExecutorField(String text) {
        Allure.step("???????????????????? ???????? Executor");
        editingClaimsScreenElements.getExecutorClaimField().perform(replaceText(text));
        SystemClock.sleep(3000);
    }

    public void checkingTheFieldsAreFilledIn() {
        Allure.step("???????????????? ???????????????????? ??????????");
        editingClaimsScreenElements.getTitleClaimField().check(matches(isDisplayed()));
        editingClaimsScreenElements.getExecutorClaimField().check(matches(isDisplayed()));
        editingClaimsScreenElements.getDateClaimField().check(matches(isDisplayed()));
        editingClaimsScreenElements.getTimeClaimField().check(matches(isDisplayed()));
        editingClaimsScreenElements.getDescriptionClaimField().check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingTheNameOfTheScreenForEditingClaims() {
        Allure.step("???????????????? ???????????????? ???????????? ?????? ???????????????????????????? ??????????????????");
        editingClaimsScreenElements.getEditingName().check(matches(isDisplayed()));
        editingClaimsScreenElements.getClaimsName().check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingDataBeforeEditingAndAfter(
            String titleClaimFieldItWas, String titleClaimFieldItWasHasBecome, String executorClaimFieldItWas, String executorClaimFieldItWasHasBecome,
            String dateClaimFieldItWas, String dateClaimFieldItWasHasBecome, String timeClaimFieldItWas, String timeClaimFieldItWasHasBecome,
            String descriptionClaimFieldItWas, String descriptionClaimFieldItWasHasBecome) {
        Allure.step("???????????????? ???????????? ???? ???????????????????????????? ?? ??????????");
        assertEquals(titleClaimFieldItWas, titleClaimFieldItWasHasBecome);
        assertEquals(executorClaimFieldItWas, executorClaimFieldItWasHasBecome);
        if (dateClaimFieldItWas.equals(dateClaimFieldItWasHasBecome)) {
            assertEquals(dateClaimFieldItWas, dateClaimFieldItWasHasBecome);
        } else {
            assertNotEquals(dateClaimFieldItWas, dateClaimFieldItWasHasBecome);
        }
        assertNotEquals(timeClaimFieldItWas, timeClaimFieldItWasHasBecome);
        assertNotEquals(descriptionClaimFieldItWas, descriptionClaimFieldItWasHasBecome);
        SystemClock.sleep(3000);
    }

    public void comparisonOfDataBeforeAndAfterEditing(
            String titleClaimFieldItWas, String titleClaimFieldItWasHasBecome, String executorClaimFieldItWas,
            String executorClaimFieldItWasHasBecome, String dateClaimFieldItWas, String dateClaimFieldItWasHasBecome,
            String timeClaimFieldItWas, String timeClaimFieldItWasHasBecome, String descriptionClaimFieldItWas, String descriptionClaimFieldItWasHasBecome) {
        assertNotEquals(titleClaimFieldItWas, titleClaimFieldItWasHasBecome);
        assertEquals(executorClaimFieldItWas, executorClaimFieldItWasHasBecome);
        if (dateClaimFieldItWas.equals(dateClaimFieldItWasHasBecome)) {
            Allure.step("?????????????????? ???????????? ???? ???????????????????????????? ?? ??????????");
            assertEquals(dateClaimFieldItWas, dateClaimFieldItWasHasBecome);
        } else {
            assertNotEquals(dateClaimFieldItWas, dateClaimFieldItWasHasBecome);
        }
        assertNotEquals(timeClaimFieldItWas, timeClaimFieldItWasHasBecome);
        assertNotEquals(descriptionClaimFieldItWas, descriptionClaimFieldItWasHasBecome);
        SystemClock.sleep(3000);
    }

    public void verificationOfClaimDataBeforeEditingAndAfterCancellationOfEditing(
            String titleClaimFieldItWas, String titleClaimFieldItWasHasBecome, String executorClaimFieldItWas, String executorClaimFieldItWasHasBecome,
            String dateClaimFieldItWas, String dateClaimFieldItWasHasBecome, String timeClaimFieldItWas, String timeClaimFieldItWasHasBecome,
            String descriptionClaimFieldItWas, String descriptionClaimFieldItWasHasBecome
    ) {
        Allure.step("???????????????? ???????????? ?????????????????? ???? ???????????????????????????? ?? ?????????? ???????????? ????????????????????????????");
        assertEquals(titleClaimFieldItWas, titleClaimFieldItWasHasBecome);
        assertEquals(executorClaimFieldItWas, executorClaimFieldItWasHasBecome);
        assertEquals(dateClaimFieldItWas, dateClaimFieldItWasHasBecome);
        assertEquals(timeClaimFieldItWas, timeClaimFieldItWasHasBecome);
        assertEquals(descriptionClaimFieldItWas, descriptionClaimFieldItWasHasBecome);
        SystemClock.sleep(3000);
    }

    public void checkingForThePresenceOfWordsFromEnglishLettersInTheFields(String validLanguageText) {
        Allure.step("???????????????? ???? ?????????????????????? ?? ?????????? ???????? ???? ???????????????????? ????????");
        editingClaimsScreenElements.getTitleClaimField().check(matches(withText(validLanguageText))).check(matches(isDisplayed()));
        editingClaimsScreenElements.getExecutorClaimField().check(matches(withText(validLanguageText))).check(matches(isDisplayed()));
        editingClaimsScreenElements.getDescriptionClaimField().check(matches(withText(validLanguageText))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingForTheAbsenceOfWordsFromRussianLettersInTheFields() {
        Allure.step("???????????????? ???? ???????????????????? ?? ?????????? ???????? ???? ?????????????? ????????");
        editingClaimsScreenElements.getTitleClaimField().check(matches(withText(""))).check(matches(isDisplayed()));
        editingClaimsScreenElements.getExecutorClaimField().check(matches(withText(""))).check(matches(isDisplayed()));
        editingClaimsScreenElements.getDescriptionClaimField().check(matches(withText(""))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingThePerformerBeforeEditingAndAfter(
            String executorClaimFieldItWas, String executorClaimFieldItWasHasBecome, String executorClaimFieldInputText) {
        Allure.step("???????????????? ?????????????????????? ???? ???????????????????????????? ?? ??????????");
        if (executorClaimFieldItWas.equals("NOT ASSIGNED")) {
            assertEquals(executorClaimFieldItWas, executorClaimFieldItWasHasBecome);
        } else {
            assertNotEquals(executorClaimFieldInputText, executorClaimFieldItWasHasBecome);
        }
        SystemClock.sleep(3000);
    }

    public void checkingTheCalendarAppearance(@NonNull AppActivity activity) {
        Allure.step("???????????????? ?????????????????? ??????????????????");
        onView(withClassName(is("android.widget.DatePicker")))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingTheAppearanceOfTheDropDownList(@NonNull AppActivity activity) {
        Allure.step("???????????????? ?????????????????? ?????????????????????? ????????????");
        onView(withClassName(is("android.widget.PopupWindow$PopupBackgroundView")))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingTheAppearanceOfClockOfTheArrowType(@NonNull AppActivity activity) {
        Allure.step("???????????????? ?????????????????? ?????????? ?????????????????????? ????????");
        onView(withClassName(is("android.widget.RadialTimePickerView")))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingTheFillEmptyFields(@NonNull AppActivity activity, String text) {
        Allure.step("???????????????? ?????????????????? ???????????????????????????????? ?????????????????? Fill empty fields");
        onView(withText(text)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public String titleClaimField() {
        return Helper.Text.getText(onView(withIndex(withId(R.id.title_text_view), 0)));
    }

    public String executorClaimField() {
        return Helper.Text.getText(onView(withIndex(withId(R.id.executor_name_text_view), 0)));
    }

    public String executorClaim() {
        return  Helper.Text.getText(editingClaimsScreenElements.getExecutorClaimField());
    }

    public String dateClaimField() {
        return Helper.Text.getText(onView(withIndex(withId(R.id.plane_date_text_view), 0)));
    }

    public String timeClaimField() {
        return Helper.Text.getText(onView(withIndex(withId(R.id.plan_time_text_view), 0)));
    }

    public String descriptionClaimField() {
        return Helper.Text.getText(onView(withId(R.id.description_text_view)));
    }
}
