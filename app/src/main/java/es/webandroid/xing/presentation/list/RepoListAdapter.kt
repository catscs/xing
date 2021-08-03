package es.webandroid.xing.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.webandroid.xing.R
import es.webandroid.xing.core.extensions.loadFromUrl
import es.webandroid.xing.databinding.RepoRowBinding
import es.webandroid.xing.domain.entities.Repo

class RepoListAdapter(private val clickListener: (String) -> Unit) : ListAdapter<Repo, RepoListAdapter.ViewHolder>(RepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RepoRowBinding = RepoRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        holder.bindTo(repo)
        holder.itemView.setOnLongClickListener {
            clickListener(repo.urlRepo)
            true
        }
    }

    class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
    }

    inner class ViewHolder(private val binding: RepoRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(hero: Repo) {
            hero.apply {
                with(binding) {
                    tvName.text = name
                    tvLogin.text = login
                    tvDescription.text = description
                    ivImage.loadFromUrl(avatar)
                    if (fork) cardView.setCardBackgroundColor(root.context.getColor(R.color.green_card))
                }
            }
        }
    }
}
