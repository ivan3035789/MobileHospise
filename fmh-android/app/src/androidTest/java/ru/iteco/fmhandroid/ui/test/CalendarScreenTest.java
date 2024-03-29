package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.Rand.random;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomDay;
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

import java.time.LocalDate;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.step.AuthorizationScreenStep;
import ru.iteco.fmhandroid.ui.step.CalendarScreenStep;
import ru.iteco.fmhandroid.ui.step.CreatingClaimsScreenStep;
import ru.iteco.fmhandroid.ui.step.MainScreenStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class CalendarScreenTest {

    @Rule
    public ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    MainScreenStep mainScreenStep = new MainScreenStep();
    AuthorizationScreenStep authorizationScreenStep = new AuthorizationScreenStep();
    CreatingClaimsScreenStep creatingClaimsScreenStep = new CreatingClaimsScreenStep();
    CalendarScreenStep calendarScreenStep = new CalendarScreenStep();

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenStep.checkNameMainScreen();
        } catch (NoMatchingViewException e) {
            authorizationScreenStep.validLoginPassword(authInfo());
        } finally {
            mainScreenStep.clickingOnTheButtonToGoToTheClaimCreationScreen();
            creatingClaimsScreenStep.checkingTheNameOfTheClaimCreationScreen();
            creatingClaimsScreenStep.clickingOnTheDateField();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("There should be a change of month")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"стрелка вправо, влево\" происходит смена месяца")
    public void thereShouldBeChangeOfMonth() {
        int numberOfMonths = 2;
        int numberOfMonths1 = 1;
        int yearNowSetUp = LocalDate.now().getYear();
        String dayOfWeekNow = String.valueOf(LocalDate.now().plusMonths(1).getDayOfWeek()).substring(0, 3).toLowerCase();
        String dayOfWeekPlusMonth = String.valueOf(LocalDate.now().plusMonths(2).getDayOfWeek()).substring(0, 3).toLowerCase();
        int monthNowSetUp = LocalDate.now().plusMonths(1).getMonthValue();
        String monthNow = String.valueOf(LocalDate.now().plusMonths(1).getMonth()).substring(0, 3).toLowerCase();
        String monthPlusTwoMonth = String.valueOf(LocalDate.now().plusMonths(2).getMonth()).substring(0, 3).toLowerCase();
        int monthPlusTwoMonthSetUp = LocalDate.now().plusMonths(2).getMonthValue();
        String dayPlusYearPlusMonth = String.valueOf(LocalDate.now().plusMonths(1).getDayOfMonth());
        int dayNowSetUp = LocalDate.now().plusMonths(1).getDayOfMonth();
        String dayNow = String.valueOf(LocalDate.now().plusMonths(1).getDayOfMonth());

        calendarScreenStep.pressingTheButtonToGoToTheNextMonthTwelveTimes(numberOfMonths);
        calendarScreenStep.settingDate(yearNowSetUp, monthPlusTwoMonthSetUp, dayNowSetUp);

        String dateBefore = calendarScreenStep.dateFromTheCalendarHeaderReturnString(dayOfWeekPlusMonth, monthPlusTwoMonth, dayPlusYearPlusMonth);

        ViewInteraction dateFromTheCalendarHeaderBefore = calendarScreenStep.dateFromTheCalendarHeaderBeforeReturnViewInteraction(dayOfWeekPlusMonth, monthPlusTwoMonth, dayPlusYearPlusMonth);
        calendarScreenStep.checkingTheDisplayInTheCalendarHeaderOfTheMonthNameChange(
                dateFromTheCalendarHeaderBefore, dayOfWeekPlusMonth, monthPlusTwoMonth, dayPlusYearPlusMonth, dateBefore, dayOfWeekPlusMonth, monthPlusTwoMonth,
                dayPlusYearPlusMonth);

        calendarScreenStep.clickingOnTheButtonToGoToThePreviousMonthTwelveTimes(numberOfMonths1);
        calendarScreenStep.settingDate(yearNowSetUp, monthNowSetUp, dayNowSetUp);

        String dateAfter = calendarScreenStep.dateFromTheCalendarHeaderReturnString(dayOfWeekNow, monthNow, dayNow);
        ViewInteraction dateFromTheCalendarHeaderAfter = calendarScreenStep.dateFromTheCalendarHeaderBeforeReturnViewInteraction(dayOfWeekNow, monthNow, dayNow);
        calendarScreenStep.checkingTheDisplayInTheCalendarHeaderOfTheMonthNameChange(
                dateFromTheCalendarHeaderAfter, dayOfWeekNow, monthNow, dayNow, dateAfter, dayOfWeekNow, monthNow, dayPlusYearPlusMonth);
    }

    @Test
    @DisplayName("Must choose the day of the month")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на числа месяца черного цвета, число выбирается и выставляется ")
    public void mustChooseTheDayOfTheMonth() {
        int randomDay = randomDay();
        String dayOfWeekPlusRandomDay = String.valueOf(LocalDate.now().plusMonths(1).plusDays(randomDay).getDayOfWeek()).substring(0, 3).toLowerCase();
        int yearNowSetUp = LocalDate.now().getYear();
        int monthPlusRandomDaySetUp = LocalDate.now().plusMonths(1).getMonthValue();
        String monthPlusRandomDay = String.valueOf(LocalDate.now().plusMonths(1).plusDays(randomDay).getMonth()).substring(0, 3).toLowerCase();
        int dayPlusRandomDayDetUp = LocalDate.now().plusDays(randomDay).getDayOfMonth();
        String dayPlusRandomDay = String.valueOf(LocalDate.now().plusMonths(1).plusDays(randomDay).getDayOfMonth());

        calendarScreenStep.settingDate(yearNowSetUp, monthPlusRandomDaySetUp, dayPlusRandomDayDetUp);

        String dateAfter = calendarScreenStep.dateFromTheCalendarHeaderReturnString(dayOfWeekPlusRandomDay, monthPlusRandomDay, dayPlusRandomDay);
        ViewInteraction dateFromTheCalendarHeaderAfter = calendarScreenStep.dateFromTheCalendarHeaderBeforeReturnViewInteraction(dayOfWeekPlusRandomDay, monthPlusRandomDay, dayPlusRandomDay);
        calendarScreenStep.checkingTheDisplayInTheCalendarHeaderOfTheMonthNameChange(
                dateFromTheCalendarHeaderAfter, dayOfWeekPlusRandomDay, monthPlusRandomDay, dayPlusRandomDay, dateAfter, dayOfWeekPlusRandomDay,
                monthPlusRandomDay, dayPlusRandomDay);

    }

    @Test
    @DisplayName("There should be a change of year")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"стрелка вправо, влево\" происходит смена года")
    public void thereShouldBeChangeOfYear() {
        int numberOfMonths = 12;
        int yearNowSetUp = LocalDate.now().getYear();
        String yearNow = String.valueOf(LocalDate.now().getYear());
        int yearIntPlusNumberOfMonthsSetUp = LocalDate.now().plusMonths(numberOfMonths).getYear();
        String yearNumberOfMonths = String.valueOf(LocalDate.now().plusMonths(numberOfMonths).getYear());
        int monthNowSetUp = LocalDate.now().getMonthValue();
        int monthPlusNumberOfMonthsSetUp = LocalDate.now().plusMonths(numberOfMonths).getMonthValue();
        int dayNowSetUp = LocalDate.now().getDayOfMonth();

        calendarScreenStep.pressingTheButtonToGoToTheNextMonthTwelveTimes(numberOfMonths);
        calendarScreenStep.settingDate(yearIntPlusNumberOfMonthsSetUp, monthPlusNumberOfMonthsSetUp, dayNowSetUp);

        String yearFromTheCalendarHeaderBefore = calendarScreenStep.yearFromTheCalendarHeaderReturnString(yearNumberOfMonths);
        ViewInteraction yearBefore = calendarScreenStep.yearFromTheCalendarHeaderReturnViewInteraction(yearNumberOfMonths);
        calendarScreenStep.checkingTheYearChangeCalendarDisplayInTheHeader(yearBefore, yearNumberOfMonths, yearFromTheCalendarHeaderBefore);

        calendarScreenStep.clickingOnTheButtonToGoToThePreviousMonthTwelveTimes(numberOfMonths);
        calendarScreenStep.settingDate(yearNowSetUp, monthNowSetUp, dayNowSetUp);

        String yearFromTheCalendarHeaderAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnString(yearNow);
        ViewInteraction yearAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnViewInteraction(yearNow);
        calendarScreenStep.checkingTheYearChangeCalendarDisplayInTheHeader(yearAfter, yearNow, yearFromTheCalendarHeaderAfter);
    }

    @Test
    @DisplayName("The set date must be displayed correctly")
    @Description("В этом тест кейсе мы проверяем, что при выборе даты, дата в верхней части окна соответствует выбранной пользователем")
    public void theSetDateMustBeDisplayedCorrectly() {
        int randomDay = randomDay();
        int randomYear = random(1, 2);
        String yearPlusRandomYear = String.valueOf(LocalDate.now().plusYears(randomYear).getYear());
        int yearNowSetUp = LocalDate.now().getYear();
        String dayOfWeekPlusRandomDay = String.valueOf(LocalDate.now().plusMonths(1).plusDays(randomDay).getDayOfWeek()).substring(0, 3).toLowerCase();
        int monthPlusRandomDaySetUp = LocalDate.now().plusMonths(1).getMonthValue();
        String monthPlusRandomDay = String.valueOf(LocalDate.now().plusMonths(1).plusDays(randomDay).getMonth()).substring(0, 3).toLowerCase();
        String dayOfMonthPlusRandomDay = String.valueOf(LocalDate.now().plusMonths(1).plusDays(randomDay).getDayOfMonth());
        int DayOfMonthPlusRandomDaySetUp = LocalDate.now().plusMonths(1).plusDays(randomDay).getDayOfMonth();

        calendarScreenStep.settingDate(yearNowSetUp, monthPlusRandomDaySetUp, DayOfMonthPlusRandomDaySetUp);

        String dateAfter = calendarScreenStep.dateFromTheCalendarHeaderReturnString(dayOfWeekPlusRandomDay, monthPlusRandomDay, dayOfMonthPlusRandomDay);
        ViewInteraction dateFromTheCalendarHeaderAfter = calendarScreenStep.dateFromTheCalendarHeaderBeforeReturnViewInteraction(dayOfWeekPlusRandomDay, monthPlusRandomDay, dayOfMonthPlusRandomDay);
        calendarScreenStep.checkingTheDisplayInTheCalendarHeaderOfTheMonthNameChange(
                dateFromTheCalendarHeaderAfter, dayOfWeekPlusRandomDay, monthPlusRandomDay, dayOfMonthPlusRandomDay, dateAfter, dayOfWeekPlusRandomDay,
                monthPlusRandomDay, dayOfMonthPlusRandomDay);

        calendarScreenStep.pressingTheButtonToSelectTheYear();
        calendarScreenStep.settingTheYear(randomYear);

        String yearFromTheCalendarHeaderAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnString(yearPlusRandomYear);
        ViewInteraction yearAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnViewInteraction(yearPlusRandomYear);
        calendarScreenStep.checkingTheYearChangeCalendarDisplayInTheHeader(yearAfter, yearPlusRandomYear, yearFromTheCalendarHeaderAfter);
    }

    @Test
    @DisplayName("The selected date should be set")
    @Description("В этом тест кейсе мы проверяем, что при выборе даты и после нажатия на кнопку \"ок\" в поле \"Publication date\" устанавливается выбранная дата")
    public void theSelectedDateShouldBeSet() {
        int randomYear = random(1, 2);

        int yearPlusYears = LocalDate.now().plusYears(randomYear).getYear();
        int monthNow = LocalDate.now().getMonthValue();
        int dayOfMonthNow = LocalDate.now().getDayOfMonth();

        calendarScreenStep.pressingTheButtonToSelectTheYear();
        calendarScreenStep.settingTheYear(randomYear);

        String today = calendarScreenStep.dateFormatting(yearPlusYears, monthNow, dayOfMonthNow);

        calendarScreenStep.clickingOnTheConfirmButton();
        creatingClaimsScreenStep.checkingTheNameOfTheClaimCreationScreen();
        String dateField = creatingClaimsScreenStep.dateFromTheField();

        creatingClaimsScreenStep.checkingTheSetDateWithTheDateInTheField(dateField, today);
    }

    @Test
    @DisplayName("Canceling the date selection and setting")
    @Description("В этом тест кейсе мы проверяем, что при нажатии на кнопку \"Cancel\", происходит отмена установки выбраной даты, календарь закрывается")
    public void cancelingTheDateSelectionAndSetting() {
        int randomYear = random(1);
        int randomMonth = random(1);
        int day = randomDay();
        String yearPlusRandomYear = String.valueOf(LocalDate.now().plusYears(randomYear).getYear());
        int yearPlusRandomYearSetUp = LocalDate.now().plusYears(randomYear).getYear();
        int monthPlusRandomMonthSetUp = LocalDate.now().plusMonths(randomMonth).getMonthValue();
        int dayOfMonthPlusRandomYearPlusRandomMonth = LocalDate.now().plusYears(randomYear).plusMonths(randomMonth).plusDays(day).getDayOfMonth();
        String dayOfWeekPlusRandomYearPlusRandomMonth = String.valueOf(LocalDate.now().plusYears(randomYear).plusMonths(randomMonth).plusDays(day).getDayOfWeek()).substring(0, 3).toLowerCase();
        String monthPlusRandomYearPlusRandomMonth = String.valueOf(LocalDate.now().plusYears(randomYear).plusMonths(randomMonth).plusDays(day).getMonth()).substring(0, 3).toLowerCase();
        String dayPlusRandomYearPlusRandomMonth = String.valueOf(LocalDate.now().plusYears(randomYear).plusMonths(randomMonth).plusDays(day).getDayOfMonth());

        calendarScreenStep.pressingTheButtonToSelectTheYear();
        calendarScreenStep.settingTheYear(randomYear);

        String yearFromTheCalendarHeaderAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnString(yearPlusRandomYear);
        ViewInteraction yearAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnViewInteraction(yearPlusRandomYear);
        calendarScreenStep.checkingTheYearChangeCalendarDisplayInTheHeader(yearAfter, yearPlusRandomYear, yearFromTheCalendarHeaderAfter);

        calendarScreenStep.pressingTheButtonToGoToTheNextMonthTwelveTimes(randomMonth);
        calendarScreenStep.settingDate(yearPlusRandomYearSetUp, monthPlusRandomMonthSetUp, dayOfMonthPlusRandomYearPlusRandomMonth);

        String dateAfter = calendarScreenStep.dateFromTheCalendarHeaderReturnString(dayOfWeekPlusRandomYearPlusRandomMonth, monthPlusRandomYearPlusRandomMonth, dayPlusRandomYearPlusRandomMonth);
        ViewInteraction dateFromTheCalendarHeaderAfter = calendarScreenStep.dateFromTheCalendarHeaderBeforeReturnViewInteraction(dayOfWeekPlusRandomYearPlusRandomMonth, monthPlusRandomYearPlusRandomMonth, dayPlusRandomYearPlusRandomMonth);

        calendarScreenStep.checkingTheDisplayInTheCalendarHeaderOfTheMonthNameChange(
                dateFromTheCalendarHeaderAfter, dayOfWeekPlusRandomYearPlusRandomMonth, monthPlusRandomYearPlusRandomMonth, dayPlusRandomYearPlusRandomMonth, dateAfter, dayOfWeekPlusRandomYearPlusRandomMonth,
                monthPlusRandomYearPlusRandomMonth, dayPlusRandomYearPlusRandomMonth);

        calendarScreenStep.clickingOnTheCancelYearSettingButton();
        creatingClaimsScreenStep.checkingTheNameOfTheClaimCreationScreen();
        creatingClaimsScreenStep.checkingTheAbsenceOfSetYear();
    }

    @Test
    @DisplayName("There should be a change of year")
    @Description("В этом тест кейсе мы проверяем, что при нажатии в верхней части окна на год, открывается окно для выбора года, год можно менять")
    public void thereShouldBeChangeOfYear1() {
        int randomYear = random(1, 2);
        String yearPlusRandomYear = String.valueOf(LocalDate.now().plusYears(randomYear).getYear());

        calendarScreenStep.pressingTheButtonToSelectTheYear();
        calendarScreenStep.settingTheYear(randomYear);

        String yearFromTheCalendarHeaderAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnString(yearPlusRandomYear);
        ViewInteraction yearAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnViewInteraction(yearPlusRandomYear);
        calendarScreenStep.checkingTheYearChangeCalendarDisplayInTheHeader(yearAfter, yearPlusRandomYear, yearFromTheCalendarHeaderAfter);
    }

    @Test
    @DisplayName("Canceling the selection and setting of the year")
    @Description(" В этом тест кейсе мы проверяем, что при нажатии на кнопку \"Cancel\", происходит отмена выбора и установки года в календаре")
    public void cancelingTheSelectionAndSettingOfTheYear() {
        int randomYear = random(1, 2);
        String yearPlusRandomYear = String.valueOf(LocalDate.now().plusYears(randomYear).getYear());

        calendarScreenStep.pressingTheButtonToSelectTheYear();
        calendarScreenStep.settingTheYear(randomYear);

        String yearFromTheCalendarHeaderAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnString(yearPlusRandomYear);
        ViewInteraction yearAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnViewInteraction(yearPlusRandomYear);
        calendarScreenStep.checkingTheYearChangeCalendarDisplayInTheHeader(yearAfter, yearPlusRandomYear, yearFromTheCalendarHeaderAfter);

        calendarScreenStep.clickingOnTheCancelYearSettingButton();
        creatingClaimsScreenStep.checkingTheAbsenceOfSetYear();
    }

    @Test
    @DisplayName("Should display the current date")
    @Description("В этом тест кейсе мы проверяем, что текущая дата соответствует сегоднящней")
    public void shouldDisplayTheCurrentDate() {

        String dayOfWeekNow = String.valueOf(LocalDate.now().getDayOfWeek()).substring(0, 3).toLowerCase();
        String monthNow = String.valueOf(LocalDate.now().getMonth()).substring(0, 3).toLowerCase();
        String dayNow = String.valueOf(LocalDate.now().getDayOfMonth());
        String yearNow = String.valueOf(LocalDate.now().getYear());

        String dateAfter = calendarScreenStep.dateFromTheCalendarHeaderReturnString(dayOfWeekNow, monthNow, dayNow);
        ViewInteraction dateFromTheCalendarHeaderAfter = calendarScreenStep.dateFromTheCalendarHeaderBeforeReturnViewInteraction(dayOfWeekNow, monthNow, dayNow);
        calendarScreenStep.checkingTheDisplayInTheCalendarHeaderOfTheMonthNameChange(
                dateFromTheCalendarHeaderAfter, dayOfWeekNow, monthNow, dayNow, dateAfter, dayOfWeekNow,
                monthNow, dayNow);

        String yearFromTheCalendarHeaderAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnString(yearNow);
        ViewInteraction yearAfter = calendarScreenStep.yearFromTheCalendarHeaderReturnViewInteraction(yearFromTheCalendarHeaderAfter);
        calendarScreenStep.checkingTheYearChangeCalendarDisplayInTheHeader(yearAfter, yearNow, yearFromTheCalendarHeaderAfter);
    }
}
