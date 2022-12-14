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
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import android.os.SystemClock;

import androidx.annotation.NonNull;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.screenElements.CalendarScreenElements;
import ru.iteco.fmhandroid.ui.screenElements.ControlPanelScreenElements;
import ru.iteco.fmhandroid.ui.screenElements.CreatingNewsScreenElements;
import ru.iteco.fmhandroid.ui.screenElements.WatchScreenElements;

public class CreatingNewsScreenStep {

    CreatingNewsScreenElements creatingNewsScreenElements = new CreatingNewsScreenElements();

    public void clickingOnTheCategoryField() {
        Allure.step("Нажатие на поле категория");
        creatingNewsScreenElements.getCategoryFieldNews().perform(click()).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheDateField() {
        Allure.step("Нажатие на поле дата");
        creatingNewsScreenElements.getPublicationDateFieldNews().perform(click());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheTimeField() {
        Allure.step("Нажатие на поле время");
        creatingNewsScreenElements.getTimeFieldNews().perform(click());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheExitButtonFromNewsCreation() {
        Allure.step("Нажатие на кнопку выхода из создания новости");
        creatingNewsScreenElements.getCancelButton().perform(click());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheConfirmationButtonToExitTheNewsCreation() {
        Allure.step("Нажатие на кнопку подтверждения выхода из создания новости");
        creatingNewsScreenElements.getOkButton().perform(scrollTo(), click());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheSaveNewsButton() {
        Allure.step("Нажатие на кнопку сохранения новости");
        creatingNewsScreenElements.getSaveButton().perform(click());
    }

    public void clickingOnTheCancelNewsCreationButton() {
        Allure.step("Нажатие на кнопку отмены создания новости");
        creatingNewsScreenElements.getCancelButton2().perform(scrollTo(), click());
        SystemClock.sleep(3000);
    }

    public void clickingOnTheConfirmationButtonToCancelTheCreationOfTheNews() {
        Allure.step("Нажатие на кнопку подтверждения отмены создания новости");
        creatingNewsScreenElements.getOkButton().perform(scrollTo(), click());
        SystemClock.sleep(3000);
    }

    public void validLanguage(String title) {
        Allure.step("Ввод валидного языка");
        creatingNewsScreenElements.getCategoryFieldNews().perform(typeText(title)).perform(closeSoftKeyboard());
        SystemClock.sleep(2000);
        creatingNewsScreenElements.getTitleFieldNews().perform(typeText(title)).perform(closeSoftKeyboard());
        SystemClock.sleep(2000);
        creatingNewsScreenElements.getDescriptionFieldNews().perform(typeText(title)).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
    }

    public void invalidLanguage(String title) {
        Allure.step("Ввод невалидного языка");
        try {
            creatingNewsScreenElements.getTitleFieldNews().perform(typeText(title));
        } catch (RuntimeException e) {
            creatingNewsScreenElements.getTitleFieldNews().perform(closeSoftKeyboard());
        }
        SystemClock.sleep(2000);
        try {
            creatingNewsScreenElements.getDescriptionFieldNews().perform(typeText(title));
        } catch (RuntimeException e) {
            creatingNewsScreenElements.getDescriptionFieldNews().perform(closeSoftKeyboard());
        }
        SystemClock.sleep(3000);
    }

    public void fillingInFieldsWithValidData(String text, String validCategory) {
        Allure.step("Заполнение полей валидными данными");
        WatchScreenElements watchScreenElements = new WatchScreenElements();
        CalendarScreenStep calendarScreenStep = new CalendarScreenStep();

        creatingNewsScreenElements.getCategoryFieldNews().perform(replaceText(validCategory)).perform(closeSoftKeyboard());
        creatingNewsScreenElements.getTitleFieldNews().perform(typeText(text), click()).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
        creatingNewsScreenElements.getPublicationDateFieldNews().perform(click());
        watchScreenElements.getOkButton().perform(scrollTo(), click());
        SystemClock.sleep(3000);
        creatingNewsScreenElements.getTimeFieldNews().perform(click());
        calendarScreenStep.clickingOnTheConfirmButton();
        creatingNewsScreenElements.getDescriptionFieldNews().perform(typeText(text), click()).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
    }

    public void fillingInTheCategoryField(String text) {
        Allure.step("Заполнение поля Category");
        WatchScreenElements watchScreenElements = new WatchScreenElements();
        CalendarScreenElements calendarScreenElements = new CalendarScreenElements();
                                                                      //randomCategory()
        creatingNewsScreenElements.getCategoryFieldNews().perform(replaceText(text)).perform(closeSoftKeyboard());
        creatingNewsScreenElements.getTitleFieldNews().perform(replaceText(text), click()).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
        creatingNewsScreenElements.getPublicationDateFieldNews().perform(click());
        calendarScreenElements.getOkButton().perform(scrollTo(), click());
        creatingNewsScreenElements.getTimeFieldNews().perform(click());
        watchScreenElements.getOkButton().perform(scrollTo(), click());
        SystemClock.sleep(3000);
        creatingNewsScreenElements.getDescriptionFieldNews().perform(click());
        creatingNewsScreenElements.getDescriptionFieldNews().perform(replaceText(text), click()).perform(closeSoftKeyboard());
        SystemClock.sleep(3000);
    }

    public void choosingNews(int position) {
        Allure.step("Выбор новости");
        ControlPanelScreenElements controlPanelScreenElements = new ControlPanelScreenElements();
        controlPanelScreenElements.getRecyclerView().perform(actionOnItemAtPosition(position, click()));
        SystemClock.sleep(3000);
    }

    public void checkingTheNameOfTheCreatingNewsScreen() {
        Allure.step("Проверка названия экрана Creating News");
        creatingNewsScreenElements.getCreatingNameScreen().check(matches(isDisplayed()));
        creatingNewsScreenElements.getNewsNameScreen().check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkNameFieldInCreatingNews() {
        Allure.step("Проверка идентифицирующих названий полей для заполнения");
        creatingNewsScreenElements.getCategoryName().check(matches(isDisplayed()));
        creatingNewsScreenElements.getTitleName().check(matches(isDisplayed()));
        creatingNewsScreenElements.getPublicationDateName().check(matches(isDisplayed()));
        creatingNewsScreenElements.getTimeName().check(matches(isDisplayed()));
        creatingNewsScreenElements.getDescriptionName().check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingForTheAbsenceOfWordsFromRussianLettersInTheFields() {
        Allure.step("Проверка на отсутствие в полях слов из русских букв");
        creatingNewsScreenElements.getTitleFieldNews().check(matches(withText(""))).check(matches(isDisplayed()));
        creatingNewsScreenElements.getDescriptionFieldNews().check(matches(withText(""))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingForThePresenceOfWordsFromEnglishLettersInTheFields(String text) {
        Allure.step("Проверка на присудствие в полях слов из английских букв");
        creatingNewsScreenElements.getCategoryFieldNews().check(matches(withText(text))).check(matches(isDisplayed()));
        creatingNewsScreenElements.getTitleFieldNews().check(matches(withText(text))).check(matches(isDisplayed()));
        creatingNewsScreenElements.getDescriptionFieldNews().check(matches(withText(text))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void comparingTheDataOfTheCreatedNewsWithTheDataOfTheFirstNewsFromTheList(
            String nameNewsItWas, String nameNewsItWasHasBecomes, String publicationDateNewsItWas, String publicationDateNewsItWasHasBecomes,
            String creationDateNewsItWas, String creationDateNewsItWasHasBecomes, String authorNewsItWas, String authorNewsItWasHasBecomes,
            String descriptionNewsItWas, String descriptionNewsItWasHasBecomes) {
        Allure.step("Сравнение данных созданной новости с данными новости первой из списка");

        assertNotEquals(nameNewsItWas, nameNewsItWasHasBecomes);

        if (publicationDateNewsItWas.equals(publicationDateNewsItWasHasBecomes)) {
            assertEquals(publicationDateNewsItWas, publicationDateNewsItWasHasBecomes);
        } else {
            assertNotEquals(publicationDateNewsItWas, publicationDateNewsItWasHasBecomes);
        }
        if (creationDateNewsItWas.equals(creationDateNewsItWasHasBecomes)) {
            assertEquals(creationDateNewsItWas, creationDateNewsItWasHasBecomes);
        } else {
            assertNotEquals(creationDateNewsItWas, creationDateNewsItWasHasBecomes);
        }
        if (authorNewsItWas.equals(authorNewsItWasHasBecomes)) {
            assertEquals(authorNewsItWas, authorNewsItWasHasBecomes);
        } else {
            assertNotEquals(authorNewsItWas, authorNewsItWasHasBecomes);
        }
        assertNotEquals(descriptionNewsItWas, descriptionNewsItWasHasBecomes);
        SystemClock.sleep(3000);
    }

    public void checkingTheDataOfTheFirstNewsFromTheListMustMatchAfterCancelingTheCreationOfTheNews(
            String nameNewsItWas, String nameNewsItWasHasBecomes, String publicationDateNewsItWas, String publicationDateNewsItWasHasBecomes,
            String creationDateNewsItWas, String creationDateNewsItWasHasBecomes, String authorNewsItWas, String authorNewsItWasHasBecomes,
            String descriptionNewsItWas, String descriptionNewsItWasHasBecomes) {
        Allure.step("Проверка данные первой новости из списка должны совпадать после отмены создания новости");
        assertEquals(nameNewsItWas.trim(), nameNewsItWasHasBecomes.trim());
        assertEquals(publicationDateNewsItWas, publicationDateNewsItWasHasBecomes);
        assertEquals(creationDateNewsItWas, creationDateNewsItWasHasBecomes);
        assertEquals(authorNewsItWas, authorNewsItWasHasBecomes);
        assertEquals(descriptionNewsItWas.trim(), descriptionNewsItWasHasBecomes.trim());
        SystemClock.sleep(3000);
    }

    public void checkingTheCalendarAppearance(@NonNull AppActivity activity) {
        Allure.step("Проверка появления календаря");
        onView(withClassName(is("android.widget.DatePicker")))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingTheAppearanceOfTheDropDownList(@NonNull AppActivity activity) {
        Allure.step("Проверка появления выпадающего списка");
        onView(withClassName(is("android.widget.PopupWindow$PopupBackgroundView")))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingTheAppearanceOfClockOfTheArrowType(@NonNull AppActivity activity) {
        Allure.step("Проверка появления часов стрелочного типа");
        onView(withClassName(is("android.widget.RadialTimePickerView")))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingTheFillEmptyFields(@NonNull AppActivity activity, String text) {
        Allure.step("Проверка появления предупреждающего сообщения Fill empty fields");
        onView(withText(text)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public void checkingTheSavingFailedTryAgainLater(@NonNull AppActivity activity, String text) {
        Allure.step("Проверка появления предупреждающего сообщения Saving failed. Try again later");
        onView(withText(text)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    public String nameNews() {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_title_text_view), 0)));
    }

    public String nameNewsPosition(int position) {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_title_text_view), position)));
    }

    public String publicationDateNews() {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_publication_date_text_view), 0)));
    }

    public String publicationDateNewsPosition(int position) {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_publication_date_text_view), position)));
    }

    public String creationDateNews() {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_create_date_text_view), 0)));
    }

    public String creationDateNewsPosition(int position) {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_create_date_text_view), position)));
    }

    public String authorNews() {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_author_name_text_view), 0)));
    }

    public String authorNewsPosition(int position) {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_author_name_text_view), position)));
    }

    public String descriptionNews() {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_description_text_view), 0)));
    }

    public String descriptionNewsPosition(int position) {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_description_text_view), position)));
    }

}
