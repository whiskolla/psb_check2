package tests.api.at3.dtos;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private String company;
    private String description;
    private ArrayList<Dlc> dlcs;
    private int gameId;
    private String genre;
    private boolean isFree;
    private int price;
    private Date publish_date;
    private int rating;
    private boolean requiredAge;
    private Requirements requirements;
    private ArrayList<String> tags;
    private String title;
}
