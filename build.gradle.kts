plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id("org.springframework.boot") version "2.7.18"
    id("io.spring.dependency-management") version "1.1.7"
    id("java-library")
    id("maven-publish")
}

group = "com.github.solly29"
version = "0.0.5-SNAPSHOT"
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
}

repositories {
    mavenCentral()
}

publishing {
    // 라이브러리를 Maven 형식으로 게시할 것임을 선언
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    // GitLab 패키지 레지스트리 정보를 설정
    repositories {
        maven {
            name = "GitLab"
            // GitLab 프로젝트의 패키지 레지스트리 URL
            url = uri("http://192.168.1.32:8990/api/v4/projects/269/packages/maven")
            // 인증 정보
            credentials {
                username = System.getenv("CI_DEPLOY_USER") ?: findProperty("gitlabUser") as String?
                password = System.getenv("CI_DEPLOY_PASSWORD") ?: findProperty("gitlabToken") as String?
            }
        }
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter")
    api("org.springframework.boot:spring-boot-starter-aop")
    api("com.fasterxml.jackson.core:jackson-databind")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
