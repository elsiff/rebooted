package me.elsiff.rebooted.game

/**
 * Created by elsiff on 2019-02-26.
 */
fun main(args: Array<String>) {
    if (args.isNotEmpty() && args[0] == "debug")
        Game.isDebugMode = true

    Game.start()
}