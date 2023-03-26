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
import ru.yandex.praktikum.page.GeneralPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

@Feature("Авторизация")
public class AuthorizationTest {
    private UserReqJson userReqJson;
    private GeneralPage generalPage;
    private String email;
    private String password;

    @Before
    public void setUp() {
        new DriverInitialization().startBrowser();
        UserApiClient userApiClient = new UserApiClient();
        userReqJson = GenerateData.generateUserAccount();
        userApiClient.createUser(userReqJson);
        generalPage = open(Url.URL_BASE, GeneralPage.class);
        email = userReqJson.getEmail();
        password = userReqJson.getPassword();
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void successLoginFromMainPageButtonLogInTest() {
        generalPage.clickMainLogInButton()
                .inputEmailPasswordAndLogIn(email, password);

        assertTrue(generalPage.returnTrueIfCreateOrderButtonExist());
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void successLoginFromMainPageHeaderButtonAccountTest() {
        generalPage.clickHeaderAccountButton()
                .inputEmailPasswordAndLogIn(email, password);

        assertTrue(generalPage.returnTrueIfCreateOrderButtonExist());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void successLoginFromRegistrationPageAccountTest() {
        generalPage.openRegisterPage()
                .clickHyperLinkLogIn()
                .inputEmailPasswordAndLogIn(email, password);

        assertTrue(generalPage.returnTrueIfCreateOrderButtonExist());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void successLoginFromRestorePasswordTest() {
        generalPage.openRestorePasswordPage()
                .clickHyperLogIn()
                .inputEmailPasswordAndLogIn(email, password);

        assertTrue(generalPage.returnTrueIfCreateOrderButtonExist());
    }

    @After
    public void tearDown() {
        closeWebDriver();
        UserApiClient.deleteUserAccount(userReqJson);
    }
}
