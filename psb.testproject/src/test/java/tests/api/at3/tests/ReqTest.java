package tests.api.at3.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;
import tests.api.at3.SuccessReg;
import tests.api.at3.UnsuccessReg;
import tests.api.at3.dtos.UserDTO;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqTest {
    String url = "http://85.192.34.140:8080/";
    String jwtToken;

    //post
    // Обязательные поля login и pass только. Список с играми может отсутсвовать у пользователя при регистрации, их можно добавить потом
    @Test
    @Description("Удачная регистрация нового пользователя")
    public void successUserRegTest() {
        String log = "qwww12ew12";
        UserDTO user = new UserDTO(log, "string");
        UserDTO userReg = given()
                .baseUri(url)
                .body(user)
                .when()
                .contentType(ContentType.JSON)
                .post("api/signup")
                .then().statusCode(201).log().all()
                .extract().body().jsonPath().getObject("register_data", UserDTO.class);
        Assert.assertEquals(log, userReg.getLogin());
    }

    @Test
    @Description("Неспешная регистрация нового пользователя")
    public void unSuccessUserRegTest() {
        UserDTO user = new UserDTO("qwerty12", "string");
        String mes = "Login already exist";
        UnsuccessReg unsuccessReg = given()
                .baseUri(url)
                .body(user)
                .when()
                .contentType(ContentType.JSON)
                .post("api/signup")
                .then().statusCode(400).log().all()
                .extract().body().jsonPath().getObject("info", UnsuccessReg.class);
        Assert.assertEquals(mes, unsuccessReg.getMessage());
    }

    //Получение JWT токена для пользователя
    @Test
    @Description("Удачное получение JWT токена для пользователя")
    public void successUserLoginTest() {
        SuccessReg successReg = given()
                .baseUri(url)
                .body("{\"password\": \"string\", \"username\": \"qwe12\"}")
                .when()
                .contentType(ContentType.JSON)
                .post("api/login")
                .then().statusCode(200).log().all()
                .body("token", notNullValue())
                .extract().body().as(SuccessReg.class);
        jwtToken = successReg.getToken();
    }

    @Test
    @Description("Неуспешное получение JWT токена, пользователь не зарегестрирован")
    public void unSuccessUserLoginTest() {
        String error = "Unauthorized";
        Response response = given()
                .baseUri(url)
                .body("{\"password\": \"string\", \"username\": \"string\"}")
                .when()
                .contentType(ContentType.JSON)
                .post("api/login")
                .then().statusCode(401).log().all()
                .body("token", nullValue())
                .body("error", equalTo(error))
                .extract().response();
    }

    //get
    //Получение информации о пользователе
    @Test
    @Description("Успешное получение информации о пользователе")
    public void checkGetUser200() {
        successUserLoginTest();
        Response response = given()
                .baseUri(url)
                .auth()
                .oauth2(jwtToken)
                .when()
                //.header("Authorization", "Bearer " + jwtToken)
                .contentType(ContentType.JSON)
                .get("api/user")
                .then().statusCode(200).log().all()
                .body("login", equalTo("qwe12"))
                .body("pass", equalTo("string"))
                .extract().response();
    }

    @Test
    @Description("Неуспешное получение информации, пользователь не зарегестрирован")
    public void checkGetUser401() {
        String error = "Unauthorized";
        UnsuccessReg unsuccessReg = given()
                .baseUri(url)
                .when()
                .contentType(ContentType.JSON)
                .get("api/user")
                .then().statusCode(401).log().all()
                .extract().as(UnsuccessReg.class);
        Assert.assertEquals(error, unsuccessReg.getError());
    }

    //Скачать картинку с сервера в формате JPEG
    @Test
    @Description("Успешное получение картинки")
    public void getJPEG() {
        try {
            InputStream inputStream = given()
                    .baseUri(url)
                    .when()
                    .contentType("image/jpeg")
                    .get("api/files/download")
                    .then().statusCode(200).log().all()
                    .extract().asInputStream();
            FileOutputStream outputStream = new FileOutputStream("threadqa.jpeg");

            byte[] buffer = new byte[1024];
            int bytesRead;

            //из InputStream в файл
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Description("неуспешное получение картинки, пользователь не авторизован")
    public void getJPEG401() {
        Response response = given()
                .baseUri(url)
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalid-authorization-header")
                .get("api/files/download")
                .then().log().all().statusCode(401)
                .extract().response();
    }
    @Test
    @Description("неуспешное получение картинки, изображение не найдено")
    public void getJPEG404() {
        String error = "Not Found";
        UnsuccessReg unsuccessReg = given()
                .baseUri(url)
                .when()
                .get("api/files/downloa")
                .then().statusCode(404).log().all()
                .extract().as(UnsuccessReg.class);
        Assert.assertEquals(error, unsuccessReg.getError());
    }
}

