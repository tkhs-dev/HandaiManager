package util

import org.slf4j.LoggerFactory

actual object Logger {
    private val logger = LoggerFactory.getLogger("HandaiManager")
    actual fun error(tag: String?, message: Any?) {
        logger.error("<{}>: {}", tag, message)
    }
    actual fun debug(tag: String?, message: Any?) {
        logger.debug("<{}>: {}", tag, message)
    }
    actual fun info(tag: String?, message: Any?) {
        logger.info("<{}>: {}", tag, message)
    }
    actual fun warn(tag: String?, message: Any?) {
        logger.warn("<{}>: {}", tag, message)
    }
    actual fun verbose(tag: String?, message: Any?) {
        logger.trace("<{}>: {}", tag, message)
    }
}