package ch.comparis.challenge

import org.mockito.Mockito

inline fun <reified T> mock(): T = Mockito.mock(T::class.java, Mockito.RETURNS_DEEP_STUBS)