plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("kapt") version "1.6.21"
    id("org.springframework.boot") version "2.7.18"
    id("io.spring.dependency-management") version "1.1.7"
    id("java-library")
}

group = "com.github.solly29"
version = "0.0.6-SNAPSHOT"
description = "CommonSpring"


java {
    withSourcesJar()
    withJavadocJar()
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.jar {
    archiveClassifier.set("") // 'plain'과 같은 분류자 제거
    archiveFileName.set("${project.name}-${project.version}.jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.springframework.boot:spring-boot-starter")
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-aop")

    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    compileOnly("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.3.1")
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.fasterxml.jackson.core:jackson-databind")

//    api("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
