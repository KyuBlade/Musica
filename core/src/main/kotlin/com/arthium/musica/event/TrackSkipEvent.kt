package com.arthium.musica.event


/**
 * Thrown when skipping the playing audioTrack
 *
 * @param forward true if skipping forward, false otherwise
 */
class TrackSkipEvent(forward: Boolean = true)