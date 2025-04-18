/**
 * Gradle build.gradle.kts init by @ken
 */
description = "Right track platform build"
version = "0.0.1-SNAPSHOT"

plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    `version-catalog` apply true
    kotlin("jvm") version ("1.9.23") apply true
}

subprojects {
    group = "pers.ken.rt"
    version = rootProject.version

    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    java {
        java.sourceCompatibility = JavaVersion.VERSION_17
        java.targetCompatibility = JavaVersion.VERSION_17
    }



    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
        testImplementation {
            extendsFrom(configurations.compileOnly.get())
        }
    }


    repositories {
        mavenLocal()
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/central")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/spring")
        maven("https://maven.aliyun.com/repository/spring-plugin")
        maven("https://maven.aliyun.com/repository/grails-core")
        maven("https://maven.aliyun.com/repository/apache-snapshots")
        maven("https://nexus.bsdn.org/content/groups/public/")
        maven("https://repo1.maven.org/maven2")
        mavenCentral()
    }

    dependencies {
        implementation(platform(rootProject.libs.springcloud.dependencies))
        implementation(platform(rootProject.libs.springboot.dependencies))
        implementation(platform(rootProject.libs.springcloud.alibaba.dependencies))
        annotationProcessor(rootProject.libs.lombok)
        compileOnly(rootProject.libs.lombok)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        enabled = false
    }
}

