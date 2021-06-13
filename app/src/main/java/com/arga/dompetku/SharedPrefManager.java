package com.arga.dompetku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.util.Date;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "dompetku";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_FULLNAME = "keyfullname";
    private static final String KEY_CITY = "keycity";
    private static final String KEY_WALLET = "keywallet";
    private static final String KEY_ID_USER = "keyiduser";
    private static final String KEY_ID_HISTORY = "keyidhistory";
    private static final String KEY_JENIS = "keyjenis";
    private static final String KEY_NOMINAL = "keynominal";
    private static final String KEY_TANGGAL = "keytanggal";
    private static final String KEY_KEGIATAN = "keykegiatan";
    private static final String KEY_ID_WISHLIST = "keyidwishlist";
    private static final String KEY_WISH = "keywish";
    private static final String KEY_TAHUN = "keytahun";
    private static final String KEY_HARGA = "keyharga";
    private static com.arga.dompetku.SharedPrefManager mInstance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized com.arga.dompetku.SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new com.arga.dompetku.SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID_USER, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_FULLNAME, user.getFullname());
        editor.putString(KEY_CITY, user.getCity());
        editor.putInt(KEY_WALLET, user.getWallet());
        editor.apply();
    }

    public void addHistory(History history){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID_HISTORY, history.getId_history());
        editor.putInt(KEY_ID_USER, history.getId_user());
        editor.putString(KEY_JENIS, history.getJenis());
        editor.putInt(KEY_NOMINAL, history.getNominal());
        editor.putString(KEY_TANGGAL, history.getTanggal());
        editor.putString(KEY_KEGIATAN, history.getKegiatan());
        editor.apply();
    }

    public History getListHistory(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new History(
                sharedPreferences.getInt(KEY_ID_HISTORY, -1),
                sharedPreferences.getInt(KEY_ID_USER, -1),
                sharedPreferences.getString(KEY_JENIS, null),
                sharedPreferences.getInt(KEY_NOMINAL, -1),
                sharedPreferences.getString(KEY_TANGGAL, null),
                sharedPreferences.getString(KEY_KEGIATAN, null)
        );
    }

    public void addWishlist(Wishlist wishlist){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID_WISHLIST, wishlist.getId_wishlist());
        editor.putInt(KEY_ID_USER, wishlist.getId_user());
        editor.putString(KEY_WISH, wishlist.getWish());
        editor.putInt(KEY_TAHUN, wishlist.getTahun());
        editor.putInt(KEY_HARGA, wishlist.getHarga());
        editor.apply();
    }

    public Wishlist getWishlist(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Wishlist(
                sharedPreferences.getInt(KEY_ID_WISHLIST, -1),
                sharedPreferences.getInt(KEY_ID_USER, -1),
                sharedPreferences.getString(KEY_WISH, null),
                sharedPreferences.getInt(KEY_TAHUN, -1),
                sharedPreferences.getInt(KEY_HARGA, -1)
        );
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID_USER, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_FULLNAME, null),
                sharedPreferences.getString(KEY_CITY, null),
                sharedPreferences.getInt(KEY_WALLET, -1)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }
}