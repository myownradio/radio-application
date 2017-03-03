package com.radioteria.domain.entity

import com.radioteria.auth.BelongsToUser
import org.hibernate.annotations.Formula
import javax.persistence.*

@Entity
@Table(name = "tracks")
data class Track(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Long? = null,

        @Column(name = "time_offset")
        var offset: Long = 0,

        @Formula("time_offset + duration")
        var ending: Long = 0,

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

    override fun belongsTo(user: User): Boolean {
        return user.id == this.channel.user.id
    }

    fun belongsTo(channel: Channel): Boolean {
        return channel.id == this.channel.id
    }

    fun playingAt(lapTime: Long): Boolean {
        return lapTime in offset until ending
    }

}
