/*
 * This file was generated by the Gradle 'init' task.
 */


rootProject.name = "right-track-platform"
include(":rt-common")
include(":rt-gateway")
include(":rt-mall-api")
include(":rt-mall-service")
include(":rt-auth-api")
include(":rt-auth-service")
include(":rt-account-api")
include(":rt-account-service")
include(":rt-pbac-spring-boot-starter")
project(":rt-pbac-spring-boot-starter").projectDir = file("rt-common-starter/rt-pbac-spring-boot-starter")

pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("springboot", "2.7.7")
            version("springcloud", "2021.0.5")
            version("alibaba-springcloud", "2021.1")
            version("springdoc", "1.6.14")
            version("guava", "31.1-jre")
            version("common-lang3", "3.12.0")
            version("mapstruct", "1.5.3.Final")
            version("druid", "1.2.15")
            version("nimbusds","10.4")
            version("oauth2-jose","5.7.6")
            version("postgresql-driver","42.5.1")
            version("hibernate-types-55","2.21.1")
            version("spring-security-authorizationserver","0.4.0")


            library("springboot-web","org.springframework.boot","spring-boot-starter-web").withoutVersion()
            library("springboot-data-jpa","org.springframework.boot","spring-boot-starter-data-jpa").withoutVersion()
            library("springboot-validation","org.springframework.boot","spring-boot-starter-validation").withoutVersion()
            library("springboot-devtools","org.springframework.boot","spring-boot-devtools").withoutVersion()
            library("springboot-actuator","org.springframework.boot","spring-boot-starter-actuator").withoutVersion()
            library("springboot-oauth2-resourceserver","org.springframework.boot","spring-boot-starter-oauth2-resource-server").withoutVersion()
            library("springboot-oauth2-client","org.springframework.boot","spring-boot-starter-oauth2-client").withoutVersion()
            library("spring-security-oauth2-autoconfig","org.springframework.security.oauth.boot","spring-security-oauth2-autoconfigure").withoutVersion()
            library("spring-security-authorizationserver","org.springframework.security","spring-security-oauth2-authorization-server").versionRef("spring-security-authorizationserver")
            library("springboot-security","org.springframework.boot","spring-boot-starter-security").withoutVersion()
            library("springboot-configuration-processor","org.springframework.boot","spring-boot-configuration-processor").withoutVersion()
            library("springboot-test","org.springframework.boot","spring-boot-starter-test").withoutVersion()
            library("oauth2-oidc-sdk","com.nimbusds","oauth2-oidc-sdk").versionRef("nimbusds")
            library("oauth2-jose","org.springframework.security","spring-security-oauth2-jose").versionRef("oauth2-jose")

            library("springcloud-dependencies","org.springframework.cloud", "spring-cloud-dependencies").versionRef("springcloud")
            library("springcloud-openfeign","org.springframework.cloud", "spring-cloud-starter-openfeign").withoutVersion()
            library("springcloud-gateway","org.springframework.cloud","spring-cloud-starter-gateway").withoutVersion()
            library("springcloud-loadbalancer","org.springframework.cloud","spring-cloud-starter-loadbalancer").withoutVersion()
            library("alibaba-springcloud-dependencies","com.alibaba.cloud", "spring-cloud-alibaba-dependencies").versionRef("alibaba-springcloud")
            library("alibaba-springcloud-sentinel-gateway","com.alibaba.cloud","spring-cloud-alibaba-sentinel-gateway").withoutVersion()
            library("alibaba-springcloud-sentinel","com.alibaba.cloud","spring-cloud-starter-alibaba-sentinel").withoutVersion()
            library("alibaba-springcloud-nacos-discovery","com.alibaba.cloud","spring-cloud-starter-alibaba-nacos-discovery").withoutVersion()
            library("alibaba-springcloud-nacos-config","com.alibaba.cloud","spring-cloud-starter-alibaba-nacos-config").withoutVersion()

            library("springdoc-openapi","org.springdoc","springdoc-openapi-ui").versionRef("springdoc")
            library("springdoc-openapi-webflux","org.springdoc","springdoc-openapi-webflux-ui").versionRef("springdoc")

            library("lombok","org.projectlombok", "lombok").withoutVersion()
            library("google-guava","com.google.guava", "guava").versionRef("guava")
            library("postgresql","org.postgresql", "postgresql").versionRef("postgresql-driver")
            library("mapstruct","org.mapstruct", "mapstruct").versionRef("mapstruct")
            library("mapstruct-processor","org.mapstruct", "mapstruct-processor").versionRef("mapstruct")
            library("alibaba-druid","com.alibaba", "druid").versionRef("druid")
            library("apache-common-lang3","org.apache.commons", "commons-lang3").versionRef("common-lang3")
            library("hibernate-types55","com.vladmihalcea", "hibernate-types-55").versionRef("hibernate-types-55")
        }
    }
}
