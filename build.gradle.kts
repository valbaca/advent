plugins {
    java
    application
    id("io.freefair.lombok") version "5.3.3.3"
    id("com.palantir.graal") version "0.9.0"
}

group = "com.valbaca.advent"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.20")
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-text:1.9")
    implementation("org.apache.commons:commons-math3:3.6.1")

    // vavr gives good FP
//    implementation("io.vavr:vavr:0.10.3")

// Eclipse Collections are memory-efficient alternatives to java.util collections
//    implementation("org.eclipse.collections:eclipse-collections-api:10.4.0")
//    implementation("org.eclipse.collections:eclipse-collections:10.4.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.assertj:assertj-guava:3.4.0")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("com.valbaca.advent.Main")
    applicationDefaultJvmArgs = listOf("-Xms2G", "-Xmx16G")
}

graal {
    mainClass("com.valbaca.advent.Main")
    outputName("advent")
    graalVersion("21.2.0")
    javaVersion("16")
}