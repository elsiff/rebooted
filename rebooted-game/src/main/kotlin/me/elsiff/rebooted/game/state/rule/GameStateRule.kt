package me.elsiff.rebooted.game.state.rule

import me.elsiff.rebooted.engine.state.GameState
import me.elsiff.rebooted.game.GameRule

/**
 * Created by elsiff on 2019-08-21.
 */
interface GameStateRule : GameRule {
    val gameState: GameState
}