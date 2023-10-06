package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("avatar")
    val avatar: Avatar,
    @SerialName("contact")
    val contact: Contact,
    @SerialName("created")
    val created: String,
    @SerialName("id")
    val id: String,
    @SerialName("institutionRoleIds")
    val institutionRoleIds: List<String>,
    @SerialName("job")
    val job: Job,
    @SerialName("modified")
    val modified: String,
    @SerialName("name")
    val name: Name,
    @SerialName("studentId")
    val studentId: String,
    @SerialName("systemRoleIds")
    val systemRoleIds: List<String>,
    @SerialName("userName")
    val userName: String,
    @SerialName("uuid")
    val uuid: String
) {
    @Serializable
    data class Avatar(
        @SerialName("source")
        val source: String,
        @SerialName("viewUrl")
        val viewUrl: String
    )

    @Serializable
    data class Contact(
        @SerialName("email")
        val email: String
    )

    @Serializable
    data class Job(
        @SerialName("company")
        val company: String,
        @SerialName("department")
        val department: String
    )

    @Serializable
    data class Name(
        @SerialName("family")
        val family: String,
        @SerialName("given")
        val given: String,
        @SerialName("preferredDisplayName")
        val preferredDisplayName: String
    )
}