package model
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by elder.santos on 16/08/2017.
 */

@IgnoreExtraProperties
class Questions  {
    var questionId: String = ""
    var questionTitle: String = ""
    var year: String = ""

    constructor() {
        //this constructor is required
    }

    constructor(questionId: String, question: String, year: String) {
        this.questionId = questionId
        this.questionTitle = question
        this.year = year

    }
}