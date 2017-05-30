@file:Suppress("IllegalIdentifier")

package com.bookatable.domain.usecases

import com.bookatable.data.datasource.CustomerEntityDataSource
import com.bookatable.data.entity.CustomerEntity
import com.bookatable.domain.model.Customer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable.just
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

    private fun assumeDataSourceHasSearchedContent(customers: ArrayList<CustomerEntity>) {
        whenever(mMockCustomerEntityDataSource.searchCustomersByName(FAKE_CUSTOMER_TITLE)).thenReturn(
                just<List<CustomerEntity>>(customers))
    }

    private fun createCustomersList() = ArrayList<CustomerEntity>().apply {
        add(CustomerEntity().apply { firstName = FAKE_CUSTOMER_TITLE })
    }
}