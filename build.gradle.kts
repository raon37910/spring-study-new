plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

// 하위 프로젝트에 적용할 설정
subprojects {

    group = "com.raon"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(21)
		}
	}

	repositories {
		mavenCentral()
	}
	// 하위 프로젝트에 추가할 의존성
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		// lombok
		compileOnly("org.projectlombok:lombok:1.18.30")
		annotationProcessor("org.projectlombok:lombok:1.18.30")

		// testlombok
		testCompileOnly("org.projectlombok:lombok:1.18.30")
		testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
