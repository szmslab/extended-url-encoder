/*
 * Copyright (c) 2017 szmslab
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package com.szmslab.extendedurlencoder;

import lombok.Value;

/**
 * A class that stores the replacement definitions used by {@link com.szmslab.extendedurlencoder.ExtendedURLEncoder}.
 *
 * @author szmslab
 * @see com.szmslab.extendedurlencoder.ExtendedURLEncoder
 * @since 1.0.0
 */
@Value
public class ReplacementDefinition {

    private final CharSequence target;
    private final CharSequence replacement;
    private final int order;

    public ReplacementDefinition(CharSequence target, CharSequence replacement) {
        this(target, replacement, 10);
    }

    public ReplacementDefinition(CharSequence target, CharSequence replacement, int order) {
        this.target = target;
        this.replacement = replacement;
        this.order = order;
    }

}
