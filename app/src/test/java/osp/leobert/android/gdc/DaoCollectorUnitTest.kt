package osp.leobert.android.gdc

import android.database.sqlite.SQLiteDatabase
import com.google.common.testing.EqualsTester
import org.junit.Test
import osp.leobert.android.gdc.daos.AppDaoCollector
import osp.leobert.android.gdc.daos.DaoMaster

//@RunWith(JUnit4::class)
//@RunWith(RobolectricTestRunner::class)
//@Config(
//    constants = BuildConfig::class,
//    manifest = Config.NONE,
//    packageName = "osp.leobert.android.gdc"
//)

class DaoCollectorUnitTest {
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