dependencies {
    implementation(project(":rt-common"))
    implementation(libs.postgresql)
//    implementation(libs.springboot.data.jpa)
//    implementation(libs.springboot.security)
//    implementation(libs.springboot.oauth2.resourceserver)
//    implementation(libs.spring.security.authorizationserver)
    implementation(libs.springcloud.bootstrap)
//    implementation(libs.alibaba.springcloud.nacos.discovery)
//    implementation(libs.alibaba.springcloud.nacos.config)
//    implementation(libs.springboot.configuration.processor)
    implementation(libs.springcloud.loadbalancer)
    testImplementation(libs.springboot.test)
    implementation("com.aliyun.oss:aliyun-sdk-oss:3.8.0")
    implementation("com.alibaba:fastjson:2.0.25")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}