package com.example.firstaid.model

data class Question(
    val id: Int,
    val text: String,
    val tag: String
)

data class Guide(
    val id: Int,
    val title: String,
    val description: String,
    val steps: List<Step>,
    val tags: List<String>
)
data class Step(
    val title: String,
    val items: List<PageItem>
)


sealed class PageItem {
    data class TextItem(val text: String) : PageItem()
    data class ImageItem(val imageResId: Int) : PageItem()
}