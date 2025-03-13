package com.example.firstaid

sealed class Route(val name: String) {
    data object Disclaimer : Route("disclaimer")
    data object Main : Route("main")
    data object Bookmarks : Route("bookmarks")
    data object About : Route("about")
    data object LegalInfo : Route("legalInfo")

    data object Search : Route("Search")
    data object HospitalsMap : Route("HospitalsMap")

    data object GuidesList : Route("guidesList")
    data object GuideDetail : Route("guideDetail")

    data object Questionnaire : Route("questionnaire")
    data object QuestionnaireResult : Route("questionnaireResult")
}

