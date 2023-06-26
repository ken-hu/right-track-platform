group = "pers.ken.rt"
version = "0.0.1-SNAPSHOT"
description = "rt-storage-spring-boot-starter"



dependencies {
    api(libs.springboot.web)
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-io:1.3.2")
    compileOnly("com.aliyun.oss:aliyun-sdk-oss:3.8.0")
    compileOnly("com.huaweicloud:esdk-obs-java-bundle:3.22.12")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

