package de.die_bartmanns.daniel.mymeal.data.onlineDatabase;

public interface FirestoreDataReceivingCallback<T> {

    void onCallback(T data);
}
