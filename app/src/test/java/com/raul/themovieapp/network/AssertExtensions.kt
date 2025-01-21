@file:Suppress("UNCHECKED_CAST")

package com.raul.themovieapp.network

import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.StringContains
import kotlin.reflect.KClass

infix fun <T> T.should(predicate: (T) -> Boolean) = assertThat("Should match predicate, was: $this", predicate(this))

infix fun <T> T.shouldEqual(expected: T) = assertThat(this, equalTo(expected))

infix fun <T> T.shouldNotEqual(expected: T) = assertThat(this, not(equalTo(expected)))

infix fun <T> List<T>.shouldContain(expected: T) = assertThat(this, hasItem(expected))

infix fun <T> List<T>.shouldContain(expected: Matcher<T>) = assertThat(this, hasItem(expected))

infix fun <T> List<T>.shouldContain(predicate: (T) -> Boolean) = assertThat(this, hasItemMatching(predicate))

infix fun <T> List<T>.shouldNotContain(expected: T) = assertThat(this, not(hasItem(expected)))

infix fun <T> List<T>.shouldNotContain(expected: Matcher<T>) = assertThat(this, not(hasItem(expected)))

infix fun <T> List<T>.shouldNotContain(predicate: (T) -> Boolean) = assertThat(this, not(hasItemMatching(predicate)))

infix fun String?.shouldContain(expected: String) = assertThat(this, StringContains(expected))

inline infix fun <reified T : Any> Any?.shouldBeA(@Suppress("UNUSED_PARAMETER") expected: KClass<T>) =
    assertThat("$this should be an instance of $expected", this is T, equalTo(true))

inline infix fun <reified T : Any> Any?.shouldNotBeA(@Suppress("UNUSED_PARAMETER") expected: KClass<T>) =
    assertThat("$this should not be an instance of $expected", this is T, equalTo(false))

fun <T> List<T>.assertEmpty() = this.size shouldEqual 0

fun <T> List<T>.assertNotEmpty() = this.size shouldNotEqual 0

fun Any?.assertNull() = assertThat(this, nullValue())

fun Any?.assertNotNull() = assertThat(this, notNullValue())

fun Boolean.assertTrue() = assertThat(this, equalTo(true))

fun Boolean.assertFalse() = assertThat(this, equalTo(false))


private fun <T> hasItemMatching(predicate: (T) -> Boolean) = hasItem(object : BaseMatcher<T>() {

    override fun describeTo(description: Description?) {
        description?.appendText("item that matches provided predicate $predicate")
    }

    override fun matches(item: Any?): Boolean {
        return (item as? T)?.let { predicate.invoke(item) } ?: false
    }
})
