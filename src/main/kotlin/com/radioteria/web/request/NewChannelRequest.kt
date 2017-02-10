package com.radioteria.web.request

import javax.validation.constraints.Size

data class NewChannelRequest(
        @field:Size(min = 1, max = 64)
        val name: String = ""
)