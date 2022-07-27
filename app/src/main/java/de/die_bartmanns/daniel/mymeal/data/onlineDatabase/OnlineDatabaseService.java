package de.die_bartmanns.daniel.mymeal.data.onlineDatabase;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.data.Meal;

public interface OnlineDatabaseService {

    void uploadMeal(Meal meal);

    void uploadMeals(List<Meal> meals, FirestoreDataReceivingCallback<List<String>> onPostExecute);

    void downloadMeal(String documentId, String category);

    void downloadMeals(List<String> docIds, FirestoreDataReceivingCallback<GetMealsResult> callback);

    void getMeals(DocumentSnapshot lastVisible, FirestoreDataReceivingCallback<GetMealsResult> callback);
}
