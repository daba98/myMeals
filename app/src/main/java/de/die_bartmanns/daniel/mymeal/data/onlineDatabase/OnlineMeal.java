package de.die_bartmanns.daniel.mymeal.data.onlineDatabase;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.List;

import de.die_bartmanns.daniel.mymeal.data.Difficulty;
import de.die_bartmanns.daniel.mymeal.data.MealType;
import de.die_bartmanns.daniel.mymeal.data.Recipe;

public class OnlineMeal implements Serializable {
    
    private String name;
    private int timeToCook;
    private Difficulty difficulty;
    private float rating;
    private int nboRatings;
    private String photo;
    private Recipe recipe;
    private long creationTimestamp;
    private MealType type;

    private boolean withMeat;
    private boolean withNoodles;
    private boolean withPotatoes;
    private boolean withRice;
    private boolean withVegetables;
    private boolean withFish;

    @Exclude
    private String documentId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeToCook() {
        return timeToCook;
    }

    public void setTimeToCook(int timeToCook) {
        this.timeToCook = timeToCook;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNboRatings() {
        return nboRatings;
    }

    public void setNboRatings(int nboRatings) {
        this.nboRatings = nboRatings;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public MealType getType() {
        return type;
    }

    public void setType(MealType type) {
        this.type = type;
    }

    public boolean isWithMeat() {
        return withMeat;
    }

    public void setWithMeat(boolean withMeat) {
        this.withMeat = withMeat;
    }

    public boolean isWithNoodles() {
        return withNoodles;
    }

    public void setWithNoodles(boolean withNoodles) {
        this.withNoodles = withNoodles;
    }

    public boolean isWithPotatoes() {
        return withPotatoes;
    }

    public void setWithPotatoes(boolean withPotatoes) {
        this.withPotatoes = withPotatoes;
    }

    public boolean isWithRice() {
        return withRice;
    }

    public void setWithRice(boolean withRice) {
        this.withRice = withRice;
    }

    public boolean isWithVegetables() {
        return withVegetables;
    }

    public void setWithVegetables(boolean withVegetables) {
        this.withVegetables = withVegetables;
    }

    public boolean isWithFish() {
        return withFish;
    }

    public void setWithFish(boolean withFish) {
        this.withFish = withFish;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
