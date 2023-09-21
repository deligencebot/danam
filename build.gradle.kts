import com.ewerk.gradle.plugins.tasks.QuerydslCompile

plugins {
	java
	id("org.springframework.boot") version "2.7.14"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
}

group = "com.delbot"
version = "0.0.1-SNAPSHOT"
val queryDslVersion = "5.0.0" // QueryDSL Version Setting

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
	// Spring Dependencies
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework:spring-aop")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	//  QueryDSL
	implementation("com.querydsl:querydsl-jpa:${queryDslVersion}")
  annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}")

	// Lombok & Jdbc Driver
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")

	// Thymeleaf
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE")
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")

	// Utility
	implementation("org.modelmapper:modelmapper:3.1.1")
	implementation("commons-io:commons-io:2.11.0")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// QueryDSL Build Options
val querydslDir = "$buildDir/generated/querydsl"

querydsl {
	jpa = true
	querydslSourcesDir = querydslDir
}
sourceSets.getByName("main") {
	java.srcDir(querydslDir)
}
configurations {
	named("querydsl") {
		extendsFrom(configurations.compileClasspath.get())
	}
}
tasks.withType<QuerydslCompile> {
	options.annotationProcessorPath = configurations.querydsl.get()
}