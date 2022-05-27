package com.example.contract.ui.model

import com.example.contract.ui.UIState

/**
 * Abbreviation stands for Loading, Error, State
 * Pretty common for every screen, so I decided to make a wrapper.
 *
 * You can model yours screens state completely in different way,
 * depending on your personal preferences.
 *
 * It's just fun name, which reminds me of russian proverb:
 * "За деревьями леса не видишь".
 *
 * That subject (distinction between main and support things) surely was in my talk.
 * You should focus on certain ("main") things.
 * Usage of this wrapper is not such a thing. =)
 */
data class LES<T: UIState>(
    val isLoading: Boolean = false,
    val errorText: String? = null,
    val state: T? = null
) {
    val isError = errorText != null
    val isCorrect = isLoading || isError || state != null
}