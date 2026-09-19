plugins {
    id("java")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(26)
    }
}

group = "br.com.dio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

}
