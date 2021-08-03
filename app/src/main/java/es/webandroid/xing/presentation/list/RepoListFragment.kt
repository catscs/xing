package es.webandroid.xing.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import es.webandroid.xing.core.extensions.hideKeyboard
import es.webandroid.xing.core.platform.BaseFragment
import es.webandroid.xing.core.platform.ConsumingObserver
import es.webandroid.xing.presentation.list.RepoListViewModel.Event
import android.content.Intent

import android.net.Uri
import es.webandroid.xing.R
import es.webandroid.xing.core.extensions.showModalInfo
import es.webandroid.xing.databinding.FragmentRepoListBinding


@AndroidEntryPoint
class RepoListFragment : BaseFragment() {

    private val viewModel by viewModels<RepoListViewModel>()

    private lateinit var adapter: RepoListAdapter

    private var _binding: FragmentRepoListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getRepositories(getString(R.string.search_default))
        }

        configureView()
        viewModel.event.observe(viewLifecycleOwner, ConsumingObserver(::updateUi))
        viewModel.failure.observe(viewLifecycleOwner, Observer(::handleFailure))
    }

    private fun configureView() {
        binding.searchView.apply {
            isSubmitButtonEnabled = true
            isQueryRefinementEnabled = true
            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.getRepositories(query)
                    hideKeyboard(requireActivity())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        adapter = RepoListAdapter(viewModel::onRepoClicked)
        binding.recycler.adapter = adapter
    }

    private fun updateUi(event: Event) {
        binding.progress.visibility = if (event is Event.Loading) View.VISIBLE else View.GONE
        when (event) {
            is Event.Content -> adapter.submitList(event.repositories)
            is Event.Detail -> showModal(event.urlRepo)
        }
    }

    private fun showModal(urlRepo: String) {
       showModalInfo(R.string.title_modal, R.string.button_go_modal) {
           val uri = Uri.parse(urlRepo)
           val intent = Intent(Intent.ACTION_VIEW, uri)
           startActivity(intent)
       }
    }

}
