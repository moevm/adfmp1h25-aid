package com.example.firstaid.data

import com.example.firstaid.R
import com.example.firstaid.model.Guide
import com.example.firstaid.model.LegalInfo
import com.example.firstaid.model.PageItem
import com.example.firstaid.model.Question
import com.example.firstaid.model.Step
import com.example.firstaid.model.Hospital
import com.example.firstaid.model.HospitalType
import org.osmdroid.util.GeoPoint

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

    val hospitalsList = listOf(
        Hospital(
            id = 1,
            name = "Больница №1",
            address = "Ракитовская ул., 29",
            phone = "+7 (812) 293-45-49",
            workingHours = "09:00–16:00",
            coordinates = "60.0922, 30.2368",
            imageResId = R.drawable.hospital_id_1,
            type = HospitalType.HOSPITAL,
            geoPoint = GeoPoint(60.0922, 30.2368)
        ),
        Hospital(
            id = 2,
            name = "Поликлиника №5",
            address = "7-я линия Васильевского острова, 64",
            phone = "+7 (812) 242-38-58",
            workingHours = "08:00–20:00",
            coordinates = "59.9447, 30.2756",
            imageResId = R.drawable.hospital_id_2,
            type = HospitalType.POLYCLINIC,
            geoPoint = GeoPoint(59.9447, 30.2756)
        ),
        Hospital(
            id = 3,
            name = "Травмпункт №3",
            address = "8-я линия Васильевского острова, 51",
            phone = "+7 (812) 241-57-88",
            workingHours = "Круглосуточно",
            coordinates = "59.9434, 30.2759",
            imageResId = R.drawable.hospital_id_3,
            type = HospitalType.TRAUMA_CENTER,
            geoPoint = GeoPoint(59.9434, 30.2759)
        ),
        Hospital(
            id = 4,
            name = "Станция №1",
            address = "ул. Профессора Попова, 16Б",
            phone = "+7 (812) 234-48-55",
            workingHours = "Круглосуточно",
            coordinates = "59.9709, 30.3099",
            imageResId = R.drawable.hospital_id_4,
            type = HospitalType.HOSPITAL,  // Or create new type if needed
            geoPoint = GeoPoint(59.9709, 30.3099)
        ),
        Hospital(
            id = 5,
            name = "Отделение экстренной медицинской помощи",
            address = "Будапештская ул., 3",
            phone = "+7 (812) 705-29-70",
            workingHours = "Круглосуточно",
            coordinates = "59.8737, 30.3638",
            imageResId = R.drawable.hospital_id_5,
            type = HospitalType.HOSPITAL,  // Main hospital type for emergency
            geoPoint = GeoPoint(59.8737, 30.3638)
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