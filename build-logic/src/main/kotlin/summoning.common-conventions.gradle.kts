import com.diffplug.gradle.spotless.FormatExtension
import java.util.*

plugins {
  id("net.kyori.indra")
  id("net.kyori.indra.licenser.spotless")
}

val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

indra {
  javaVersions {
    target(21)
    minimumToolchain(21)
    strictVersions(true)
  }
}

repositories {
  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
  compileOnly(libs.paper)
}

spotless {
  fun FormatExtension.applyCommon() {
    trimTrailingWhitespace()
    endWithNewline()
    indentWithSpaces(2)
  }
  java {
    importOrderFile(rootProject.file(".spotless/emptyte.importorder"))
    removeUnusedImports()
    applyCommon()
  }
  kotlinGradle {
    applyCommon()
  }
}

java {
  withJavadocJar()
}

tasks {
  jar {
    manifest {
      attributes(
        "Specification-Version" to project.version,
        "Specification-Vendor" to "emptyte-team",
        "Implementation-Build-Date" to Date()
      )
    }
  }

  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name()
    dependsOn(spotlessApply)
    options.compilerArgs.add("-parameters")
  }
}
