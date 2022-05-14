package app.view;

import app.enums.CatBreed;
import app.enums.CatColor;

public class CatView {
    public String date;
    public CatBreed breed;
    public CatColor color;

    public CatView(String date, CatBreed breed, CatColor color) {
        this.date = date;
        this.breed = breed;
        this.color = color;
    }
}
