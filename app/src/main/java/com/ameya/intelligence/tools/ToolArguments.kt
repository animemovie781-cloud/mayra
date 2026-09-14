package com.ameya.intelligence.tools

internal fun firstToolArgument(map: Map<String, Any?>, vararg keys: String): Any? =
    keys.asSequence().mapNotNull(map::get).firstOrNull { it.toString().isNotBlank() }
