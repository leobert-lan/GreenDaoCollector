package osp.leobert.android.gdc

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.greenrobot.greendao.AbstractDao
import osp.leobert.android.gdc.daos.DaoMaster

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val allDao :List<Class<AbstractDao<*,*>>> = DaoMaster(
            SQLiteDatabase.create(null)
        ).newSession().allDaos.map {
            it.javaClass
        }.toCollection(arrayListOf())
    }
}