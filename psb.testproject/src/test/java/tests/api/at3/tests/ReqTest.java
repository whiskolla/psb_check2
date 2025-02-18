package tests.api.at3.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;
import tests.api.at3.SuccessReg;
import tests.api.at3.UnsuccessReg;
import tests.api.at3.dtos.UserDTO;
import tests.api.at3.spec.Specification;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class ReqTest {
    String url = "http://85.192.34.140:8080/";
    String jwtToken;

    @Test
    @Description("Удачная регистрация нового пользователя")
    public void successSignupTest_201() {
        Specification.installSpecification(Specification.requestSpec(url), Specification.responseSpec(201));
        String log = "qw" + System.currentTimeMillis();
        UserDTO user = new UserDTO(log, "string");
        UserDTO userReg = given()
                .body(user)
                .when()
                .contentType(ContentType.JSON)
                .post("api/signup")
                .then().log().all()
                .extract().body().jsonPath().getObject("register_data", UserDTO.class);
        Assert.assertEquals(log, userReg.getLogin());
    }

    @Test
    @Description("Неспешная регистрация нового пользователя")
    public void unSuccessSignupTest_400() {
        Specification.installSpecification(Specification.requestSpec(url), Specification.responseSpec(400));
        UserDTO user = new UserDTO("qwerty12", "string");
        String mes = "Login already exist";
        UnsuccessReg unsuccessReg = given()
                .body(user)
                .when()
                .contentType(ContentType.JSON)
                .post("api/signup")
                .then().log().all()
                .extract().body().jsonPath().getObject("info", UnsuccessReg.class);
        Assert.assertEquals(mes, unsuccessReg.getMessage());
    }

    @Test
    @Description("Удачное получение JWT токена для пользователя")
    public void successLoginTest_200() {
        Specification.installSpecification(Specification.requestSpec(url), Specification.responseSpec(200));
        SuccessReg successReg = given()
                .body("{\"password\": \"string\", \"username\": \"qwe12\"}")
                .when()
                .contentType(ContentType.JSON)
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
        Response response = given()
                .body("{\"password\": \"string\", \"username\": \"string\"}")
                .when()
                .contentType(ContentType.JSON)
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
        Assert.assertEquals("qwe12", response.body().jsonPath().getJsonObject("login"));
        Assert.assertEquals("string", response.body().jsonPath().getJsonObject("pass"));
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
        Specification.installSpecification(Specification.requestSpecJPEG(url), Specification.responseSpec(200));
        Response response = given()
                .when()
                .get("api/files/download")
                .then()
                .extract().response();
        Assert.assertNotNull(response.asByteArray());

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.asByteArray());
        File outputFile = new File("src/test/java/tests/threadqa.jpg");
        ImageIO.write(ImageIO.read(byteArrayInputStream), "jpg", outputFile);
    }

    @Test
    @Description("Удаление картинки")
    public void deleteJPEG() throws IOException {
        String path = "src/test/java/tests/threadqa.jpg";
        Files.delete(Paths.get(path));
        Assert.assertFalse(Files.exists(Path.of(path)));
    }

    @Test
    @Description("неуспешное получение картинки, пользователь не авторизован")
    public void getJPEG_401() {
        Specification.installSpecification(Specification.requestSpecJPEG(url), Specification.responseSpec(401));
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

