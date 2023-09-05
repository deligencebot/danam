plugins {
	java
	id("org.springframework.boot") version "2.7.14"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.delbot"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	// implementation("javax.validation:validation-api")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE")
	// https://mvnrepository.com/artifact/org.modelmapper/modelmapper
	implementation("org.modelmapper:modelmapper:3.1.1")
	implementation("commons-io:commons-io:2.11.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
