package com.radioteria.service.storage

import com.fasterxml.jackson.annotation.JsonProperty

data class Metadata(
        @JsonProperty("contentType") val contentType: String,
        @JsonProperty("userMetadata") val userMetadata: Map<String, Any> = mapOf()
)
