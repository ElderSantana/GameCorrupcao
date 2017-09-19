package model.util

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference



/**
 * Created by elder.santos on 19/09/2017.
 */
object LibraryClass {
    var PREF = "com.example.elder.quizz.feature.login.PREF"
    private var firebase: DatabaseReference? = null

    fun getFirebase(): DatabaseReference? {
        if (firebase == null) {
            firebase = FirebaseDatabase.getInstance().reference
        }

        return firebase
    }

    fun saveSP(context: Context, key: String, value: String) {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        sp.edit().putString(key, value).apply()
    }

    fun getSP(context: Context, key: String): String {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return sp.getString(key, "")
    }
}