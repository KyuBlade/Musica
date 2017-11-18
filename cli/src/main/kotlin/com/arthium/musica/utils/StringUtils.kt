package com.arthium.musica.utils

object StringUtils {

    /**
     * Format timestamp to a string.
     *
     * @param timestamp timestamp in milliseconds
     * @return a formatted string of the timestamp in format HH:mm:ss
     */
    fun formatDuration(timestamp: Long): String {

        var remaining: Long = timestamp
        val hours: Long = remaining / 3600000L

        remaining -= hours * 3600000L

        val minutes: Long = remaining / 60000L
        remaining -= minutes * 60000L

        val seconds: Long = remaining / 1000L

        val builder = StringBuilder()
        with(builder) {

            // Hours
            if (hours > 0) {
                if (hours < 10)
                    append('0')

                append("$hours:")
            }

            // Minutes
            if (minutes < 10)
                append('0')

            append("$minutes:")

            // Seconds
            if (seconds < 10)
                append('0')

            append(seconds)

            return toString()
        }
    }
}