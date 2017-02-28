package com.radioteria.domain.entity

import com.radioteria.auth.BelongsToUser
import javax.persistence.*

@Entity
@Table(name = "tracks")
data class Track(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Long? = null,

        @Column(name = "offset")
        var offset: Long = 0,

        @Column(name = "title")
        var title: String = "",

        @Column(name = "artist")
        var artist: String = "",

        @Column(name = "duration")
        var duration: Long,

        @JoinColumn(name = "audio_file_id")
        @ManyToOne(targetEntity = File::class)
        val audioFile: File,

        @JoinColumn(name = "channel_id")
        @ManyToOne(targetEntity = Channel::class)
        var channel: Channel
) : BelongsToUser {

    val ending: Long get() = offset + duration

    override fun belongsTo(user: User): Boolean {
        return user.id == this.channel.user.id
    }

    fun belongsTo(channel: Channel): Boolean {
        return channel.id == this.channel.id
    }

    fun isPlayingAt(lapTime: Long): Boolean {
        return offset <= lapTime && ending > lapTime
    }

}