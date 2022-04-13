package ru.netology;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderTest {

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    public String generateDate(int Days) {
        return LocalDate.now().plusDays(Days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void firstTest(){
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $$(withText("Забронировать")).first().click();
        //$$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(14));
    }


    @Test
    void testNoName(){
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").setValue("16.04.2022");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").should(visible, Duration.ofSeconds(14));
    }

    @Test
    void testInvalidName(){
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").setValue("16.04.2022");
        $("[data-test-id='name'] input").setValue("Ivanov Ivan");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").should(visible, Duration.ofSeconds(14));
    }

    @Test
    void testNoCity(){
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='date'] input").setValue("16.04.2022");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").should(visible, Duration.ofSeconds(14));
    }

    @Test
    void testInvalidCity(){
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Moscow");
        $("[data-test-id='date'] input").setValue("16.04.2022");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").should(visible, Duration.ofSeconds(14));
    }

    @Test
    void testNoPhone(){
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Moscow");
        $("[data-test-id='date'] input").setValue("16.04.2022");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").should(visible, Duration.ofSeconds(14));
    }

    @Test
    void testInvalidPhone(){
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Moscow");
        $("[data-test-id='date'] input").setValue("16.04.2022");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+7999123456");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").should(visible, Duration.ofSeconds(14));
    }
}
