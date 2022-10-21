package mx.com.nu.challenge_shortener.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import mx.com.nu.challenge_shortener.R
import mx.com.nu.challenge_shortener.databinding.ActivityMainBinding
import mx.com.nu.challenge_shortener.ktx.*
import mx.com.nu.challenge_shortener.ktx.observe
import mx.com.nu.challenge_shortener.data.LocalUrlRepository
import mx.com.nu.challenge_shortener.ui.adapter.UrlShortAdapter
import mx.com.nu.challenge_shortener.ui.listUrl.ListUrlActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var urlShortAdapter: UrlShortAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupObservers()
    }

    private fun setupView() {
        //urlShortAdapter = UrlShortAdapter { openBrowser(it) }
        with(binding) {
            btnList.setOnClickListener {
                startActivity(Intent(this@MainActivity, ListUrlActivity::class.java))
            }
            btnShorted.setOnClickListener {
                if (urlTextInputEditText.text.toString().isNotEmpty()) {
                    viewModel.onExecute(urlTextInputEditText.text.toString().trim())
                }
            }
            urlTextInputEditText.addTextChangedListener {
                viewModel.validateUrl(it.toString().trim())
            }
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(this, ::handle)
    }

    private fun handle(state: MainViewModel.State) {
        when (state) {
            MainViewModel.State.Failure -> {
                Toast.makeText(this, getString(R.string.error_url), Toast.LENGTH_SHORT).show()
            }
            MainViewModel.State.Loading -> {
                showProgress()
            }
            is MainViewModel.State.ValidUrl -> {
                setupEnableButton(state.isValidUrl)
                binding.urlTextInputLayout.error =
                    if (!state.isValidUrl) {
                        getString(R.string.text_hint_url_incorrect)
                    } else {
                        null
                    }
            }
            is MainViewModel.State.Success -> {
                with(binding) {
                    LocalUrlRepository.addUrl(state.urlShortener)
                    tvTitleItem.text = state.urlShortener.urlShortener
                    tvDescriptionItem.text = state.urlShortener.urlOriginal
                }
                hideProgress()
            }
            else -> Unit
        }.exhaustive
    }

    private fun setupEnableButton(isEnabled: Boolean) {
        binding.btnShorted.apply {
            this.isEnabled = isEnabled
            this.setBackgroundResource(if (isEnabled) R.drawable.white_rounded else R.drawable.grey_rounded)
        }
    }

    private fun showProgress() {
        with(binding) {
            progressBar.visible()
        }
    }

    private fun hideProgress() {
        with(binding) {
            progressBar.gone()
            urlTextInputEditText.setText("")
            urlTextInputLayout.error = null
        }
    }
}
