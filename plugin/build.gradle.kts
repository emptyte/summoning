import org.gradle.configurationcache.extensions.capitalized
import java.util.*

plugins {
  id("summoning.common-conventions")
  id("com.github.johnrengelman.shadow") version "8.1.1"
  id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

dependencies {
  implementation(project(":${rootProject.name}-api"))
  implementation(libs.configurate.gson)
}

bukkit {
  val projectName = rootProject.name.split("-").joinToString("") { it.capitalized() }
  val pluginName = "Emptyte$projectName"
  name = pluginName
  apiVersion = "1.20"
  main = "team.emptyte.${projectName.lowercase(Locale.ROOT)}.${pluginName}Plugin"
  description = "Emptyte $projectName plugin."
  authors = listOf("SrVenient")
}

tasks {
  shadowJar {
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")
  }
}
