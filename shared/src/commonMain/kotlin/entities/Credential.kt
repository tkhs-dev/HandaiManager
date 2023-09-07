package entities

import kotlinx.serialization.Serializable

@Serializable
data class Credential(
    val userId: String,
    val password: String,
    val otpSecret: String? = null)