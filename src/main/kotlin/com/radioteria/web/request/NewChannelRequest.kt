package com.radioteria.web.request

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class NewChannelRequest(@NotNull @Size(min = 8, max = 64) var name: String = "")