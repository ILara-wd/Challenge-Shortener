package mx.com.nu.challenge_shortener.ui.listUrl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mx.com.nu.challenge_shortener.R
import mx.com.nu.challenge_shortener.databinding.ActivityListUrlBinding
import mx.com.nu.challenge_shortener.ktx.*
import mx.com.nu.challenge_shortener.ktx.observe
import mx.com.nu.challenge_shortener.data.LocalUrlRepository
import mx.com.nu.challenge_shortener.domain.model.UrlShortener
import mx.com.nu.challenge_shortener.ui.adapter.UrlShortAdapter

@AndroidEntryPoint
class ListUrlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListUrlBinding
    private lateinit var urlShortAdapter: UrlShortAdapter
    private val viewModel: ListUrlViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUrlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupObservers()
    }

    private fun setupView() {
        viewModel.onExecute()
        with(binding) {
            urlShortAdapter = UrlShortAdapter(
                listenerPosition = { urlShortener: UrlShortener, index: Int ->
                    urlShortAdapter.removeItem(urlShortener, index)
                },
                listener = { openBrowser(it) }
            )
            rvUrlShortener.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@ListUrlActivity)
                adapter = urlShortAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        this@ListUrlActivity,
                        DividerItemDecoration.VERTICAL,
                    )
                )
            }
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(this, ::handle)
    }

    private fun handle(state: ListUrlViewModel.State) {
        when (state) {
            ListUrlViewModel.State.Failure -> {
                Toast.makeText(this, getString(R.string.empty_url), Toast.LENGTH_SHORT).show()
            }
            ListUrlViewModel.State.Loading -> {
                showProgress()
            }
            is ListUrlViewModel.State.Success -> {
                urlShortAdapter.addUrlData(state.urlShortenerList)
                hideProgress()
            }
            else -> Unit
        }.exhaustive
    }


    private fun showProgress() {
        with(binding) {
            progressBar.visible()
        }
    }

    private fun hideProgress() {
        with(binding) {
            progressBar.gone()
        }
    }
}
