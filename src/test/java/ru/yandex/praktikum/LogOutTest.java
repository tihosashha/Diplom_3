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

@Feature("Выход из аккаунта")
public class LogOutTest {
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
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    public void successLoginFromMainPageButtonLogInTest() {
        boolean expected = generalPage.openLoginPage()
                .inputEmailPasswordAndLogIn(email, password)
                .clickHeaderAccountButton()
                .clickLogOutButton()
                .returnTrueIfOpenLogInPage();

        assertTrue(expected);
    }

    @After
    public void tearDown() {
        closeWebDriver();
        UserApiClient.deleteUserAccount(userReqJson);
    }
}
