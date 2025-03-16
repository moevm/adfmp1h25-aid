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
            id = 0,
            title = "Общие указания по оказанию первой помощи",
            description = "Базовые шаги для оказания первой помощи в экстренных ситуациях",
            steps = listOf(
                Step(
                    title = "1. Обеспечьте безопасность",
                    items = listOf(
                        PageItem.TextItem("Убедитесь, что вам и пострадавшему ничего не угрожает (огонь, ток, обрушение и т.д.)"),
                        PageItem.TextItem("Если опасность присутствует, постарайтесь устранить её или переместите пострадавшего в безопасное место"),
                        PageItem.ImageItem(R.drawable.im0_1)
                    )
                ),
                Step(
                    title = "2. Оцените состояние пострадавшего",
                    items = listOf(
                        PageItem.TextItem("Проверьте сознание: аккуратно потрясите за плечи, громко окликните"),
                        PageItem.TextItem("Определите наличие дыхания: посмотрите на движение грудной клетки, прислушайтесь к дыханию"),
                        PageItem.TextItem("Проверьте пульс на сонной артерии (если умеете)")
                    )
                ),
                Step(
                    title = "3. Вызовите скорую помощь",
                    items = listOf(
                        PageItem.TextItem("Немедленно позвоните по номеру 112 или поручите это сделать окружающим"),
                        PageItem.TextItem("Сообщите оператору: место происшествия, состояние пострадавшего, количество пострадавших, свои контактные данные")
                    )
                ),
                Step(
                    title = "4. Окажите первую помощь",
                    items = listOf(
                        PageItem.TextItem("Если пострадавший без сознания, но дышит — уложите его в устойчивое боковое положение"),
                        PageItem.TextItem("Если дыхание отсутствует — начните сердечно-легочную реанимацию (СЛР)"),
                        PageItem.TextItem("Остановите сильное кровотечение, если оно есть, с помощью прямого давления на рану или наложения жгута"),
                        PageItem.TextItem("Не перемещайте пострадавшего без крайней необходимости, особенно при подозрении на травму позвоночника")
                    )
                ),
                Step(
                    title = "5. Поддерживайте состояние пострадавшего",
                    items = listOf(
                        PageItem.TextItem("Укройте пострадавшего, чтобы предотвратить переохлаждение"),
                        PageItem.TextItem("Разговаривайте с пострадавшим, успокаивайте его"),
                        PageItem.TextItem("Не давайте воду, еду или лекарства, если это не рекомендовано диспетчером скорой помощи")
                    )
                ),
                Step(
                    title = "6. Дождитесь прибытия медиков",
                    items = listOf(
                        PageItem.TextItem("Не оставляйте пострадавшего одного"),
                        PageItem.TextItem("Сообщите прибывшим медикам все известные детали происшествия и оказанной помощи")
                    )
                )
            ),
            tags = listOf("general")
        ),
        Guide(
            id = 1,
            title = "Потеря сознания",
            description = "Первая помощь при обмороке и потере сознания",
            steps = listOf(
                Step(
                    title = "1. Оценка состояния",
                    items = listOf(
                        PageItem.TextItem("Проверьте реакцию: аккуратно потрясите за плечи, громко окликните"),
                    )
                ),
                Step(
                    title = "2. Вызов скорой помощи",
                    items = listOf(
                        PageItem.TextItem("Если человек не приходит в сознание более 1 минуты - немедленно звоните 112"),
                        PageItem.TextItem("Сообщите оператору: возраст пострадавшего, обстоятельства, состояние")
                    )
                ),
                Step(
                    title = "3. Восстановительное положение",
                    items = listOf(
                        PageItem.TextItem("Уложите на бок для предотвращения асфиксии"),
                        PageItem.TextItem("Подложите что-то мягкое под голову"),
                        PageItem.ImageItem(R.drawable.im1_1)
                    )
                ),
                Step(
                    title = "4. Контроль состояния",
                    items = listOf(
                        PageItem.TextItem("Проверяйте дыхание каждые 2 минуты"),
                        PageItem.TextItem("При остановке дыхания - начинайте СЛР")
                    )
                )
            ),
            tags = listOf("general")
        ),
        Guide(
            id = 2,
            title = "Сердечно-легочная реанимация (СЛР)",
            description = "Экстренная помощь при остановке сердца",
            steps = listOf(
                Step(
                    title = "1. Проверка безопасности",
                    items = listOf(
                        PageItem.TextItem("Убедитесь в отсутствии угрозы (огонь, ток, обрушение)"),
                    )
                ),
                Step(
                    title = "2. Оценка состояния",
                    items = listOf(
                        PageItem.TextItem("Проверьте сознание: громко окликните, похлопайте по плечам"),
                        PageItem.TextItem("Определите наличие дыхания: посмотрите на движение грудной клетки")
                    )
                ),
                Step(
                    title = "3. Вызов экстренных служб",
                    items = listOf(
                        PageItem.TextItem("Позвоните 112 или поручите сделать это окружающим"),
                        PageItem.TextItem("Сообщите: 'Человек без сознания, не дышит, требуется реанимация'")
                    )
                ),
                Step(
                    title = "4. Начало компрессий",
                    items = listOf(
                        PageItem.TextItem("Положите ладони одна на другую на центр грудной клетки"),
                        PageItem.TextItem("Глубина нажатий: 5-6 см, частота: 100-120 в минуту"),
                        PageItem.ImageItem(R.drawable.im2_1)
                    )
                ),
                Step(
                    title = "5. Искусственное дыхание",
                    items = listOf(
                        PageItem.TextItem("Запрокиньте голову, зажмите нос, сделайте 2 вдоха"),
                        PageItem.TextItem("Соотношение: 30 нажатий → 2 вдоха")
                    )
                ),
                Step(
                    title = "6. Продолжение реанимации",
                    items = listOf(
                        PageItem.TextItem("Не прерывайте СЛР до прибытия медиков"),
                        PageItem.TextItem("Если есть дефибриллятор - используйте по инструкции")
                    )
                )
            ),
            tags = listOf("no_breathing", "no_pulse")
         ),
        Guide(
            id = 3,
            title = "Сильное кровотечение",
            description = "Остановка опасной для жизни кровопотери",
            steps = listOf(
                Step(
                    title = "1. Оценка типа кровотечения",
                    items = listOf(
                        PageItem.TextItem("Артериальное: ярко-алая кровь пульсирующей струей"),
                        PageItem.TextItem("Венозное: темная кровь равномерным потоком"),
                        PageItem.ImageItem(R.drawable.im3_1png)
                    )
                ),
                Step(
                    title = "2. Прямое давление на рану",
                    items = listOf(
                        PageItem.TextItem("Используйте стерильную салфетку или чистую ткань"),
                        PageItem.TextItem("Давите всей ладонью в течение 10-15 минут")
                    )
                ),
                Step(
                    title = "3. Наложение жгута",
                    items = listOf(
                        PageItem.TextItem("ТОЛЬКО при артериальном кровотечении"),
                        PageItem.TextItem("Накладывайте на 5-7 см выше раны"),
                        PageItem.TextItem("Запишите время наложения (максимум 1 час)"),
                        //PageItem.ImageItem(R.drawable.bleeding2)
                    )
                ),
                Step(
                    title = "4. Вызов скорой помощи",
                    items = listOf(
                        PageItem.TextItem("Немедленно звоните 112 при:"),
                        PageItem.TextItem("- Кровь не останавливается за 15 минут"),
                        PageItem.TextItem("- Пострадавший бледный, холодный, сонливый")
                    )
                )
            ),
            tags = listOf("visible_injuries")
        ),
        Guide(
            id = 4,
            title = "Тяжелые ожоги",
            description = "Первая помощь при ожогах II-IV степени",
            steps = listOf(
                Step(
                    title = "1. Прекращение воздействия",
                    items = listOf(
                        PageItem.TextItem("Уберите источник ожога (огонь, химикаты)"),
                        PageItem.TextItem("Снимите горящую/пропитанную одежду (не прилипшую!)")
                    )
                ),
                Step(
                    title = "2. Охлаждение ожога",
                    items = listOf(
                        PageItem.ImageItem(R.drawable.im4_1),
                        PageItem.TextItem("Промывайте прохладной водой 15-20 минут"),
                        PageItem.TextItem("Не используйте лед! Это вызовет обморожение")
                    )
                ),
                Step(
                    title = "3. Защита раны",
                    items = listOf(
                        PageItem.TextItem("Накройте стерильной неадгезивной повязкой"),
                        PageItem.TextItem("Не вскрывайте пузыри, не мажьте маслом/мазями")
                    )
                ),
                Step(
                    title = "4. Экстренный вызов",
                    items = listOf(
                        PageItem.TextItem("Срочно звоните 112 если:"),
                        PageItem.TextItem("- Площадь ожога больше ладони пострадавшего"),
                        PageItem.TextItem("- Поражены лицо, гениталии, дыхательные пути")
                    )
                )
            ),
            tags = listOf("visible_injuries")
        ),
        Guide(
            id = 5,
            title = "Удушье",
            description = "Помощь при попадании инородного тела в дыхательные пути",
            steps = listOf(
                Step(
                    title = "1. Оценка состояния",
                    items = listOf(
                        PageItem.TextItem("Определите, может ли пострадавший кашлять или говорить"),
                        PageItem.TextItem("Если пострадавший кашляет — поощряйте его продолжать кашлять")
                    )
                ),
                Step(
                    title = "2. Помощь при частичной закупорке",
                    items = listOf(
                        PageItem.ImageItem(R.drawable.im5_1),
                        PageItem.TextItem("Если пострадавший может дышать, но с трудом, не вмешивайтесь, пока он может кашлять"),
                        PageItem.TextItem("Продолжайте наблюдать за его состоянием")
                    )
                ),
                Step(
                    title = "3. Помощь при полной закупорке",
                    items = listOf(
                        PageItem.TextItem("Если пострадавший не может дышать, говорить или кашлять, выполните прием Геймлиха:"),
                        PageItem.TextItem("Встаньте сзади пострадавшего, обхватите его руками за талию"),
                        PageItem.TextItem("Сожмите одну руку в кулак и поместите его выше пупка, но ниже грудной клетки"),
                        PageItem.TextItem("Резко надавите внутрь и вверх, повторяйте до извлечения предмета")
                    )
                ),
                Step(
                    title = "4. Вызов скорой помощи",
                    items = listOf(
                        PageItem.TextItem("Если пострадавший теряет сознание, немедленно звоните 112"),
                        PageItem.TextItem("Начните сердечно-легочную реанимацию (СЛР), если дыхание отсутствует")
                    )
                )
            ),
            tags = listOf("no_breathing")
        ),
        Guide(
            id = 6,
            title = "Переломы",
            description = "Первая помощь при переломах костей",
            steps = listOf(
                Step(
                    title = "1. Оценка состояния",
                    items = listOf(
                        PageItem.TextItem("Определите место перелома: боль, отек, деформация, невозможность двигать конечностью"),
                        PageItem.TextItem("Проверьте наличие открытых ран или кровотечения")
                    )
                ),
                Step(
                    title = "2. Иммобилизация",
                    items = listOf(
                        PageItem.ImageItem(R.drawable.im6_1),
                        PageItem.TextItem("Зафиксируйте поврежденную конечность с помощью шины или подручных средств (доска, картон)"),
                        PageItem.TextItem("Шину накладывайте так, чтобы обездвижить суставы выше и ниже перелома"),
                        PageItem.TextItem("Не пытайтесь вправлять кости!")
                    )
                ),
                Step(
                    title = "3. Остановка кровотечения",
                    items = listOf(
                        PageItem.TextItem("Если есть открытая рана, наложите стерильную повязку"),
                        PageItem.TextItem("При сильном кровотечении используйте жгут (только для конечностей)")
                    )
                ),
                Step(
                    title = "4. Вызов скорой помощи",
                    items = listOf(
                        PageItem.TextItem("Немедленно звоните 112, если:"),
                        PageItem.TextItem("- Перелом открытый"),
                        PageItem.TextItem("- Пострадавший испытывает сильную боль или шок")
                    )
                )
            ),
            tags = listOf("visible_injuries")
        ),
        Guide(
            id = 7,
            title = "Отравление",
            description = "Первая помощь при отравлении",
            steps = listOf(
                Step(
                    title = "1. Оценка состояния",
                    items = listOf(
                        PageItem.TextItem("Определите, чем отравился пострадавший (еда, химикаты, лекарства)"),
                        PageItem.TextItem("Проверьте симптомы: тошнота, рвота, головокружение, боль в животе")
                    )
                ),
                Step(
                    title = "2. Промывание желудка",
                    items = listOf(
                        PageItem.TextItem("Если пострадавший в сознании, дайте выпить 2-3 стакана воды"),
                        PageItem.TextItem("Вызовите рвоту, нажав на корень языка (только если отравление произошло менее 30 минут назад)")
                    )
                ),
                Step(
                    title = "3. Активированный уголь",
                    items = listOf(
                        PageItem.TextItem("Дайте пострадавшему активированный уголь (1 таблетка на 10 кг веса)"),
                        PageItem.TextItem("Запивайте большим количеством воды")
                    )
                ),
                Step(
                    title = "4. Вызов скорой помощи",
                    items = listOf(
                        PageItem.TextItem("Немедленно звоните 112, если:"),
                        PageItem.TextItem("- Пострадавший без сознания"),
                        PageItem.TextItem("- Отравление химикатами или лекарствами")
                    )
                )
            ),
            tags = listOf("general")
        ),
        Guide(
            id = 8,
            title = "Тепловой удар",
            description = "Первая помощь при перегреве",
            steps = listOf(
                Step(
                    title = "1. Оценка состояния",
                    items = listOf(
                        PageItem.TextItem("Определите симптомы: высокая температура, сухость кожи, головокружение, тошнота"),
                        PageItem.TextItem("Проверьте пульс и дыхание")
                    )
                ),
                Step(
                    title = "2. Охлаждение",
                    items = listOf(
                        PageItem.TextItem("Перенесите пострадавшего в прохладное место"),
                        PageItem.TextItem("Снимите лишнюю одежду"),
                        PageItem.TextItem("Приложите холодные компрессы к голове, шее, подмышкам"),
                        PageItem.ImageItem(R.drawable.im8_1)
                        )
                ),

                Step(
                    title = "3. Восстановление жидкости",
                    items = listOf(
                        PageItem.TextItem("Дайте пострадавшему прохладную воду (не ледяную)"),
                        PageItem.TextItem("Не давайте алкоголь или кофеин")
                    )
                ),
                Step(
                    title = "4. Вызов скорой помощи",
                    items = listOf(
                        PageItem.TextItem("Немедленно звоните 112, если:"),
                        PageItem.TextItem("- Пострадавший теряет сознание"),
                        PageItem.TextItem("- Симптомы не улучшаются через 30 минут")
                    )
                )
            ),
            tags = listOf("general")
        ),
        Guide(
            id = 9,
            title = "Эпилептический приступ",
            description = "Первая помощь при эпилептическом припадке",
            steps = listOf(
                Step(
                    title = "1. Обеспечение безопасности",
                    items = listOf(
                        PageItem.TextItem("Уберите опасные предметы вокруг пострадавшего"),
                        PageItem.TextItem("Подложите что-то мягкое под голову")
                    )
                ),
                Step(
                    title = "2. Контроль приступа",
                    items = listOf(
                        PageItem.ImageItem(R.drawable.im9_1),
                        PageItem.TextItem("Не пытайтесь удерживать пострадавшего"),
                        PageItem.TextItem("Не кладите ничего в рот (это может привести к травме)")
                    )
                ),
                Step(
                    title = "3. После приступа",
                    items = listOf(
                        PageItem.TextItem("Поверните пострадавшего на бок для предотвращения асфиксии"),
                        PageItem.TextItem("Оставайтесь рядом, пока он не придет в сознание")
                    )
                ),
                Step(
                    title = "4. Вызов скорой помощи",
                    items = listOf(
                        PageItem.TextItem("Звоните 112, если:"),
                        PageItem.TextItem("- Приступ длится более 5 минут"),
                        PageItem.TextItem("- Пострадавший не приходит в сознание после приступа")
                    )
                )
            ),
            tags = listOf("general")
        ),
        Guide(
            id = 10,
            title = "Астматический статус",
            description = "Помощь при тяжелом приступе астмы",
            steps = listOf(
                Step(
                    title = "1. Помощь с ингалятором",
                    items = listOf(
                        PageItem.TextItem("Помогите использовать личный ингалятор (сальбутамол)"),
                        PageItem.TextItem("Сделайте 2-4 вдоха с интервалом 2-3 минуты")
                    )
                ),
                Step(
                    title = "2. Положение тела",
                    items = listOf(
                        PageItem.TextItem("Посадите, наклонив вперед с опорой на руки"),
                        PageItem.TextItem("Расстегните тесную одежду")
                    )
                ),
                Step(
                    title = "3. Экстренный вызов",
                    items = listOf(
                        PageItem.TextItem("Немедленно звоните 112 если:"),
                        PageItem.TextItem("- Ингалятор не помогает через 10 минут"),
                        PageItem.TextItem("- Появилась синюшность губ, спутанность сознания")
                    )
                )
            ),
            tags = listOf("no_breathing")
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