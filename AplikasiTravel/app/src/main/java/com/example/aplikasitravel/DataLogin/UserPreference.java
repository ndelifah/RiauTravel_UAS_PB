package com.example.aplikasitravel.DataLogin;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    private static final String PREFS_NAME = "user_pref";
    //KEY
    private static final String EMAIL = "EMAIL";
    private static final String FULLNAME = "FULLNAME";
    private static final String ID = "ID";

    private static final String ISLOGIN = "ISLOGIN";

    //share preference
    private final SharedPreferences sharedPreferences;

    //constructor
    public UserPreference(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public void setUser(User user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //KEY DAN VALUE
        //"USERNAME" DAN "TEST"
        editor.putString(EMAIL, user.email);
        editor.putString(FULLNAME, user.fullname);
        editor.putString(ID, user.id);
        editor.putBoolean(ISLOGIN, user.isLogin);
        editor.apply();
    }
    public User getUser() {
        User user = new User();
        user.setFullname(sharedPreferences.getString(FULLNAME, ""));
        user.setEmail(sharedPreferences.getString(EMAIL, ""));
        user.setId(sharedPreferences.getString(ID, ""));
        user.setLogin(sharedPreferences.getBoolean(ISLOGIN, false));

        return user;
    }
}