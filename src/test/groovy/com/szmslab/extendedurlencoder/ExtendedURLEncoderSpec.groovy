package com.szmslab.extendedurlencoder

import com.szmslab.extendedurlencoder.exception.UnsupportedEncodingRuntimeException
import spock.lang.Specification
import spock.lang.Unroll

class ExtendedURLEncoderSpec extends Specification {

    @Unroll
    def "Character encoding 'utf-8': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder()

        expect:
        encoder.encode(param) == expected

        where:
        param || expected
        "0"   || "0"
        "9"   || "9"
        "a"   || "a"
        "z"   || "z"
        "A"   || "A"
        "Z"   || "Z"
        " "   || "+"
        "!"   || "%21"
        "\""  || "%22"
        "#"   || "%23"
        "\$"  || "%24"
        "%"   || "%25"
        "&"   || "%26"
        "'"   || "%27"
        "("   || "%28"
        ")"   || "%29"
        "*"   || "*"
        "+"   || "%2B"
        ","   || "%2C"
        "-"   || "-"
        "."   || "."
        "/"   || "%2F"
        ":"   || "%3A"
        ";"   || "%3B"
        "<"   || "%3C"
        "="   || "%3D"
        ">"   || "%3E"
        "?"   || "%3F"
        "@"   || "%40"
        "["   || "%5B"
        "\\"  || "%5C"
        "]"   || "%5D"
        "^"   || "%5E"
        "_"   || "_"
        "`"   || "%60"
        "{"   || "%7B"
        "|"   || "%7C"
        "}"   || "%7D"
        "~"   || "%7E"
        "あ"   || "%E3%81%82"
        "ｱ"   || "%EF%BD%B1"
        "㈱"   || "%E3%88%B1"
    }

    @Unroll
    def "Character encoding 'ms932': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder("ms932")

        expect:
        encoder.encode(param) == expected

        where:
        param || expected
        "0"   || "0"
        "9"   || "9"
        "a"   || "a"
        "z"   || "z"
        "A"   || "A"
        "Z"   || "Z"
        " "   || "+"
        "!"   || "%21"
        "\""  || "%22"
        "#"   || "%23"
        "\$"  || "%24"
        "%"   || "%25"
        "&"   || "%26"
        "'"   || "%27"
        "("   || "%28"
        ")"   || "%29"
        "*"   || "*"
        "+"   || "%2B"
        ","   || "%2C"
        "-"   || "-"
        "."   || "."
        "/"   || "%2F"
        ":"   || "%3A"
        ";"   || "%3B"
        "<"   || "%3C"
        "="   || "%3D"
        ">"   || "%3E"
        "?"   || "%3F"
        "@"   || "%40"
        "["   || "%5B"
        "\\"  || "%5C"
        "]"   || "%5D"
        "^"   || "%5E"
        "_"   || "_"
        "`"   || "%60"
        "{"   || "%7B"
        "|"   || "%7C"
        "}"   || "%7D"
        "~"   || "%7E"
        "あ"   || "%82%A0"
        "ｱ"   || "%B1"
        "㈱"   || "%87%8A"
    }

    def "Illegal character encoding 'xxx'"() {
        setup:
        def encoder = new ExtendedURLEncoder("xxx")

        when:
        encoder.encode("0")

        then:
        thrown(UnsupportedEncodingRuntimeException)
    }

    @Unroll
    def "All ReplacementDefinitions: '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(
                ExtendedURLEncoder.SPACE,
                ExtendedURLEncoder.EXCLAMATION_MARK,
                ExtendedURLEncoder.HASH_SIGN,
                ExtendedURLEncoder.DOLLAR_SIGN,
                ExtendedURLEncoder.AMPERSAND,
                ExtendedURLEncoder.SINGLE_QUOTE,
                ExtendedURLEncoder.OPEN_PARENTHESIS,
                ExtendedURLEncoder.CLOSE_PARENTHESIS,
                ExtendedURLEncoder.ASTERISK,
                ExtendedURLEncoder.PLUS,
                ExtendedURLEncoder.COMMA,
                ExtendedURLEncoder.FORWARD_SLASH,
                ExtendedURLEncoder.COLON,
                ExtendedURLEncoder.SEMICOLON,
                ExtendedURLEncoder.EQUAL,
                ExtendedURLEncoder.QUESTION_MARK,
                ExtendedURLEncoder.AT_SIGN,
                ExtendedURLEncoder.OPEN_BRACKET,
                ExtendedURLEncoder.CLOSE_BRACKET,
                ExtendedURLEncoder.TILDE
        )

        expect:
        encoder.encode(param) == expected

        where:
        param || expected
        "0"   || "0"
        "9"   || "9"
        "a"   || "a"
        "z"   || "z"
        "A"   || "A"
        "Z"   || "Z"
        " "   || "%20"
        "!"   || "!"
        "\""  || "%22"
        "#"   || "#"
        "\$"  || "\$"
        "%"   || "%25"
        "&"   || "&"
        "'"   || "'"
        "("   || "("
        ")"   || ")"
        "*"   || "%2A"
        "+"   || "+"
        ","   || ","
        "-"   || "-"
        "."   || "."
        "/"   || "/"
        ":"   || ":"
        ";"   || ";"
        "<"   || "%3C"
        "="   || "="
        ">"   || "%3E"
        "?"   || "?"
        "@"   || "@"
        "["   || "["
        "\\"  || "%5C"
        "]"   || "]"
        "^"   || "%5E"
        "_"   || "_"
        "`"   || "%60"
        "{"   || "%7B"
        "|"   || "%7C"
        "}"   || "%7D"
        "~"   || "~"
        "あ"   || "%E3%81%82"
        "ｱ"   || "%EF%BD%B1"
        "㈱"   || "%E3%88%B1"
    }

    def "ReplacementDefinitions args[0]='PLUS' and args[1]='SPACE': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(
                ExtendedURLEncoder.PLUS,
                ExtendedURLEncoder.SPACE
        )

        expect:
        encoder.encode(param) == expected

        where:
        param || expected
        " +"  || "%20+"
    }

    def "ReplacementDefinitions 'JS_ENCODE_URI': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(ExtendedURLEncoder.JS_ENCODE_URI)

        expect:
        encoder.encode(param) == expected

        where:
        param                                           || expected
        "09azAZ !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~あｱ㈱" || "09azAZ%20!%22#\$%25&'()*+,-./:;%3C=%3E?@%5B%5C%5D%5E_%60%7B%7C%7D~%E3%81%82%EF%BD%B1%E3%88%B1"
    }

    def "ReplacementDefinitions 'JS_ENCODE_URI_COMPONENT': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(ExtendedURLEncoder.JS_ENCODE_URI_COMPONENT)

        expect:
        encoder.encode(param) == expected

        where:
        param                                           || expected
        "09azAZ !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~あｱ㈱" || "09azAZ%20!%22%23%24%25%26'()*%2B%2C-.%2F%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E_%60%7B%7C%7D~%E3%81%82%EF%BD%B1%E3%88%B1"
    }

    def "ReplacementDefinitions 'DOTNET_HTTP_UTILITY_URL_ENCODE': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(ExtendedURLEncoder.DOTNET_HTTP_UTILITY_URL_ENCODE)

        expect:
        encoder.encode(param) == expected

        where:
        param                                           || expected
        "09azAZ !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~あｱ㈱" || "09azAZ+!%22%23%24%25%26'()*%2B%2C-.%2F%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E_%60%7B%7C%7D%7E%E3%81%82%EF%BD%B1%E3%88%B1"
    }

    def "ReplacementDefinitions 'DOTNET_HTTP_UTILITY_URL_ENCODE_FW40': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(ExtendedURLEncoder.DOTNET_HTTP_UTILITY_URL_ENCODE_FW40)

        expect:
        encoder.encode(param) == expected

        where:
        param                                           || expected
        "09azAZ !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~あｱ㈱" || "09azAZ+!%22%23%24%25%26%27()*%2B%2C-.%2F%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E_%60%7B%7C%7D%7E%E3%81%82%EF%BD%B1%E3%88%B1"
    }

    def "ReplacementDefinitions 'DOTNET_URI_ESCAPE_URI_STRING': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(ExtendedURLEncoder.DOTNET_URI_ESCAPE_URI_STRING)

        expect:
        encoder.encode(param) == expected

        where:
        param                                           || expected
        "09azAZ !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~あｱ㈱" || "09azAZ%20!%22#\$%25&'()*+,-./:;%3C=%3E?@%5B%5C%5D%5E_%60%7B%7C%7D~%E3%81%82%EF%BD%B1%E3%88%B1"
    }

    def "ReplacementDefinitions 'DOTNET_URI_ESCAPE_URI_STRING_FW45': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(ExtendedURLEncoder.DOTNET_URI_ESCAPE_URI_STRING_FW45)

        expect:
        encoder.encode(param) == expected

        where:
        param                                           || expected
        "09azAZ !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~あｱ㈱" || "09azAZ%20!%22#\$%25&'()*+,-./:;%3C=%3E?@[%5C]%5E_%60%7B%7C%7D~%E3%81%82%EF%BD%B1%E3%88%B1"
    }

    def "ReplacementDefinitions 'DOTNET_URI_ESCAPE_DATA_STRING': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(ExtendedURLEncoder.DOTNET_URI_ESCAPE_DATA_STRING)

        expect:
        encoder.encode(param) == expected

        where:
        param                                           || expected
        "09azAZ !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~あｱ㈱" || "09azAZ%20!%22%23%24%25%26'()*%2B%2C-.%2F%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E_%60%7B%7C%7D~%E3%81%82%EF%BD%B1%E3%88%B1"
    }

    def "ReplacementDefinitions 'DOTNET_URI_ESCAPE_DATA_STRING_FW45': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(ExtendedURLEncoder.DOTNET_URI_ESCAPE_DATA_STRING_FW45)

        expect:
        encoder.encode(param) == expected

        where:
        param                                           || expected
        "09azAZ !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~あｱ㈱" || "09azAZ%20%21%22%23%24%25%26%27%28%29%2A%2B%2C-.%2F%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E_%60%7B%7C%7D~%E3%81%82%EF%BD%B1%E3%88%B1"
    }

    def "ReplacementDefinitions 'DOTNET_WEB_UTILITY_URL_ENCODE': '#param' -> '#expected'"() {
        setup:
        def encoder = new ExtendedURLEncoder(ExtendedURLEncoder.DOTNET_WEB_UTILITY_URL_ENCODE)

        expect:
        encoder.encode(param) == expected

        where:
        param                                           || expected
        "09azAZ !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~あｱ㈱" || "09azAZ+!%22%23%24%25%26%27()*%2B%2C-.%2F%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E_%60%7B%7C%7D%7E%E3%81%82%EF%BD%B1%E3%88%B1"
    }

}