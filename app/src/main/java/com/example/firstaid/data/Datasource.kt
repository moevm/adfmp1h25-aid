package com.example.firstaid.data

import com.example.firstaid.model.Guide
import com.example.firstaid.model.LegalInfo
import com.example.firstaid.model.PageItem
import com.example.firstaid.model.Step

object Datasource {
    val legalInfoList = listOf(
        LegalInfo(
            title = "Определение “Первой помощи”",
            text = "Федеральный закон от 21.11.2011 г. № 323-ФЗ «Об основах охраны здоровья граждан в Российской Федерации» определяет первую помощь как особый вид помощи (отличный от медицинской), оказываемой лицами, не имеющими медицинского образования, при травмах и неотложных состояниях до прибытия медицинского персонала."
        ),
        LegalInfo(
            title = "Кто имеет право на оказание первой помощи?",
            text = "Согласно ч. 4 ст. 31 Федерального закона от 21.11.2011 г. № 323-ФЗ «Об основах охраны здоровья граждан в Российской Федерации» каждый гражданин имеет право оказывать первую помощь при наличии соответствующей подготовки и (или) навыков."
        )
    )

    val guidesList = listOf(
        Guide(
            id = 1,
            title = "Потеря сознания",
            description = "Что делать, если человек потерял сознание?",
            steps = listOf(
                Step(
                    title = "Шаг 1: Уложите человека",
                    items = listOf(
                        PageItem.TextItem("Уложите человека, если он сидит, чтобы облегчить приток крови к мозгу."),
                        //PageItem.ImageItem(R.drawable.step1_image)
                    )
                ),
                Step(
                    title = "Шаг 2: Обеспечьте доступ кислорода",
                    items = listOf(
                        PageItem.TextItem("Распахните одежду на шее, откройте окно, вынесите человека на улицу, в тень и т.д.")
                    )
                )
            ),
            inBookmarks = true
        )
    )

    val questionnaireQuestions = listOf(
        "Есть ли у пострадавшего дыхание?",
        "Есть ли у пострадавшего пульс?",
        "Есть ли видимые травмы?"
    )
}