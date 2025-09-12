/*
 * MIT License
 *
 * Copyright (c) 2025 Dreamfire Studio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.dreamfirestudios.bluefusioncustommessages.Enum;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;

import java.util.Objects;

/**
 * Defines reusable message templates for the plugin.
 * <p>
 * Messages are parameterized and automatically formatted with the plugin's name.
 * </p>
 */
public enum BFCM_Messages {
    PlayerReloadedConfig("#7fff36[%s]: Configs have been reloaded!"),
    PlayerResetConfig("#7fff36[%s]: Configs have been reset!"),
    PlayerSerializedItem("#7fff36[%s]: Item has been added to serialise items!"),
    SystemNotEnabled("#7fff36[%s]: System Isn't Enabled!"),
    PlayerUpdatedCustomMessage("#7fff36[%s]: Updated custom message."),
    PlayerResetCustomMessage("#7fff36[%s]: Reset custom message."),
    GlobalUpdatedCustomMessage("#7fff36[%s]: Updated global custom message."),
    GlobalResetCustomMessage("#7fff36[%s]: Reset global custom message."),
    TargetPlayerNotFound("#7fff36[%s]: Target player not found!"),
    InvalidMessageKey("#7fff36[%s]: Invalid message key.");

    private final String template;

    BFCM_Messages(final String template) {
        this.template = Objects.requireNonNull(template, "template");
    }

    /**
     * Returns the message with the plugin class name injected.
     *
     * @return formatted message string
     */
    public String GetMessage() {
        return String.format(template, BlueFusionCustomMessages.GetClassName());
    }
}