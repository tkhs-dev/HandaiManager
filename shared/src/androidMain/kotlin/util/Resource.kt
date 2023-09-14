package util

actual object ResourceUtil {
    actual suspend fun readTextFile(fileName: String): String {
        val fileName = fileName.substring(0, fileName.lastIndexOf("."))
        val id = ContextUtil.mContext.resources.getIdentifier(fileName, "raw", ContextUtil.mContext.packageName)
        ContextUtil.mContext.resources.openRawResource(id).use {
            return it.bufferedReader().readText()
        }
    }
}