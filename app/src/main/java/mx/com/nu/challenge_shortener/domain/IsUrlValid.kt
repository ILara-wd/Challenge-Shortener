package mx.com.nu.challenge_shortener.domain

import dagger.Reusable
import javax.inject.Inject
import android.util.Patterns
import android.webkit.URLUtil
import java.net.MalformedURLException

@Reusable
class IsUrlValid @Inject constructor() {

    operator fun invoke(urlString: String): Boolean = try {
        URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString).matches()
    } catch (ignored: MalformedURLException) {
        false
    }
}
