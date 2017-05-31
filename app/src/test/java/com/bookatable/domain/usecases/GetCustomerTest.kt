@file:Suppress("IllegalIdentifier")

package com.bookatable.domain.usecases

import com.bookatable.data.datasource.CustomerEntityDataSource
import com.bookatable.data.entity.Customer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.anyInt
import rx.Single.just
import rx.observers.TestSubscriber
import kotlin.test.assertEquals

class GetCustomerTest {

    private val mCustomerEntityDataSource: CustomerEntityDataSource = mock()

    private lateinit var mGetCustomer: GetCustomer
    private lateinit var mTestSubscriber: TestSubscriber<Customer>


    companion object {
        private val FAKE_CUSTOMER_ID = 123
    }

    @Before fun setUp() {
        mTestSubscriber = TestSubscriber.create()
        mGetCustomer = GetCustomer(mCustomerEntityDataSource)
    }

    @Test fun `should get particular customer`() {
        val customerEntity = createCustomerEntity()
        assumeDataSourceHasRequestedCustomer(customerEntity)

        mGetCustomer.call(FAKE_CUSTOMER_ID).subscribe(mTestSubscriber)

        assertEquals(FAKE_CUSTOMER_ID, mTestSubscriber.onNextEvents[0].id)
    }

    private fun createCustomerEntity() = Customer().apply {
        id = FAKE_CUSTOMER_ID
    }

    private fun assumeDataSourceHasRequestedCustomer(customer: Customer) {
        whenever(mCustomerEntityDataSource.customer(anyInt())).thenReturn(
                just(customer))
    }
}
