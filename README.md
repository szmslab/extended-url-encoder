# Extended URL Encoder

[![Build Status](https://travis-ci.org/szmslab/extended-url-encoder.svg?branch=master)](https://travis-ci.org/szmslab/extended-url-encoder)

A URL encoding utility that extends the function of java.net.URLEncoder.

## Installation

#### Gradle configuration

```groovy
repositories {
  maven {
    url 'https://szmslab.github.io/maven-repository/'
  }
}

dependencies {
  compile 'com.szmslab.extendedurlencoder:extended-url-encoder:1.0.0'
}
```

## Examples

#### application/x-www-form-urlencoded

```java
String encoded = new ExtendedURLEncoder().encode("a b*"); // a+b*
```

#### RFC3986

```java
String encoded = new ExtendedURLEncoder(ExtendedURLEncoder.RFC3986).encode("a b*"); // a%20b%2A
```

## Requirements

JDK 8 or later.

## License

MIT, see [LICENSE](https://github.com/szmslab/extended-url-encoder/blob/master/LICENSE) for details.