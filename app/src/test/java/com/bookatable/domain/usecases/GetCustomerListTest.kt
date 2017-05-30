@file:Suppress("IllegalIdentifier")

package com.bookatable.domain.usecases

import com.bookatable.data.datasource.CustomerEntityDataSource
import com.bookatable.data.entity.CustomerEntity
import com.bookatable.domain.Customer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable.just
import rx.Scheduler
import rx.observers.TestSubscriber
import java.util.*
import kotlin.test.assertEquals

class GetCustomerListTest {

    private val mCustomerEntityDataSource: CustomerEntityDataSource = mock()
    private val mMockScheduler: Scheduler = mock()

    private lateinit var mGetCustomersList: GetCustomersList
    private lateinit var mTestSubscriber: TestSubscriber<List<Customer>>

    @Before fun setUp() {
        mTestSubscriber = TestSubscriber.create()
        mGetCustomersList = GetCustomersList(mMockScheduler, mMockScheduler, mCustomerEntityDataSource)
    }

    @Test fun `should get particular customer`() {
        val customers = createCustomersList()

        assumeDataSourceHasRequestedCustomers(customers)

        mGetCustomersList.call().subscribe(mTestSubscriber)

        assertEquals(customers.size, mTestSubscriber.onNextEvents[0].size)
    }

    private fun assumeDataSourceHasRequestedCustomers(customers: ArrayList<CustomerEntity>) {
        whenever(mCustomerEntityDataSource.customers()).thenReturn(just<List<CustomerEntity>>(customers))
    }

    private fun createCustomersList() = ArrayList<CustomerEntity>().apply {
        add(CustomerEntity())
    }
}


