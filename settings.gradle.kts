pluginManagement {
    includeBuild("build-logic")
}


rootProject.name = "summoning"


sequenceOf("api", "plugin").forEach {
    include("summoning-$it")
    project(":summoning-$it").projectDir = file(it)
}
include("plugin")
