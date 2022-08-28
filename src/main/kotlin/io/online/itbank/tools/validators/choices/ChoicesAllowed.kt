package io.online.itbank.tools.validators.choices

import javax.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ChoicesAllowed(
        val message: String = "Field value should be from list of",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [],
        val choices: Array<String> = []
)