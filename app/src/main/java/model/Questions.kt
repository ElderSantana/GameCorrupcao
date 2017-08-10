package model
import java.io.Serializable
/**
 * Created by elder.santos on 10/08/2017.
 */


class Questions : Serializable {

    var pergunta: String? = null

    fun Questions(pergunta: String) {
        this.pergunta = pergunta
    }
}
