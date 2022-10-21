package mx.com.nu.challenge_shortener.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.com.nu.challenge_shortener.databinding.ViewUrlItemBinding
import mx.com.nu.challenge_shortener.domain.model.UrlShortener

internal class UrlShortAdapter(
    private val listenerPosition: (UrlShortener, Int) -> Unit,
    private val listener: (String) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataUrls: MutableList<UrlShortener> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        UrlViewHolder(
            ViewUrlItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as UrlViewHolder).bind(dataUrls[position], position)

    override fun getItemCount(): Int = dataUrls.size

    @SuppressLint("NotifyDataSetChanged")
    fun addUrlData(data: List<UrlShortener>) {
        this.dataUrls.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(urlShortener: UrlShortener, index: Int) {
        this.dataUrls.remove(urlShortener)
        notifyItemRemoved(index)
    }

    inner class UrlViewHolder(private val binding: ViewUrlItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UrlShortener, index: Int) = with(binding) {
            titleTextView.text = data.urlShortener
            ivRemove.setOnClickListener { listenerPosition(data, index) }
            descriptionTextView.text = data.urlOriginal
            container.setOnClickListener { listener(data.urlShortener) }
        }
    }

}
