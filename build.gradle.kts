import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object DependencyVersion {
    const val KOTLIN_LOGGING = "2.1.20"
    const val SPRINGDOC_OPENAPI = "1.6.11"
}
plugins {
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

group = "com"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springdoc:springdoc-openapi-ui:${DependencyVersion.SPRINGDOC_OPENAPI}")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.github.microutils:kotlin-logging-jvm:${DependencyVersion.KOTLIN_LOGGING}")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

springBoot {
    // BuildProperties 사용하려면 필요
    // See https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#integrating-with-actuator.build-info
    buildInfo()
}