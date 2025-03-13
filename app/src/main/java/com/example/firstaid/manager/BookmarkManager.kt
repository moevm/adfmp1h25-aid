package com.example.firstaid.data

import android.content.Context
import androidx.core.content.edit

object BookmarkManager {
    private const val PREFS_NAME = "bookmarks_prefs"
    private const val BOOKMARKS_KEY = "bookmarked_guides"

    fun isBookmarked(context: Context, guideId: Int): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(BOOKMARKS_KEY, emptySet())?.contains(guideId.toString()) ?: false
    }

    fun toggleBookmark(context: Context, guideId: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val bookmarks = prefs.getStringSet(BOOKMARKS_KEY, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        if (bookmarks.contains(guideId.toString())) {
            bookmarks.remove(guideId.toString())
        } else {
            bookmarks.add(guideId.toString())
        }
        prefs.edit {
            putStringSet(BOOKMARKS_KEY, bookmarks)
        }
    }

    fun getBookmarkedIds(context: Context): Set<Int> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(BOOKMARKS_KEY, emptySet())
            ?.mapNotNull { it.toIntOrNull() }
            ?.toSet() ?: emptySet()
    }
}