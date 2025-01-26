dependencies {
    implementation("org.springframework.boot:spring-boot-starter-batch")
    // jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.18.2")
    // commons csv
    implementation("org.apache.commons:commons-csv:1.11.0")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.batch:spring-batch-test")
}

tasks.test {
    useJUnitPlatform()
}