package com.lavnok.yuj.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseController {
    final FirebaseDatabase database=FirebaseDatabase.getInstance();
    static FireBaseController db=new FireBaseController();
     DatabaseReference ref;
    public static FireBaseController getInstance( ) {
        return  db;
    }

    private void setRef(String path){
        ref = database.getReference(path);
    }

//    static
}
