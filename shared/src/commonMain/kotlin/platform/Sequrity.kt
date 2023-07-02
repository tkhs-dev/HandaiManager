package platform

/**
 * Get the AES key from the platform
 * @return the AES key to encrypt the data with.The class is dependent on the platform.java.security.Key is common on Android and JVM.
 */
expect fun getAesKey(): Any