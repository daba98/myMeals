package de.die_bartmanns.daniel.mymeal.data.onlineDatabase;

import android.content.Context;

public class OnlineDatabaseServiceFactory {

    public static OnlineDatabaseService getOnlineDatabaseService(Context context){
        return new OnlineDatabaseServiceImpl(context);
    }
}
