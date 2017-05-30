@file:Suppress("IllegalIdentifier")
package com.customerviewer.domain.usecases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.customerviewer.data.entity.CustomerEntity
import com.customerviewer.data.datasource.CustomerEntityDataSource
import com.customerviewer.domain.Customer
import org.junit.Before
import org.junit.Test
import rx.Observable.just
import rx.Scheduler
import rx.observers.TestSubscriber
import java.util.*
import kotlin.test.assertEquals

class SearchByTitleTest {

    private val mMockCustomerEntityDataSource: CustomerEntityDataSource = mock()
    private val mMockScheduler: Scheduler = mock()

    private lateinit var mSearchByTitle: SearchByTitle
    private lateinit var mTestSubscriber: TestSubscriber<List<Customer>>

    companion object {
        private val FAKE_CUSTOMER_TITLE = "Fake Title"
    }

    @Before fun setUp() {
        mTestSubscriber = TestSubscriber.create()
        mSearchByTitle = SearchByTitle(mMockScheduler, mMockScheduler, mMockCustomerEntityDataSource)
    }

    @Test fun `should search by title`() {
        val customers = createCustomersList()

        assumeDataSourceHasSearchedContent(customers)

        mSearchByTitle.setSearchedTitle(FAKE_CUSTOMER_TITLE)
        mSearchByTitle.call().subscribe(mTestSubscriber)

        assertEquals(customers.size, mTestSubscriber.onNextEvents[0].size)
    }

    private fun assumeDataSourceHasSearchedContent(customers: ArrayList<CustomerEntity>) {
        whenever(mMockCustomerEntityDataSource.searchCustomersByName(FAKE_CUSTOMER_TITLE)).thenReturn(
                just<List<CustomerEntity>>(customers))
    }

    private fun createCustomersList() = ArrayList<CustomerEntity>().apply {
        add(CustomerEntity().apply { firstName = FAKE_CUSTOMER_TITLE })
    }
}