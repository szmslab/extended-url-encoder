/*
 * Copyright (c) 2017 szmslab
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package com.szmslab.extendedurlencoder.exception;

import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;

/**
 * A class that wraps a {@code java.io.UnsupportedEncodingException} with an unchecked exception.
 *
 * @author szmslab
 * @since 1.0.0
 */
public class UnsupportedEncodingRuntimeException extends UncheckedIOException {

    public UnsupportedEncodingRuntimeException(String message, UnsupportedEncodingException cause) {
        super(message, cause);
    }

    public UnsupportedEncodingRuntimeException(UnsupportedEncodingException cause) {
        super(cause);
    }

}
