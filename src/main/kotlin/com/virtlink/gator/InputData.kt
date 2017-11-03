package com.virtlink.gator

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Template input data.
 *
 * @property instances Gets the instances of the template.
 * @property template The path to the template.
 * @property templateGroup The file or directory with the templates.
 * @property autoIndent Whether to auto-indent.
 */
class InputData @JsonCreator constructor(
            @JsonProperty("instances") val instances: List<InstanceData>,
            @JsonProperty("template-group", required = false) val templateGroup: String?,
            @JsonProperty("template", required = false) val template: String?,
            @JsonProperty("auto-indent", required = false) val autoIndent: Boolean?
)