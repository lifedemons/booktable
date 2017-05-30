@file:Suppress("IllegalIdentifier")

package com.bookatable.domain.usecases

import com.bookatable.data.datasource.CustomerEntityDataSource
import com.bookatable.data.entity.CustomerEntity
import com.bookatable.domain.model.Customer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.anyInt
import rx.Observable
import rx.Scheduler
import rx.observers.TestSubscriber
import kotlin.test.assertEquals

class GetCustomerTest {

    private val mCustomerEntityDataSource: CustomerEntityDataSource = mock()
    private val mScheduler: Scheduler = mock()

    private lateinit var mGetCustomer: GetCustomer
    private lateinit var mTestSubscriber: TestSubscriber<Customer>


    companion object {
        private val FAKE_CUSTOMER_ID = 123
    }

    @Before fun setUp() {
        mTestSubscriber = TestSubscriber.create()
        mGetCustomer = GetCustomer(mScheduler, mScheduler, mCustomerEntityDataSource)
    }

    @Test fun `should get particular customer`() {
        val customerEntity = createCustomerEntity()
        assumeDataSourceHasRequestedCustomer(customerEntity)

        mGetCustomer.setCustomerId(FAKE_CUSTOMER_ID)
        mGetCustomer.call().subscribe(mTestSubscriber)

        assertEquals(FAKE_CUSTOMER_ID, mTestSubscriber.onNextEvents[0].id)
    }

    private fun createCustomerEntity() = CustomerEntity().apply {
        id = FAKE_CUSTOMER_ID
    }

    private fun assumeDataSourceHasRequestedCustomer(customerEntity: CustomerEntity) {
        whenever(mCustomerEntityDataSource.customer(anyInt())).thenReturn(
                Observable.just(customerEntity))
    }
}
