package de.die_bartmanns.daniel.mymeal.data.onlineDatabase;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class GetMealsResult {

    private final List<OnlineMeal> meals;
    private final DocumentSnapshot lastVisible;

    public GetMealsResult(List<OnlineMeal> meals, DocumentSnapshot lastVisible) {
        this.meals = meals;
        this.lastVisible = lastVisible;
    }

    public List<OnlineMeal> getMeals() {
        return meals;
    }

    public DocumentSnapshot getLastVisible() {
        return lastVisible;
    }
}
