package cl.ravenhill.echo.util

/**
 * Returns the input string as it is.
 *
 * This function takes a string as input and returns the same string without any modifications.
 *
 * ## Usage:
 * ```
 * val text = "Hello, world!"
 * val result = id(text)
 * // result == "Hello, world!"
 * ```
 *
 * @param text The input string to be returned.
 * @return The same input string.
 */
fun id(text: String) = text
