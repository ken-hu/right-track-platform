plugins {
    java
    id("org.springframework.boot") version "3.0.3"
    id("io.spring.dependency-management") version "1.1.0"
    `version-catalog`
    kotlin("jvm") version ("1.7.10")
}

description = "Right track platform build"
java {
    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17
}


allprojects {
    group = "pers.ken.rt"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

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
    }

    dependencies {
        annotationProcessor("org.projectlombok:lombok")
        compileOnly("org.projectlombok:lombok")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.1")
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.0.3")
            mavenBom("com.alibaba.cloud:spring-cloud-alibaba-dependencies:2022.0.0.0-RC1")
        }
    }


    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

