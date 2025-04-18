group = "pers.ken.rt"
version = "0.0.1-SNAPSHOT"

dependencies {
    // project
    implementation(project(":rt-common"))
    implementation(project(":pbac-spring-boot-starter"))
    // springboot
    implementation(libs.springboot.oauth2.resourceserver)
    implementation(libs.springboot.configuration.processor)
    implementation(libs.springboot.data.jpa)
    // springcloud
    implementation(libs.springcloud.alibaba.nacos.discovery)
    implementation(libs.springcloud.alibaba.nacos.config)
    implementation(libs.springcloud.bootstrap)

    // database
    implementation(libs.postgresql)
    implementation(libs.mybatis.starter)

    // utils
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)
    //test
    testImplementation(libs.springboot.test)
}
