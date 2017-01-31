package org.jetbrains.ktor.features

import org.jetbrains.ktor.application.*
import org.jetbrains.ktor.content.*
import org.jetbrains.ktor.http.*
import org.jetbrains.ktor.transform.*
import org.jetbrains.ktor.util.*

object HeadRequestSupport : ApplicationFeature<ApplicationCallPipeline, Unit, Unit> {
    override val key = AttributeKey<Unit>("Automatic Head Response")

    override fun install(pipeline: ApplicationCallPipeline, configure: Unit.() -> Unit) {
        Unit.configure()

        pipeline.intercept(ApplicationCallPipeline.Infrastructure) {
            if (call.request.local.method == HttpMethod.Head) {
                call.transform.register<FinalContent>(
                        predicate = { message -> message !is FinalContent.NoContent },
                        handler = { message -> HeadResponse(message) })
            }
        }
    }

    private class HeadResponse(val delegate: FinalContent) : FinalContent.NoContent() {
        override val headers by lazy { delegate.headers }
        override val status: HttpStatusCode? get() = delegate.status
    }
}
