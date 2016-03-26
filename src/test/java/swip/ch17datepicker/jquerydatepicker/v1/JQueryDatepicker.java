package swip.ch17datepicker.jquerydatepicker.v1;

import swip.ch15pageflow.v2.framework.Browser;

import java.time.LocalDate;
import java.time.Month;

import static org.openqa.selenium.By.linkText;
import static swip.ch17datepicker.jquerydatepicker.JQueryByClassName.*;
import static swip.ch17datepicker.jquerydatepicker.JQueryById.DATE_FIELD;
import static swip.ch17datepicker.jquerydatepicker.JQueryById.UI_DATEPICKER_DIV;
import static swip.ch17datepicker.jquerydatepicker.JQueryPredicates.CALENDAR_CLOSED;


public class JQueryDatepicker {

    private final Browser browser;

    public JQueryDatepicker(Browser browser) {
        this.browser = browser;
    }

    public String getDate() {                                  //<7>
        return browser.getInputText(DATE_FIELD);
    }

    public void pick(Month month, int day, int year) {
        LocalDate.of(year, month, day);    //<1>
        show();                                          //<2>
        pickYear(year);                                  //<3>
        pickMonth(month.ordinal());                      //<4>
        pickDay(day);                                    //<5>
    }

    private void show() {
        browser.click(DATE_FIELD);
    }

    private void pickYear(int year) {
        if (displayedYear() < year) {        //<1>
            while (displayedYear() != year) {
                nextMonth();
            }
        } else if (displayedYear() > year) {
            while (displayedYear() != year) {
                previousMonth();
            }
        }
    }

    private int displayedYear() {
        return Integer.parseInt(
            browser.untilFound(UI_DATEPICKER_DIV)
                .getText(DISPLAY_YEAR)
        );
    }

    private void pickMonth(int month) {
        if (displayedMonth() < month) {             //<2>
            while (displayedMonth() != month) {
                nextMonth();
            }
        } else if (displayedMonth() > month) {
            while (displayedMonth() != month) {
                previousMonth();
            }
        }
    }

    private void previousMonth() {
        browser.untilFound(UI_DATEPICKER_DIV)
            .click(PREV_MONTH_BUTTON);  //<3>
    }

    private void nextMonth() {
        browser.untilFound(UI_DATEPICKER_DIV)
            .click(NEXT_MONTH_BUTTON);  //<4>
    }

    private int displayedMonth() {
        return Month.valueOf(
            browser.untilFound(UI_DATEPICKER_DIV).getText(DISPLAY_MONTH)
                .toUpperCase()
        ).ordinal();   //<7>
    }

    public void pickDay(int day) {
        browser.untilFound(UI_DATEPICKER_DIV)
            .click(() -> linkText(String.valueOf(day))); //<9>
        browser.until(CALENDAR_CLOSED);  //<11>
    }
}
