package mx.com.nu.challenge_shortener.ktx

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}

fun AppCompatActivity.openBrowser(_url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(_url))
    startActivity(browserIntent)
}