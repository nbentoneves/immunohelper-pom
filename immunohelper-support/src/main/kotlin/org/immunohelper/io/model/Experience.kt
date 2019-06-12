package org.immunohelper.io.model

class Experience private constructor(val parentNode: Node?,
                                     val headersInfo: Map<Int, String>,
                                     val samplesInfo: Set<String>,
                                     val valid: Boolean) {

    class Builder {

        private var parentNode: Node? = null
        private var headersInfo: Map<Int, String> = hashMapOf()
        private var samplesInfo: Set<String> = hashSetOf()
        private var valid: Boolean = false

        fun withHeadersInfo(headersInfo: Map<Int, String>) = apply { this.headersInfo = headersInfo }

        fun withSamplesInfo(samplesInfo: Set<String>) = apply { this.samplesInfo = samplesInfo }

        fun withTreeCell(nodeCell: Node) = apply { this.parentNode = nodeCell }

        fun withIsValid(isValue: Boolean) = apply { this.valid = isValue }

        fun build() = Experience(parentNode, headersInfo, samplesInfo, valid)

    }

    override fun toString(): String {
        return "Experience(parentNode=$parentNode, headersInfo=$headersInfo, samplesInfo=$samplesInfo, valid=$valid)"
    }

}