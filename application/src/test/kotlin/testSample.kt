import kotlin.test.Test
import kotlin.test.assertEquals

class Sample() {
    fun sum(a: Int, b: Int): Int {
        return a + b
    }
}


internal class SampleTest {
    private val testSample: Sample = Sample()
    @Test
    fun testSum() {
        val expected = 42
        assertEquals(expected, testSample.sum(40, 2))
    }
}