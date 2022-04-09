package osp.leobert.android.gdc

import android.database.sqlite.SQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.testing.EqualsTester
import org.junit.Test
import org.junit.runner.RunWith
import osp.leobert.android.gdc.daos.AppDaoCollector
import osp.leobert.android.gdc.daos.DaoMaster

@RunWith(AndroidJUnit4::class)
class AndroidDaoCollectorUnitTest {
    @Test
    fun testDaoCollector() {
        EqualsTester().addEqualityGroup(

            AppDaoCollector.getAllMigratedDaos().sortedBy { it.hashCode() },

            DaoMaster.allDao.sortedBy { it.hashCode() },

            DaoMaster(
                SQLiteDatabase.create(null)
            ).newSession().allDaos.map {
                it.javaClass
            }.toCollection(arrayListOf()).sortedBy { it.hashCode() }

        ).testEquals()
    }

}