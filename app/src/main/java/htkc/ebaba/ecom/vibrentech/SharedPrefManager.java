package htkc.ebaba.ecom.vibrentech;

import android.content.Context;
import android.content.SharedPreferences;

import htkc.ebaba.ecom.vibrentech.response.User;
import htkc.ebaba.ecom.vibrentech.response.Verify;


public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "chandrasharedpref";


    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_UNAME = "keyusername";
    private static final String KEY_USER_MOBILE = "keyusermobile";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_ADDRESS = "keyuseraddress";
    private static final String KEY_USER_VERIFY = "keyuserverify";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String uname, String mobile, String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_MOBILE, mobile);
        editor.putString(KEY_USER_UNAME, uname);
        editor.putString(KEY_USER_ID, id);

        editor.apply();
        return true;
    }
    public boolean userVerify(String verify) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_VERIFY, verify);

        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_MOBILE, null) != null)
            return true;
        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_USER_UNAME, null),
                sharedPreferences.getString(KEY_USER_MOBILE, null),
                sharedPreferences.getString(KEY_USER_ID, null)
        );
    }
    public Verify verifyNo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Verify(
                sharedPreferences.getString(KEY_USER_VERIFY, null)
        );
    }
    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}