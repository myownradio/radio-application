package com.radioteria.domain.service.event

import com.radioteria.domain.entity.Channel
import org.springframework.context.ApplicationEvent

open class BaseChannelEvent(source: Any, val channel: Channel) : ApplicationEvent(source)