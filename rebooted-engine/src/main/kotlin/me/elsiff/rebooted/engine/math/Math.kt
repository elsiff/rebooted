package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-07-14.
 */
const val EPSILON = 0.0000000001f

fun clamp(value: Float, min: Float, max: Float) =
    kotlin.math.max(kotlin.math.min(value, max), min)

fun clamp(value: Vector2f, min: Vector2f, max: Vector2f) =
    vec2f(clamp(value.x, min.x, max.x), clamp(value.y, min.y, max.y))

fun lerp(from: Float, to: Float, d: Float) =
    from + d * (to - from)

fun lerp(from: Vector2f, to: Vector2f, d: Float): Vector2f =
    vec2f(lerp(from.x, to.x, d), lerp(from.y, to.y, d))

fun lerp(from: Vector3f, to: Vector3f, d: Float): Vector3f =
    vec3f(lerp(from.x, to.x, d), lerp(from.y, to.y, d), lerp(from.z, to.z, d))