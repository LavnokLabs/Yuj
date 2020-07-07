package com.lavnok.yuj.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseController {
    final static FirebaseDatabase database=FirebaseDatabase.getInstance();
    static {
        database.setPersistenceEnabled(true);
    }
    static FireBaseController db=new FireBaseController();
     DatabaseReference ref;
    public static FirebaseDatabase getInstance( ) {
        return database;
    }
}
