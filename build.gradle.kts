import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

import java.util.*

plugins {
  `java-library`
  `maven-publish`
  idea
  id("com.github.johnrengelman.shadow") version "8.1.1"
  id("io.papermc.paperweight.userdev") version "1.5.6"
  id("xyz.jpenilla.run-paper") version "2.1.0"
  id("net.minecrell.plugin-yml.paper") version "0.6.0"
}

group = "com.example.plugintemplate"
version = "1.0.0-SNAPSHOT"
description = "Plugin Template for Paper Servers"

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))

  withJavadocJar()
  withSourcesJar()
}

repositories {
  mavenCentral()
  mavenLocal()

  maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
  paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
  // paperweight.foliaDevBundle("1.20.1-R0.1-SNAPSHOT")
  // paperweight.devBundle("com.example.paperfork", "1.19.4-R0.1-SNAPSHOT")
}

tasks {
  assemble {
    dependsOn(reobfJar)
  }
  compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release.set(17)
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }
  /*
  reobfJar {
    // Set out dir
    outputJar.set(layout.buildDirectory.file("libs/PaperweightTestPlugin-${project.version}.jar"))
  }
   */
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = group.toString().lowercase(Locale.ENGLISH)
      artifactId = rootProject.name.lowercase(Locale.ENGLISH)
      version = project.version.toString()

      from(components["java"])
    }
  }
}

paper {
  name = rootProject.name
  version = project.version.toString()
  description = project.description.toString()
  website = "https://github.com/Arian100/PaperPluginTemplate"
  author = "XY"

  main = "com.example.testplugin.TestPlugin"
  apiVersion = "1.19"
  foliaSupported = true

  bootstrapper = "com.example.testplugin.bootstrapper.TestPluginBootsTrapper"
  loader = "com.example.testplugin.loader.TestPluginLoader"
  hasOpenClassloader = false
  generateLibrariesJson = true

  load = BukkitPluginDescription.PluginLoadOrder.STARTUP // POSTWORLD
  prefix = project.name
  defaultPermission = BukkitPluginDescription.Permission.Default.OP

  // authors = listOf("Notch", "Notch2")
  // provides = listOf("TestPluginOldName", "TestPlug")

  /*
  bootstrapDependencies {
    register("WorldEdit")

    register("BeforePlugin") {
      required = false
      load = PaperPluginDescription.RelativeLoadOrder.BEFORE
    }
    register("AfterPlugin") {
      required = false
      load = PaperPluginDescription.RelativeLoadOrder.AFTER
    }
  }

  serverDependencies {
    register("LuckPerms") {
      load = PaperPluginDescription.RelativeLoadOrder.BEFORE
    }

    register("WorldEdit") {
      load = PaperPluginDescription.RelativeLoadOrder.BEFORE
    }

    register("ProtocolLib") {
      required = false
    }

    register("Essentials") {
      required = false
      joinClasspath = false
    }
  }

  permissions {
    register("testplugin.*") {
      children = listOf("testplugin.test")
      childrenMap = mapOf("testplugin.test" to true)
    }
    register("testplugin.test") {
      description = "Allows you to run the test command"
      default = BukkitPluginDescription.Permission.Default.OP
    }
  }
   */
}
