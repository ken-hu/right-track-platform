/*
 * This file was generated by the Gradle 'init' task.
 */



dependencies {
    api("org.springframework.boot:spring-boot-starter-web:2.7.7")
    api("org.springframework.boot:spring-boot-starter-aop:2.7.7")
    api("org.projectlombok:lombok:1.18.24")
    api("com.alibaba:druid:1.2.15")
    api(project(":rt-common"))
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.7")
}

group = "pers.ken.rt.starter"
description = "rt-pbac-spring-boot-starter"
