plugins {
    id("java")
}

group = "pers.ken.rt"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":rt-common"))
    implementation(project(":pbac-spring-boot-starter"))
    implementation(libs.springboot.oauth2.resourceserver)
    implementation(libs.postgresql)
    implementation(libs.mybatis.starter)
    implementation(libs.springboot.data.jpa)
    implementation(libs.alibaba.springcloud.nacos.discovery)
    implementation(libs.alibaba.springcloud.nacos.config)
    implementation(libs.springboot.configuration.processor)
    implementation(libs.springcloud.bootstrap)
    testImplementation(libs.springboot.test)
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}