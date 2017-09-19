package model

import android.content.Context
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import model.util.LibraryClass


/**
 * Created by elder.santos on 19/09/2017.
 */


class User {
    var PROVIDER = "com.example.elder.quizz.model.login.User.PROVIDER"

    var id: String? = null
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var newPassword: String? = null

    constructor() {
        //this constructor is required
    }

    constructor(id: String, name: String,  email: String, password: String , newPassword:String) {
        this.id = id
        this.name = name
        this.email = email
        this.password = password
        this.newPassword = newPassword

    }

    fun saveProviderSP(context: Context, token: String) {
        LibraryClass.saveSP(context, PROVIDER, token)
    }

}