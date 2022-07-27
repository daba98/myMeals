package de.die_bartmanns.daniel.mymeal.data.database;

import android.content.Context;

public class DatabaseServiceFactory {

    public static DatabaseService getDatabaseService(Context context){
        return new DatabaseServiceImpl(context);
    }
}
