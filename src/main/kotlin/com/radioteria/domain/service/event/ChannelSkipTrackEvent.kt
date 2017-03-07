package com.radioteria.domain.service.event

import com.radioteria.domain.entity.Channel

class ChannelSkipTrackEvent(source: Any, channel: Channel) : BaseChannelEvent(source, channel)