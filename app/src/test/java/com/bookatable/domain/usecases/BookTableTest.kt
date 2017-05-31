@file:Suppress("IllegalIdentifier")
package com.bookatable.domain.usecases

import com.bookatable.data.datasource.TableEntityDataSource
import com.bookatable.data.entity.Customer
import com.bookatable.data.entity.Table
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Completable
import rx.observers.TestSubscriber

class BookTableTest {

    private val mTableEntityDataSource: TableEntityDataSource = mock()

    private lateinit var mBookTable: BookTable
    private lateinit var mTestSubscriber: TestSubscriber<Void>

    @Before fun setUp() {
        mTestSubscriber = TestSubscriber.create()
        mBookTable = BookTable(mTableEntityDataSource)
    }

    @Test fun `should book table`() {
        val table = Table().apply {
            id = 1
        }

        var fakeCustomer = Customer().apply {
            id = 2
        }

        val bookedTable = Table().apply {
            id = table.id
            isBooked = true
            customer = fakeCustomer
        }

        assumeDataSourceCanUpdateTable()

        mBookTable.call(table, fakeCustomer).toObservable<Void>().subscribe(mTestSubscriber)

        verify(mTableEntityDataSource).update(ArrayList<Table>().apply {
            add(bookedTable)
        })
    }

    private fun assumeDataSourceCanUpdateTable() {
        whenever(mTableEntityDataSource.update(any())).thenReturn(Completable.complete())
    }
}