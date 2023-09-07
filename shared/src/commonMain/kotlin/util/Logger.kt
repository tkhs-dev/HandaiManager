package util

expect object Logger {
    fun error(tag:String?, message: Any?)
    fun debug(tag:String?, message: Any?)
    fun info(tag:String?, message: Any?)
    fun warn(tag:String?, message: Any?)
    fun verbose(tag:String?, message: Any?)
}