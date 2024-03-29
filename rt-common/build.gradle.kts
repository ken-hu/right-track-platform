import org.springframework.boot.gradle.tasks.bundling.BootJar

/*
 * This file was generated by the Gradle 'init' task.
 */
description = "rt-common"

dependencies {
    // springboot
    api(libs.springboot.web)
    api(libs.springboot.data.jpa)
    api(libs.springboot.validation)
    runtimeOnly(libs.springboot.devtools)

    // web
    api(libs.springdoc.openapi)
    api(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)

    // utils
    api(libs.alibaba.druid)
    api(libs.google.guava)
    api(libs.apache.common.lang3)
}


tasks.withType<Jar> {
    enabled = true
}

tasks.withType<BootJar> {
    enabled = false
}

