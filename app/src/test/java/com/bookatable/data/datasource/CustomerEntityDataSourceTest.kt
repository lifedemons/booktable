@file:Suppress("IllegalIdentifier")

package com.bookatable.data.datasource

import com.bookatable.data.datasource.datastore.DatabaseCustomerEntityStore
import com.bookatable.data.datasource.datastore.ServerCustomerEntityStore
import com.bookatable.data.entity.CustomerEntity
import com.bookatable.domain.usecases.SimpleSubscriber
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import rx.Observable.just
import java.util.*

class CustomerEntityDataSourceTest {

    private val mDatabaseCustomerEntityStore: DatabaseCustomerEntityStore = mock()
    private val mServerCustomerEntityStore: ServerCustomerEntityStore = mock()

    private lateinit var mCustomerEntityDataSource: CustomerEntityDataSource

    companion object {
        private val FAKE_CUSTOMER_ID = 31
    }

    @Before fun setUp() {
        mCustomerEntityDataSource = CustomerEntityDataSource(mDatabaseCustomerEntityStore, mServerCustomerEntityStore)
    }

    @Test fun `should query database on getting customers`() {
        val customersList = createCustomersList()

        assumeDatabaseIsEmpty()
        assumeServerHasRequestedContent(customersList)

        mCustomerEntityDataSource.customers().subscribe(SimpleSubscriber<List<CustomerEntity>>())

        verify(mDatabaseCustomerEntityStore).queryForAll()
    }

    @Test fun `should query server on getting customers if database does not have them`() {
        val customersList = createCustomersList()

        assumeDatabaseIsEmpty()
        assumeServerHasRequestedContent(customersList)

        mCustomerEntityDataSource.customers().subscribe(SimpleSubscriber<List<CustomerEntity>>())

        verify(mServerCustomerEntityStore).customerEntityList()
    }

    @Test fun `should save retrieved customers from server on getting customers`() {
        val customersList = createCustomersList()

        assumeDatabaseIsEmpty()
        assumeServerHasRequestedContent(customersList)

        mCustomerEntityDataSource.customers().subscribe(SimpleSubscriber<List<CustomerEntity>>())

        verify(mDatabaseCustomerEntityStore).saveAll(customersList)
    }

    private fun createCustomersList() = ArrayList<CustomerEntity>().apply {
        add(CustomerEntity())
    }

    private fun assumeDatabaseIsEmpty() {
        whenever(mDatabaseCustomerEntityStore.queryForAll()).thenReturn(
                just<List<CustomerEntity>>(ArrayList<CustomerEntity>()))
    }

    private fun assumeServerHasRequestedContent(customersList: List<CustomerEntity>) {
        whenever(mServerCustomerEntityStore.customerEntityList()).thenReturn(just<List<CustomerEntity>>(customersList))
    }

    @Test fun `should not query server on getting customers if database has them`() {
        val customerEntities = createCustomersList()
        assumeDatabaseHasRequestesContent(customerEntities)
        assumeServerHasRequestedContent(customerEntities)

        mCustomerEntityDataSource.customers().subscribe()

        verify(mDatabaseCustomerEntityStore).queryForAll()
        verifyZeroInteractions(mServerCustomerEntityStore)
    }

    private fun assumeDatabaseHasRequestesContent(customerEntities: List<CustomerEntity>) {
        whenever(mDatabaseCustomerEntityStore.queryForAll()).thenReturn(just<List<CustomerEntity>>(customerEntities))
    }

    @Test fun `should query only database on getting particular customer`() {
        val customerEntity = CustomerEntity()
        whenever(mDatabaseCustomerEntityStore.queryForId(FAKE_CUSTOMER_ID)).thenReturn(
                just(customerEntity))

        mCustomerEntityDataSource.customer(FAKE_CUSTOMER_ID).subscribe()

        verify(mDatabaseCustomerEntityStore).queryForId(FAKE_CUSTOMER_ID)
    }
}
