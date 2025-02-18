package tests.api.at3.tests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;
import tests.api.at3.dtos.SuccessReg;
import tests.api.at3.dtos.UnsuccessReg;
import tests.api.at3.dtos.UserDTO;
import tests.api.at3.spec.Specification;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class ReqTest {
    String url = "http://85.192.34.140:8080/";
    String jwtToken;
    String pass = "string";
    String log;

    @Test
    @Description("Удачная регистрация нового пользователя")
    public void successSignUpTest_201() {
        Specification.installSpecification(Specification.requestSpec(url), Specification.responseSpec(201));
        log = "qw" + System.currentTimeMillis();
        UserDTO user = new UserDTO(log, pass);
        UserDTO userReg = given()
                .body(user)
                .when()
                .post("api/signup")
                .then().log().all()
                .extract().body().jsonPath().getObject("register_data", UserDTO.class);
        Assert.assertEquals(log, userReg.getLogin());
    }

    @Test
    @Description("Неспешная регистрация нового пользователя")
    public void unSuccessSignUpTest_400() {
        successSignUpTest_201();
        Specification.installSpecification(Specification.requestSpec(url), Specification.responseSpec(400));
        UserDTO user = new UserDTO(log, pass);
        String mes = "Login already exist";
        UnsuccessReg unsuccessReg = given()
                .body(user)
                .when()
                .post("api/signup")
                .then().log().all()
                .extract().body().jsonPath().getObject("info", UnsuccessReg.class);
        Assert.assertEquals(mes, unsuccessReg.getMessage());
    }

    @Test
    @Description("Удачное получение JWT токена для пользователя")
    public void successLoginTest_200() {
        successSignUpTest_201();
        String user = "{\"password\": \"" + pass + "\", \"username\": \"" + log + "\"}";
        Specification.installSpecification(Specification.requestSpec(url), Specification.responseSpec(200));
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/login")
                .then().log().all()
                .extract().body().as(SuccessReg.class);
        Assert.assertNotNull(successReg.getToken());
        jwtToken = successReg.getToken();
    }

    @Test
    @Description("Неуспешное получение JWT токена, пользователь не зарегестрирован")
    public void unSuccessLoginTest_401() {
        Specification.installSpecification(Specification.requestSpec(url), Specification.responseSpec(401));
        String error = "Unauthorized";
        String unauthorizedUser = "{\"password\": \"string\", \"username\": \"string\"}";
        Response response = given()
                .body(unauthorizedUser)
                .when()
                .post("api/login")
                .then().log().all()
                .extract().response();
        Assert.assertNull(response.body().jsonPath().getJsonObject("token"));
        Assert.assertEquals(error, response.body().jsonPath().getJsonObject("error"));
    }

    @Test
    @Description("Успешное получение информации о пользователе")
    public void checkGetUser_200() {
        successLoginTest_200();
        Specification.installSpecification(Specification.requestSpecAuth(url, jwtToken), Specification.responseSpec(200));
        Response response = given()
                .when()
                .get("api/user")
                .then().log().all()
                .extract().response();
        Assert.assertEquals(log, response.body().jsonPath().getJsonObject("login"));
        Assert.assertEquals(pass, response.body().jsonPath().getJsonObject("pass"));
    }

    @Test
    @Description("Неуспешное получение информации, пользователь не зарегестрирован")
    public void checkGetUser_401() {
        Specification.installSpecification(Specification.requestSpec(url), Specification.responseSpec(401));
        String error = "Unauthorized";
        UnsuccessReg unsuccessReg = given()
                .when()
                .get("api/user")
                .then().log().all()
                .extract().as(UnsuccessReg.class);
        Assert.assertEquals(error, unsuccessReg.getError());
    }

    @Test
    @Description("Успешное получение картинки")
    public void getJPEG_200() throws IOException {
        int length = 49393;
        String path = "src/test/java/tests/threadqa.jpg";
        Specification.installSpecification(Specification.requestSpecJPEG(url), Specification.responseSpec(200));
        Response response = given()
                .when()
                .get("api/files/download")
                .then()
                .extract().response();
        Assert.assertNotNull(response.asByteArray());

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.asByteArray());
        File outputFile = new File(path);
        ImageIO.write(ImageIO.read(byteArrayInputStream), "jpg", outputFile);
        Assert.assertEquals(length, outputFile.length());

        if (Files.exists(Path.of(path))) {
            deleteJPEG(path);
        }
    }

    public void deleteJPEG(String path) throws IOException {
        Files.delete(Paths.get(path));
        Assert.assertFalse(Files.exists(Path.of(path)));
    }

    @Test
    @Description("неуспешное получение картинки, пользователь не авторизован")
    public void getJPEG_401() {
        Specification.installSpecification(Specification.requestSpec(url), Specification.responseSpec(401));
        Response response = given()
                .when()
                .header("Authorization", "Bearer invalid-authorization-header")
                .get("api/files/download")
                .then().log().all()
                .extract().response();
    }

    @Test
    @Description("неуспешное получение картинки, изображение не найдено")
    public void getJPEG_404() {
        Specification.installSpecification(Specification.requestSpecJPEG(url), Specification.responseSpec(404));
        String error = "Not Found";
        UnsuccessReg unsuccessReg = given()
                .when()
                .get("api/files/downloa")
                .then().log().all()
                .extract().as(UnsuccessReg.class);
        Assert.assertEquals(error, unsuccessReg.getError());
    }
}

