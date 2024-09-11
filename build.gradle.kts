val exposedVersion: String by project

plugins {
  kotlin("jvm") version "2.0.10"
}

group = "com.lbty"
version = "1.0-SNAPSHOT"

repositories {
  flatDir {
    dirs("lib")  // 指定 lib 目录
  }
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
//  implementation(files("lib/exposed-core-0.54.0.jar"))
//  implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion") {
    exclude(group = "org.jetbrains.exposed", module = "exposed-core")
  }
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion") {
    exclude(group = "org.jetbrains.exposed", module = "exposed-core")
  }

//  implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")
  // or
//  implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
  // or
//  implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")

//  implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
//  implementation("org.jetbrains.exposed:exposed-money:$exposedVersion")
  // https://mvnrepository.com/artifact/org.postgresql/postgresql
  implementation("org.postgresql:postgresql:42.7.4")
// https://mvnrepository.com/artifact/com.zaxxer/HikariCP
  implementation("com.zaxxer:HikariCP:5.1.0")

// https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
  implementation("ch.qos.logback:logback-classic:1.5.8")

}
tasks.test {
  useJUnitPlatform()
}
kotlin {
  jvmToolchain(17)
}
