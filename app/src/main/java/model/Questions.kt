package model
import com.google.firebase.database.IgnoreExtraProperties



/**
 * Created by elder.santos on 16/08/2017.
 */

@IgnoreExtraProperties
class Questions {
    var pergunta: String = ""

    init {

    }

//    constructor()

    fun constructor(pergunta :String) {
         this.pergunta = pergunta
    }






}