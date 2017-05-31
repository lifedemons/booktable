@file:Suppress("IllegalIdentifier")

package com.bookatable.data.datasource.datastore.table

import com.bookatable.data.entity.Table
import com.bookatable.data.network.TablesRestService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable.just
import rx.observers.TestSubscriber
import java.util.*
import kotlin.test.assertEquals

class ServerTableEntityStoreTest {

    private val mRestService: TablesRestService = mock()

    private lateinit var mServerTableEntityStore: ServerTableEntityStore
    private lateinit var mTestSubscriber: TestSubscriber<List<Table>>

    companion object {
        private val FAKE_CUSTOMER_ID = 31
    }

    @Before fun setUp() {
        mServerTableEntityStore = ServerTableEntityStore(mRestService)
        mTestSubscriber = TestSubscriber.create()
    }

    @Test fun `should assign ids to tables`() {
        assumeServerHasTables()

        mServerTableEntityStore.tablesList().subscribe(mTestSubscriber)

        assertEquals(createTablesWithIdsList(), mTestSubscriber.onNextEvents[0])
    }

    private fun assumeServerHasTables() {
        whenever(mRestService.tables).thenReturn(just(createTablesList()))
    }

    private fun createTablesList() = (0..29).mapTo(ArrayList<Table>(30)) {
        Table()
    }

    private fun createTablesWithIdsList() = (0..29).mapTo(ArrayList<Table>(30)) {
        Table().apply {
            id = it
        }
    }
}