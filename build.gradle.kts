plugins {
    id("java")
    id("maven-publish")
}

group = "cat.breadcat"
version = "2.1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("cat.breadcat:breech:2.0.0")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}