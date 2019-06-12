package org.immunohelper.io.model

class Sample private constructor(val sampleName: String?) {

    class Builder {
        
        fun build(cellName: String) = Sample(cellName)

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sample

        if (sampleName != other.sampleName) return false

        return true
    }

    override fun hashCode(): Int {
        return sampleName?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Sample(sampleName=$sampleName)"
    }

}