package es.webandroid.xing.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.webandroid.xing.core.error_handling.Failure
import es.webandroid.xing.core.platform.BaseViewModel
import es.webandroid.xing.core.platform.Consumable
import es.webandroid.xing.domain.entities.Repo
import es.webandroid.xing.domain.use_cases.RepoUseCase
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val useCase: RepoUseCase,
) : BaseViewModel() {

    private val _event = MutableLiveData<Consumable<Event>>()
    val event: LiveData<Consumable<Event>>
        get() = _event

    fun getRepositories(name: String?) {
        if (name.isNullOrEmpty()) return;

        triggerEvent(Event.Loading)
        useCase(RepoUseCase.Params(name), viewModelScope) { it.fold(::handlerFailure, ::handlerResult) }
    }

    override fun handlerFailure(failure: Failure) {
        super.handlerFailure(failure)
        triggerEvent(Event.Content(emptyList()))
    }

    private fun handlerResult(repositories: List<Repo>) {
        triggerEvent(Event.Content(repositories))
    }

    private fun triggerEvent(event: Event) {
        _event.value = Consumable(event)
    }

    fun onRepoClicked(urlRepo: String) {
        triggerEvent(Event.Detail(urlRepo))
    }

    sealed class Event {
        object Loading : Event()
        data class Content(val repositories: List<Repo>) : Event()
        data class Detail(val urlRepo: String): Event()
    }
}
