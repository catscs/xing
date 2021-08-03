package es.webandroid.xing.domain.use_cases

import es.webandroid.xing.core.error_handling.Failure
import es.webandroid.xing.core.functional.Either
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, scope: CoroutineScope = GlobalScope, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val job = scope.async(Dispatchers.IO) { run(params) }
        scope.launch(Dispatchers.Main) { onResult(job.await()) }
    }
}
