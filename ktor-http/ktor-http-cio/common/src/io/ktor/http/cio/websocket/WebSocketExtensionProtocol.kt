/*
 * Copyright 2014-2020 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.http.cio.websocket

public class WebSocketExtensionProtocol(
    public val name: String,
    public val parameters: List<String>
)
