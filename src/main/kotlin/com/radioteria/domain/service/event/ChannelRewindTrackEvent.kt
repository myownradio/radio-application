package com.radioteria.domain.service.event

import com.radioteria.domain.entity.Channel

class ChannelRewindTrackEvent(source: Any, channel: Channel) : BaseChannelEvent(source, channel)