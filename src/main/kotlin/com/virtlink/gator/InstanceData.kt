package com.virtlink.gator

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Template instance data.
 *
 * @property output The output filename or path.
 * @property data The data for the instance.
 * @property template The path to the template; or null to use the globally defined template.
 * @property templateGroup The file or directory with the templates.
 * @property autoIndent Whether to auto-indent.
 */
class InstanceData @JsonCreator constructor(
        @JsonProperty("output") val output: String,
        @JsonProperty("data") val data: Map<String, Any>,
        @JsonProperty("template", required = false) val template: String?,
        @JsonProperty("template-group", required = false) val templateGroup: String?,
        @JsonProperty("auto-indent", required = false) val autoIndent: Boolean?
)