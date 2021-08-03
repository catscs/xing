package es.webandroid.xing.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.webandroid.xing.core.error_handling.Failure

abstract class BaseViewModel : ViewModel() {

    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    open fun handlerFailure(failure: Failure) {
        _failure.value = failure
    }
}
