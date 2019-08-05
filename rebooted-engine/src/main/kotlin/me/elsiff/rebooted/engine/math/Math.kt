package me.elsiff.rebooted.engine.math

/**
 * Created by elsiff on 2019-07-14.
 */
const val EPSILON = 0.0000000001f

fun clamp(value: Float, min: Float, max: Float) =
    kotlin.math.max(kotlin.math.min(value, max), min)

fun clamp(value: Vector2f, min: Vector2f, max: Vector2f) =
    vec2f(
        x = clamp(value.x, min.x, max.x),
        y = clamp(value.y, min.y, max.y)
    )

fun clamp(value: Vector3f, min: Vector3f, max: Vector3f) =
    vec3f(
        x = clamp(value.x, min.x, max.x),
        y = clamp(value.y, min.y, max.y),
        z = clamp(value.z, min.z, max.z)
    )

fun clamp(value: Vector4f, min: Vector4f, max: Vector4f) =
    vec4f(
        x = clamp(value.x, min.x, max.x),
        y = clamp(value.y, min.y, max.y),
        z = clamp(value.z, min.z, max.z),
        w = clamp(value.w, min.w, max.w)
    )

fun lerp(from: Float, to: Float, d: Float) =
    from + d * (to - from)

fun lerp(from: Vector2f, to: Vector2f, d: Float): Vector2f =
    vec2f(
        x = lerp(from.x, to.x, d),
        y = lerp(from.y, to.y, d)
    )

fun lerp(from: Vector3f, to: Vector3f, d: Float): Vector3f =
    vec3f(
        x = lerp(from.x, to.x, d),
        y = lerp(from.y, to.y, d),
        z = lerp(from.z, to.z, d)
    )

fun lerp(from: Vector4f, to: Vector4f, d: Float): Vector4f =
    vec4f(
        x = lerp(from.x, to.x, d),
        y = lerp(from.y, to.y, d),
        z = lerp(from.z, to.z, d),
        w = lerp(from.w, to.w, d)
    )