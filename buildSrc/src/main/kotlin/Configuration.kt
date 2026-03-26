object Configuration {
    const val appName = "PairlyLove"
    const val applicationId = "app.luichigo15.pairly"
    const val versionMajor = 1
    const val versionMinor = 0
    const val versionPatch = 0
    const val versionClassifier = ""
    const val minSdk = 29
    const val compileSdk = 36
    const val targetSdk = 36
    const val versionCode = versionMajor+ versionMinor+ versionPatch

    fun buildVersionName(): String {
        val versionName = "$versionMajor.$versionMinor.$versionPatch"
        return if (versionClassifier.isNotEmpty()) versionName.plus("-$versionClassifier")
        else versionName
    }

    fun getBuildName():String{
        return "$appName-${buildVersionName().replace(".","-")}"
    }
}