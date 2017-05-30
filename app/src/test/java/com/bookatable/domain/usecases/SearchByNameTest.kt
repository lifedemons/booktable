@file:Suppress("IllegalIdentifier")

package com.bookatable.domain.usecases

import com.bookatable.data.datasource.CustomerEntityDataSource
import com.bookatable.data.entity.Customer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Single.just
import rx.Scheduler
import rx.observers.TestSubscriber
import java.util.*
import kotlin.test.assertEquals

class SearchByNameTest {

    private val mMockCustomerEntityDataSource: CustomerEntityDataSource = mock()
    private val mMockScheduler: Scheduler = mock()

    private lateinit var mSearchByName: SearchByName
    private lateinit var mTestSubscriber: TestSubscriber<List<Customer>>

    companion object {
        private val FAKE_CUSTOMER_TITLE = "Fake Title"
    }

    @Before fun setUp() {
        mTestSubscriber = TestSubscriber.create()
        mSearchByName = SearchByName(mMockScheduler, mMockScheduler, mMockCustomerEntityDataSource)
    }

    @Test fun `should search by title`() {
        val customers = createCustomersList()

        assumeDataSourceHasSearchedContent(customers)

        mSearchByName.setSearchedTitle(FAKE_CUSTOMER_TITLE)
        mSearchByName.call().subscribe(mTestSubscriber)

        assertEquals(customers.size, mTestSubscriber.onNextEvents[0].size)
    }

    private fun assumeDataSourceHasSearchedContent(customers: ArrayList<Customer>) {
        whenever(mMockCustomerEntityDataSource.searchCustomersByName(FAKE_CUSTOMER_TITLE)).thenReturn(
                just<List<Customer>>(customers))
    }

    private fun createCustomersList() = ArrayList<Customer>().apply {
        add(Customer().apply { firstName = FAKE_CUSTOMER_TITLE })
    }
}