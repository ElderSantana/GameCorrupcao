package model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by elder.santos on 16/08/2017.
 */

@IgnoreExtraProperties
class Alternatives {

    var alternativeId: String = ""
    var alternativeTitle: String = ""

    constructor() {

    }

    constructor(alternativeId: String, alternativeTitle: String) {
        this.alternativeId = alternativeId
        this.alternativeTitle = alternativeTitle
    }
}