package com.example.s7_cafe;

public class User_Input {

    private int menu_name;
    private int base;
    private int coffee;
    private int ice;
    private int cup;
    private int ingredient1;
    private int ingredient2;
    private int ingredient3;

    public User_Input() {}

    public User_Input(int menu_name, int cup, int base, int coffee, int ice, int ingredient1, int ingredient2, int ingredient3) {
        this.menu_name = menu_name;
        this.base = base;
        this.coffee = coffee;
        this.ice = ice;
        this.cup = cup;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
    }

    public int getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(int menu_name) {
        this.menu_name = menu_name;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getCoffee() {
        return coffee;
    }

    public void setCoffee(int coffee) {
        this.coffee = coffee;
    }

    public int getIce() {
        return ice;
    }

    public void setIce(int ice) {
        this.ice = ice;
    }

    public int getCup() {
        return cup;
    }

    public void setCup(int cup) {
        this.cup = cup;
    }

    public int getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(int ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public int getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(int ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public int getIngredient3() {
        return ingredient3;
    }

    public void setIngredient3(int ingredient3) {
        this.ingredient3 = ingredient3;
    }
}