package com.virtlink.gator

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Template instance data.
 *
 * @property output The output filename or path.
 * @property data The data for the instance.
 */
class InstanceData @JsonCreator constructor(
        @JsonProperty("output") val output: String,
        @JsonProperty("data") val data: Map<String, Any>
)