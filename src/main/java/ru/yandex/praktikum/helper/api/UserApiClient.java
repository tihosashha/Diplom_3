package ru.yandex.praktikum.helper.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserApiClient extends RestAssuredClient {

    public static final String ENDPOINT_LOGIN = "/auth/login";
    public static final String ENDPOINT_REGISTER = "/auth/register";
    public static final String ENDPOINT_USER = "/auth/user";

    private String bearerToken = "";

    @Step("АПИ Авторизация")
    public Response authorization(UserReqJson body) {
        Response response = reqSpec
                .body(body)
                .post(ENDPOINT_LOGIN);
        extractToken(response);
        return response;
    }

    @Step("АПИ Создание пользователя")
    public Response createUser(UserReqJson json) {
        Response response = reqSpec
                .body(json)
                .post(ENDPOINT_REGISTER);
        extractToken(response);
        return response;
    }

    @Step("АПИ Очистка токена")
    public void clearAuthToken() {
        bearerToken = "";
    }

    @Step("АПИ получение токена")
    private void extractToken(Response response) {
        if (response.statusCode() == 200) {
            bearerToken = response.then().extract().body().path("accessToken");
        } else {
            clearAuthToken();
        }
    }

    @Step("АПИ Пользователь удалён")
    public void deleteUser() {
        reqSpec.header("Authorization", bearerToken)
                .delete(ENDPOINT_USER);
    }

    @Step("Удаление пользователя с почтой {userReqJson.email} и паролем {userReqJson.password}")
    public static void deleteUserAccount(UserReqJson userReqJson) {
        UserApiClient userApiClient = new UserApiClient();
        Response responseAuth = userApiClient.authorization(userReqJson);
        if (responseAuth.statusCode() == 200) {
            userApiClient.deleteUser();
        } else {
            System.out.println("Пользователь создан не был");
        }
    }
}