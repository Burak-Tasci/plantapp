package com.tsci.core.util

import android.content.Context
import androidx.annotation.StringRes

sealed class StringResource {
    data object Empty : StringResource()

    class Plain(val text: String): StringResource()

    class Resource(@StringRes val resourceId: Int) : StringResource()

    class ResourceWithArgs(@StringRes val resourceId: Int, vararg val args: Any): StringResource()

    class ResourceWithResourceArgs(@StringRes val resourceId: Int, @StringRes vararg val args: Int): StringResource()
}

fun StringResource.getText(context: Context): String {
    return when (this) {
        is StringResource.Plain -> this.text

        is StringResource.Resource -> {
            context.getString(resourceId)
        }

        is StringResource.ResourceWithArgs -> {
            context.getString(resourceId, args)
        }

        is StringResource.ResourceWithResourceArgs -> {
            val formats = args.map {
                context.getString(it)
            }.toTypedArray()

            context.getString(resourceId, *formats)
        }

        StringResource.Empty -> ""
    }
}