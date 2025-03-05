package com.example.firstaid.data

import com.example.firstaid.model.Guide
import com.example.firstaid.model.LegalInfo

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
        Guide(id = 1, title = "Вывих ноги", inBookmarks = true),
        Guide(id = 2, title = "Пулевое ранение", inBookmarks = false),
        Guide(id = 3, title = "Потеря сознания", inBookmarks = true)
    )
}