package com.example.project;

public class Recipe {
    public String рецепт;
    public String ингредиенты;

    public Recipe() {};

    public Recipe(String рецепт, String ингредиенты) {
        this.рецепт=рецепт;
        this.ингредиенты=ингредиенты;
    }

    public String getРецепт() {
        return рецепт;
    }

    public void setРецепт(String рецепт) {
        this.рецепт = рецепт;
    }

    public String getИнгредиенты() {
        return ингредиенты;
    }

    public void setИнгредиенты(String ингредиенты) {
        this.ингредиенты = ингредиенты;
    }
}
