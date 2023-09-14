package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Licenses(
    @SerialName("libraries")
    val libraries: List<Library>,
    @SerialName("licenses")
    val licenses: Map<String,License>,
    @SerialName("metadata")
    val metadata: Metadata
) {
    @Serializable
    data class Library(
        @SerialName("artifactVersion")
        val artifactVersion: String,
        @SerialName("description")
        val description: String,
        @SerialName("developers")
        val developers: List<Developer>,
        @SerialName("licenses")
        val licenses: List<String>,
        @SerialName("name")
        val name: String,
        @SerialName("organization")
        val organization: Organization? = null,
        @SerialName("scm")
        val scm: Scm,
        @SerialName("uniqueId")
        val uniqueId: String,
        @SerialName("website")
        val website: String
    ) {
        @Serializable
        data class Developer(
            @SerialName("name")
            val name: String,
            @SerialName("organisationUrl")
            val organisationUrl: String? = null
        )

        @Serializable
        data class Organization(
            @SerialName("name")
            val name: String,
            @SerialName("url")
            val url: String
        )

        @Serializable
        data class Scm(
            @SerialName("connection")
            val connection: String? = null,
            @SerialName("developerConnection")
            val developerConnection: String? = null,
            @SerialName("url")
            val url: String
        )
    }

    @Serializable
    data class License(
        @SerialName("content")
        val content: String? = null,
        @SerialName("hash")
        val hash: String,
        @SerialName("internalHash")
        val internalHash: String? = null,
        @SerialName("name")
        val name: String,
        @SerialName("spdxId")
        val spdxId: String? = null,
        @SerialName("url")
        val url: String
    )

    @Serializable
    data class Metadata(
        @SerialName("generated")
        val generated: String
    )
}