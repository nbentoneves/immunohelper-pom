package org.immunohelper.io.model

class Experience private constructor(val parentNode: Node?,
                                     val headersInfo: Map<Int, String>,
                                     val samplesInfo: Set<String?>) {

    data class Builder(var parentNode: Node? = null,
                       var headersInfo: Map<Int, String> = hashMapOf(),
                       var samplesInfo: Set<String?> = hashSetOf()) {

        fun withHeadersInfo(headersInfo: Map<Int, String>) = apply { this.headersInfo = headersInfo }

        fun withSamplesInfo(samplesInfo: Set<String?>) = apply { this.samplesInfo = samplesInfo }

        fun withTreeCell(nodeCell: Node) = apply { this.parentNode = nodeCell }

        fun build() = Experience(parentNode, headersInfo, samplesInfo)

    }


}