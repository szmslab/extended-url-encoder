/*
 * Copyright (c) 2017 szmslab
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package com.szmslab.extendedurlencoder;

import com.szmslab.extendedurlencoder.exception.UnsupportedEncodingRuntimeException;
import lombok.Getter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A utility class for URL encoding.
 * <p>
 * Using the conversion result by {@code java.net.URLEncoder},
 * it converts a String to other format as well as the <CODE>application/x-www-form-urlencoded</CODE> MIME format.
 * </p>
 *
 * @author szmslab
 * @since 1.0.0
 */
public class ExtendedURLEncoder {

    public static final ReplacementDefinition SPACE =
            new ReplacementDefinition("+", "%20", 0);
    public static final ReplacementDefinition EXCLAMATION_MARK =
            new ReplacementDefinition("%21", "!");
    public static final ReplacementDefinition HASH_SIGN =
            new ReplacementDefinition("%23", "#");
    public static final ReplacementDefinition DOLLAR_SIGN =
            new ReplacementDefinition("%24", "$");
    public static final ReplacementDefinition AMPERSAND =
            new ReplacementDefinition("%26", "&");
    public static final ReplacementDefinition SINGLE_QUOTE =
            new ReplacementDefinition("%27", "'");
    public static final ReplacementDefinition OPEN_PARENTHESIS =
            new ReplacementDefinition("%28", "(");
    public static final ReplacementDefinition CLOSE_PARENTHESIS =
            new ReplacementDefinition("%29", ")");
    public static final ReplacementDefinition ASTERISK =
            new ReplacementDefinition("*", "%2A");
    public static final ReplacementDefinition PLUS =
            new ReplacementDefinition("%2B", "+");
    public static final ReplacementDefinition COMMA =
            new ReplacementDefinition("%2C", ",");
    public static final ReplacementDefinition FORWARD_SLASH =
            new ReplacementDefinition("%2F", "/");
    public static final ReplacementDefinition COLON =
            new ReplacementDefinition("%3A", ":");
    public static final ReplacementDefinition SEMICOLON =
            new ReplacementDefinition("%3B", ";");
    public static final ReplacementDefinition EQUAL =
            new ReplacementDefinition("%3D", "=");
    public static final ReplacementDefinition QUESTION_MARK =
            new ReplacementDefinition("%3F", "?");
    public static final ReplacementDefinition AT_SIGN =
            new ReplacementDefinition("%40", "@");
    public static final ReplacementDefinition OPEN_BRACKET =
            new ReplacementDefinition("%5B", "[");
    public static final ReplacementDefinition CLOSE_BRACKET =
            new ReplacementDefinition("%5D", "]");
    public static final ReplacementDefinition TILDE =
            new ReplacementDefinition("%7E", "~");

    public static final ReplacementDefinition[] RFC2396 = new ReplacementDefinition[]{
            SPACE,
            EXCLAMATION_MARK,
            SINGLE_QUOTE,
            OPEN_PARENTHESIS,
            CLOSE_PARENTHESIS,
            TILDE
    };

    public static final ReplacementDefinition[] RFC3986 = new ReplacementDefinition[]{
            SPACE,
            ASTERISK,
            TILDE
    };

    public static final ReplacementDefinition[] JS_ENCODE_URI = new ReplacementDefinition[]{
            SPACE,
            EXCLAMATION_MARK,
            HASH_SIGN,
            DOLLAR_SIGN,
            AMPERSAND,
            SINGLE_QUOTE,
            OPEN_PARENTHESIS,
            CLOSE_PARENTHESIS,
            PLUS,
            COMMA,
            FORWARD_SLASH,
            COLON,
            SEMICOLON,
            EQUAL,
            QUESTION_MARK,
            AT_SIGN,
            TILDE
    };

    public static final ReplacementDefinition[] JS_ENCODE_URI_COMPONENT = RFC2396;

    public static final ReplacementDefinition[] DOTNET_HTTP_UTILITY_URL_ENCODE = new ReplacementDefinition[]{
            EXCLAMATION_MARK,
            SINGLE_QUOTE,
            OPEN_PARENTHESIS,
            CLOSE_PARENTHESIS
    };

    public static final ReplacementDefinition[] DOTNET_HTTP_UTILITY_URL_ENCODE_FW40 = new ReplacementDefinition[]{
            EXCLAMATION_MARK,
            OPEN_PARENTHESIS,
            CLOSE_PARENTHESIS
    };

    public static final ReplacementDefinition[] DOTNET_URI_ESCAPE_URI_STRING = new ReplacementDefinition[]{
            SPACE,
            EXCLAMATION_MARK,
            HASH_SIGN,
            DOLLAR_SIGN,
            AMPERSAND,
            SINGLE_QUOTE,
            OPEN_PARENTHESIS,
            CLOSE_PARENTHESIS,
            PLUS,
            COMMA,
            FORWARD_SLASH,
            COLON,
            SEMICOLON,
            EQUAL,
            QUESTION_MARK,
            AT_SIGN,
            TILDE
    };

    public static final ReplacementDefinition[] DOTNET_URI_ESCAPE_URI_STRING_FW45 = new ReplacementDefinition[]{
            SPACE,
            EXCLAMATION_MARK,
            HASH_SIGN,
            DOLLAR_SIGN,
            AMPERSAND,
            SINGLE_QUOTE,
            OPEN_PARENTHESIS,
            CLOSE_PARENTHESIS,
            PLUS,
            COMMA,
            FORWARD_SLASH,
            COLON,
            SEMICOLON,
            EQUAL,
            QUESTION_MARK,
            AT_SIGN,
            OPEN_BRACKET,
            CLOSE_BRACKET,
            TILDE
    };

    public static final ReplacementDefinition[] DOTNET_URI_ESCAPE_DATA_STRING = RFC2396;

    public static final ReplacementDefinition[] DOTNET_URI_ESCAPE_DATA_STRING_FW45 = RFC3986;

    public static final ReplacementDefinition[] DOTNET_WEB_UTILITY_URL_ENCODE = new ReplacementDefinition[]{
            EXCLAMATION_MARK,
            OPEN_PARENTHESIS,
            CLOSE_PARENTHESIS
    };

    @Getter
    private String characterEncoding;

    @Getter
    private List<ReplacementDefinition> replacementDefinitions = new ArrayList<>();

    public ExtendedURLEncoder(ReplacementDefinition... replacementDefinitions) {
        this(StandardCharsets.UTF_8.name(), replacementDefinitions);
    }

    public ExtendedURLEncoder(String characterEncoding, ReplacementDefinition... replacementDefinitions) {
        this.characterEncoding = characterEncoding;
        this.replacementDefinitions.addAll(Arrays.asList(replacementDefinitions));
        this.replacementDefinitions.sort(Comparator.comparingInt(ReplacementDefinition::getOrder));
    }

    public String encode(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        try {
            AtomicReference<String> encoded = new AtomicReference<>(URLEncoder.encode(value, characterEncoding));
            replacementDefinitions.forEach(
                    def -> encoded.set(encoded.get().replace(def.getTarget(), def.getReplacement())));
            return encoded.get();
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

}
