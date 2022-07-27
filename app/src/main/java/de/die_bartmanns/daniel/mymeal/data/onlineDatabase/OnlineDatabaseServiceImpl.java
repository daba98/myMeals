package de.die_bartmanns.daniel.mymeal.data.onlineDatabase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.die_bartmanns.daniel.mymeal.R;
import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseService;
import de.die_bartmanns.daniel.mymeal.data.database.DatabaseServiceFactory;

public class OnlineDatabaseServiceImpl implements OnlineDatabaseService{

    private static final String MEAL_COLLECTION = "Gerichte";
    public static final int BUNCH_SIZE = 8;

    private final FirebaseFirestore db;
    private final Context context;

    private final DatabaseService service;


    public OnlineDatabaseServiceImpl(Context context){
        db = FirebaseFirestore.getInstance();
        service = DatabaseServiceFactory.getDatabaseService(context);
        this.context = context;
    }

    @Override
    public void uploadMeal(Meal meal) {
        AlertDialog loadingDialog = getLoadingDialog();
        loadingDialog.show();
        OnlineMeal onlineMeal = MealToOnlineMealConverter.toOnlineMeal(meal);
        db.collection(MEAL_COLLECTION).add(onlineMeal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                System.out.println("DOCUMENT REFERENCE ID: " + documentReference.getId());
                meal.setDocumentId(documentReference.getId());
                service.updateMeal(meal);
                loadingDialog.dismiss();
                showSuccessToast("Gericht hochgeladen!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                loadingDialog.dismiss();
                showFailureDialog();
            }
        });
    }

    @Override
    public void uploadMeals(List<Meal> meals, FirestoreDataReceivingCallback<List<String>> onPostExecute) {
        AlertDialog loadingDialog = getLoadingDialog();
        loadingDialog.show();
        WriteBatch batch = db.batch();
        List<String> docRefs = new ArrayList<>();
        for(Meal meal : meals){
            if(!meal.isUploaded()){
                DocumentReference docRef = db.collection(MEAL_COLLECTION).document();
                batch.set(docRef, MealToOnlineMealConverter.toOnlineMeal(meal));
                meal.setDocumentId(docRef.getId());
                docRefs.add(docRef.getId());
            }
            else
                docRefs.add(meal.getDocumentId());
        }

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                service.updateMeals(meals);
                loadingDialog.dismiss();
                onPostExecute.onCallback(docRefs);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingDialog.dismiss();
                showFailureDialog();;
            }
        });
    }

    @Override
    public void downloadMeal(String documentId, String category) {
        AlertDialog loadingDialog = getLoadingDialog();
        loadingDialog.show();
        DocumentReference document = db.collection(MEAL_COLLECTION).document(documentId);
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                OnlineMeal onlineMeal = documentSnapshot.toObject(OnlineMeal.class);
                Meal meal = MealToOnlineMealConverter.toMeal(onlineMeal);
                meal.setDocumentId(documentId);
                meal.setOwnCategory(category);
                service.insertMeal(meal);
                loadingDialog.dismiss();
                showSuccessToast("Gericht heruntergeladen!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                loadingDialog.dismiss();
                showFailureDialog();
            }
        });
    }


    public void downloadMeals(List<String> docIds, FirestoreDataReceivingCallback<GetMealsResult> callback){
        AlertDialog loadingDialog = getLoadingDialog();
        loadingDialog.show();
        db.collection(MEAL_COLLECTION).whereIn(FieldPath.documentId(), docIds).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<OnlineMeal> meals = new ArrayList<>();
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            OnlineMeal onlineMeal = doc.toObject(OnlineMeal.class);
                            onlineMeal.setDocumentId(doc.getId());
                            meals.add(onlineMeal);
                        }
                        loadingDialog.dismiss();
                        GetMealsResult result = new GetMealsResult(meals, null);
                        callback.onCallback(result);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingDialog.dismiss();
                showFailureDialog();
            }
        });
    }


    @Override
    public void getMeals(DocumentSnapshot lastVisible, FirestoreDataReceivingCallback<GetMealsResult> callback){
        Query query;
        if(lastVisible != null)
            query = db.collection(MEAL_COLLECTION).orderBy("creationTimestamp", Query.Direction.DESCENDING)
                    .startAfter(lastVisible)
                    .limit(BUNCH_SIZE);
        else
            query = db.collection(MEAL_COLLECTION).orderBy("creationTimestamp", Query.Direction.DESCENDING)
                    .limit(BUNCH_SIZE);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<OnlineMeal> meals = new ArrayList<>();
                for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                    OnlineMeal onlineMeal = doc.toObject(OnlineMeal.class);
                    onlineMeal.setDocumentId(doc.getId());
                    meals.add(onlineMeal);
                }
                DocumentSnapshot lastVisible = null;
                if(!queryDocumentSnapshots.isEmpty())
                    lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                GetMealsResult result = new GetMealsResult(meals, lastVisible);
                callback.onCallback(result);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                        showFailureDialog();
            }
        });
    }


    private void showFailureDialog(){
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.failure_dialog_title))
                .setMessage(context.getResources().getString(R.string.failure_dialog_message))
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private AlertDialog getLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.dialog_loading);
        return builder.create();
    }

    private void showSuccessToast(String message){

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_success,null);
        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);
        text.setPadding(20,0,20,0);
        text.setTextSize(18);
        text.setTextColor(Color.WHITE);
        text.setGravity(Gravity.CENTER);
        text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle_black_24dp, 0, 0, 0);
        text.setCompoundDrawablePadding(16);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
