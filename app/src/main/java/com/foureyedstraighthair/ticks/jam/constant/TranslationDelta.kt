package com.foureyedstraighthair.ticks.jam.constant

data class TranslationDelta(
    val value: Float,
    val type: Type) {

    enum class Type {
        ABSOLUTE,
        FRACTION,
        FRACTION_P,
        DIMEN_DP
    }

    companion object {

        private val regexAbsolute = """-?(0|[1-9][0-9]*)(.[0-9]+)?""".toRegex()
        private val regexFraction = """-?(0|[1-9][0-9]*)(.[0-9]+)?%""".toRegex()
        private val regexFractionP = """-?(0|[1-9][0-9]*)(.[0-9]+)?%p""".toRegex()
        private val regexDimenDp = """-?(0|[1-9][0-9]*)(.[0-9]+)?dp""".toRegex()

        fun from(string: String?) = when {

            string == null -> null

            regexAbsolute.matches(string) ->
                TranslationDelta(string.toFloat(), Type.ABSOLUTE)

            regexFraction.matches(string) ->
                TranslationDelta(string.substring(
                    0 until (string.length - 1)).toFloat() / 100f, Type.FRACTION)

            regexFractionP.matches(string) ->
                TranslationDelta(string.substring(
                    0 until (string.length - 2)).toFloat() / 100f, Type.FRACTION_P)

            regexDimenDp.matches(string) ->
                TranslationDelta(string.substring(
                    0 until (string.length - 2)).toFloat(), Type.DIMEN_DP)

            else -> null
        }
    }
}