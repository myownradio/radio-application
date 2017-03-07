package com.radioteria.domain.service.event

import com.radioteria.domain.entity.Channel

class ChannelStartedEvent(source: Any, channel: Channel) : BaseChannelEvent(source, channel)