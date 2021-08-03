package es.webandroid.xing.core.error_handling

sealed class Failure {
    sealed class NetworkError : Failure() {
        data class Fatal(val reason: String?) : NetworkError()
        data class Recoverable(val reason: String?) : NetworkError()
        object NoConnection : NetworkError()
    }

    object DBError : Failure()
}
