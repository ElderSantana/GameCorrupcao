package model
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by elder.santos on 18/08/2017.
 */
@IgnoreExtraProperties
class Awnsers {
    var AlternativeId: String = ""

    constructor() {
        //this constructor is required
    }

    constructor(AlternativeCorrectId: String) {
        this.AlternativeId = AlternativeCorrectId
    }
}