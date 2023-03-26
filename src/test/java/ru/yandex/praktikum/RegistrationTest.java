package ru.yandex.praktikum;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.driver.DriverInitialization;
import ru.yandex.praktikum.helper.GenerateData;
import ru.yandex.praktikum.helper.Url;
import ru.yandex.praktikum.helper.api.UserApiClient;
import ru.yandex.praktikum.helper.api.UserReqJson;
import ru.yandex.praktikum.page.RegistrationPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

@Feature("Регистрация")
public class RegistrationTest {

    private UserReqJson userReqJson;
    private RegistrationPage registrationPage;

    @Before
    public void setUp() {
        new DriverInitialization().startBrowser();
        userReqJson = GenerateData.generateUserAccount();
        registrationPage = open(Url.URL_REGISTRATION, RegistrationPage.class);
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void successRegistrationTest() {
        registrationPage
                .inputNameEmailPasswordAndRegister(userReqJson.getName(), userReqJson.getEmail(), userReqJson.getPassword());

        assertTrue(registrationPage.returnTrueIfRegistrationSuccess());
    }

    @Test
    @DisplayName("Проверка ошибки что пароль должен быть больше пяти знаков")
    public void returnErrorIfShortPasswordTest() {
        userReqJson.setPassword("12345");
        registrationPage
                .inputNameEmailPasswordAndRegister(userReqJson.getName(), userReqJson.getEmail(), userReqJson.getPassword());
        assertTrue(registrationPage.returnTrueIfShowShortPasswordError());

    }

    @After
    public void tearDown() {
        closeWebDriver();
        UserApiClient.deleteUserAccount(userReqJson);
    }
}