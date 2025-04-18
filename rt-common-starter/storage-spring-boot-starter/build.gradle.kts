group = "pers.ken.rt"
version = "0.0.1-SNAPSHOT"
description = "storage-spring-boot-starter"



dependencies {
    api(libs.springboot.web)
    api(libs.apache.common.lang3)
    api(libs.apache.common.io)
    compileOnly(libs.storage.obs)
    compileOnly(libs.storage.oss)
    annotationProcessor(libs.springboot.configuration.processor)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

