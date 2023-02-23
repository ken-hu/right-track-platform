/*
 * This file was generated by the Gradle 'init' task.
 */



dependencies {
    implementation(project(":rt-common"))
    implementation(project(":rt-auth-api"))
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)
    implementation(libs.postgresql)
    implementation(libs.springboot.data.jpa)
    implementation(libs.springboot.security)
    implementation(libs.springboot.oauth2.resourceserver)
    implementation(libs.spring.security.authorizationserver)
    implementation(libs.hibernate.types55)
    implementation(libs.spring.security.oauth2.autoconfig)
    implementation(libs.alibaba.springcloud.nacos.discovery)
    implementation(libs.alibaba.springcloud.nacos.config)
    implementation(libs.springboot.configuration.processor)
    implementation(libs.springcloud.loadbalancer)
    testImplementation(libs.springboot.test)
}

description = "rt-auth-service"