plugins {
    java
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "io.sookmyung"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains:annotations:20.1.0")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")

    // Google Speech To Text
    implementation(platform("com.google.cloud:libraries-bom:26.1.4"))
    implementation("com.google.cloud:google-cloud-speech")
    implementation("com.google.protobuf:protobuf-java:3.21.12")
    implementation("com.fasterxml.jackson.core:jackson-databind")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
