package tests.api.at3.dtos;

import java.util.ArrayList;

public class UserDTO {
    private Integer id;
    private ArrayList<Game> games;
    private String login;
    private String pass;

    public UserDTO(ArrayList<Game> games, String login, String pass) {
        this.games = games;
        this.login = login;
        this.pass = pass;
    }

    public UserDTO(String login, String pass) {
        this.id = 0;
        this.login = login;
        this.pass = pass;
    }

    public UserDTO() {}

    public ArrayList<Game> getGames() {
        return games;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public Integer getId() {
        return id;
    }
}
