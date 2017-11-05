package com.virtlink.gator

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Template input data.
 *
 * @property instances Gets the instances of the template.
 * @property repository The repository directory with the templates; or null to use the working directory.
 * @property template The path to the template; or null.
 */
class InputData @JsonCreator constructor(
        @JsonProperty("instances") val instances: List<InstanceData>,
        @JsonProperty("repository", required = false) val repository: String?,
        @JsonProperty("template", required = false) val template: String?
)