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

        @Column(name = "position")
        var position: Long,

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
}