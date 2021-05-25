package com.example.bookslist

import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

internal class ParseBookTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun parse() {
        val parseBook = ParseBook()

        val url =
            "https://www.goodreads.com/book/show/49552.The_Stranger?from_search=true&from_srp=true&qid=Yv0ZiMIA7Z&rank=1"
        val liveDataValue = parseBook.parse(url).getOrAwaitValue()

        assertEquals(
            "Albert Camus Vedat Günyol (Translator) Jalal Al-e Ahmad (Translator) Matthew Ward (Translator) علی\u200Cاصغر خبره\u200Cزاده (Translator)",
            liveDataValue.author
        )
        assertEquals("The Stranger", liveDataValue.name)

    }
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 7,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
