package model
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by elder.santos on 18/08/2017.
 */
@IgnoreExtraProperties
class AlternativeCorrect {
    var AlternativeCorrectId: String = ""

    constructor() {
        //this constructor is required
    }

    constructor(AlternativeCorrectId: String) {
        this.AlternativeCorrectId = AlternativeCorrectId
    }
}