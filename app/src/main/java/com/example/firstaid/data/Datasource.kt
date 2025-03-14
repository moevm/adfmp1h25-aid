package com.example.firstaid.data

import com.example.firstaid.R
import com.example.firstaid.model.Guide
import com.example.firstaid.model.LegalInfo
import com.example.firstaid.model.PageItem
import com.example.firstaid.model.Question
import com.example.firstaid.model.Step
import com.example.firstaid.model.Hospital

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

    val questions = listOf(
        Question(
            id = 1,
            text = "Есть ли у пострадавшего дыхание?",
            tag = "no_breathing"
        ),
        Question(
            id = 2,
            text = "Есть ли у пострадавшего пульс?",
            tag = "no_pulse"
        ),
        Question(
            id = 3,
            text = "Есть ли видимые травмы?",
            tag = "visible_injuries"
        )
    )

    val guidesList = listOf(
        Guide(
            id = 1,
            title = "Потеря сознания",
            description = "Что делать, если человек потерял сознание?",
            steps = listOf(
                Step(
                    title = "1. Проверка безопасности",
                    items = listOf(
                        PageItem.TextItem("Убедитесь, что окружающая обстановка безопасна"),
                        PageItem.ImageItem(R.drawable.im1_1)
                    )
                ),
                Step(
                    title = "2. Проверка реакции",
                    items = listOf(
                        PageItem.TextItem("Аккуратно потрясите за плечи и громко спросите: «Вы в порядке?»")
                    )
                )
            ),
            tags = listOf("consciousness_loss")
        ),
        Guide(
            id = 2,
            title = "Сердечно-легочная реанимация",
            description = "Действия при отсутствии дыхания и пульса",
            steps = listOf(
                Step(
                    title = "1. Вызов помощи",
                    items = listOf(
                        PageItem.TextItem("Позвоните 112 или вызовите скорую помощь"),
                        //PageItem.ImageItem(R.drawable.im2_1)
                    )
                ),
                Step(
                    title = "2. Непрямой массаж сердца",
                    items = listOf(
                        PageItem.TextItem("Надавливайте на грудину с частотой 100-120 раз в минуту")
                    )
                )
            ),
            tags = listOf("no_breathing", "no_pulse")
        ),
        Guide(
            id = 3,
            title = "Первая помощь при кровотечении",
            description = "Остановка сильного кровотечения",
            steps = listOf(
                Step(
                    title = "1. Давление на рану",
                    items = listOf(
                        PageItem.TextItem("Наложите стерильную повязку и сильно надавите"),
                        //PageItem.ImageItem(R.drawable.im3_1)
                    )
                ),
                Step(
                    title = "2. Наложение жгута",
                    items = listOf(
                        PageItem.TextItem("Используйте жгут только при артериальном кровотечении")
                    )
                )
            ),
            tags = listOf("visible_injuries")
        ),
        Guide(
            id = 4,
            title = "Общие рекомендации",
            description = "Базовые действия первой помощи",
            steps = listOf(
                Step(
                    title = "1. Оценка ситуации",
                    items = listOf(
                        PageItem.TextItem("Убедитесь в отсутствии опасности для себя и пострадавшего")
                    )
                ),
                Step(
                    title = "2. Вызов скорой помощи",
                    items = listOf(
                        PageItem.TextItem("Наберите 112 и четко опишите ситуацию")
                    )
                )
            ),
            tags = listOf("general")
        )
    )

    // Функция фильтрации (добавить в объект Datasource)
    fun getMatchingGuides(selectedTags: List<String>): List<Guide> {
        val matched = guidesList.filter { guide ->
            guide.tags.any { it in selectedTags }
        }

        return if (matched.isEmpty()) {
            listOf(guidesList.first { it.tags.contains("general") })
        } else {
            matched.sortedByDescending { it.tags.size }
        }
    }

}