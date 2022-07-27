package de.die_bartmanns.daniel.mymeal.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

import de.die_bartmanns.daniel.mymeal.R;

/**
 * Created by Daniel on 09.08.2018.
 */
@Entity(tableName = "Meals",
        foreignKeys = @ForeignKey(entity = OwnCategory.class,
                parentColumns = "name",
                childColumns = "ownCategory"),
        indices = {@Index(name = "index_type", value = {"type"}),
        @Index(name = "index_rating", value = {"rating"}),
        @Index(name = "index_difficulty", value = {"difficulty"})})
public class Meal implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int timeToCook;
    private Difficulty difficulty;
    private int rating;
    private byte[] photoByteArray;
    private Recipe recipe;
    private MealType type;
    private String ownCategory;

    private boolean withMeat;
    private boolean withNoodles;
    private boolean withPotatoes;
    private boolean withRice;
    private boolean withVegetables;
    private boolean withFish;

    private String documentId;


    public Meal(){
        this(0);
    }

    @Ignore
    private Meal(long id){
        this.id = id;
        this.recipe = new Recipe();
    }

    @Ignore
    public Meal(MealType type){
        this(0, type);
    }

    @Ignore
    private Meal(long id, MealType type){
        this.id = id;
        this.type = type;
        this.recipe = new Recipe();
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public byte[] getPhotoByteArray() {
        return photoByteArray;
    }

    public void setPhotoByteArray(byte[] photoByteArray) {
        this.photoByteArray = photoByteArray;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
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

    public String getOwnCategory() {
        return ownCategory;
    }

    public void setOwnCategory(String ownCategory) {
        this.ownCategory = ownCategory;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getDefaultImageResId(){
        return type.getPhotoResId();
    }

    public int getColorResId(){
        return type.getColorResId();
    }

    public int getColorDarkResId(){
        return type.getDarkColorResId();
    }

    public boolean isUploaded(){
        return documentId != null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return id == meal.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
