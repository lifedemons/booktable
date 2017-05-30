@file:Suppress("IllegalIdentifier")

package com.customerviewer.domain.usecases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.customerviewer.data.entity.CustomerEntity
import com.customerviewer.data.datasource.CustomerEntityDataSource
import com.customerviewer.domain.Customer
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.anyInt
import rx.Observable
import rx.Scheduler
import rx.observers.TestSubscriber
import kotlin.test.assertEquals

class GetCustomerDetailsTest {

    private val mCustomerEntityDataSource: CustomerEntityDataSource = mock()
    private val mScheduler: Scheduler = mock()

    private lateinit var mGetCustomerDetails: GetCustomerDetails
    private lateinit var mTestSubscriber: TestSubscriber<Customer>


    companion object {
        private val FAKE_CUSTOMER_ID = 123
    }

    @Before fun setUp() {
        mTestSubscriber = TestSubscriber.create()
        mGetCustomerDetails = GetCustomerDetails(mScheduler, mScheduler, mCustomerEntityDataSource)
    }

    @Test fun `should get particular customer`() {
        val customerEntity = createCustomerEntity()
        assumeDataSourceHasRequestedCustomer(customerEntity)

        mGetCustomerDetails.setCustomerId(FAKE_CUSTOMER_ID)
        mGetCustomerDetails.call().subscribe(mTestSubscriber)

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
