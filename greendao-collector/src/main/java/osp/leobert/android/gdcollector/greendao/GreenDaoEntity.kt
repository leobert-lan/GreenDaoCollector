package osp.leobert.android.gdcollector.greendao

import org.greenrobot.greendao.generator.DaoUtil
import javax.lang.model.element.TypeElement

/**
 * <p><b>Package:</b> osp.leobert.android.gdcollector.greendao </p>
 * <p><b>Project:</b> GreenDaoCollector </p>
 * <p><b>Classname:</b> GreenDaoEntity </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2022/1/5.
 */
class GreenDaoEntity(val entityElement: TypeElement, val entityClzName: String, val daoPkg: String) {
    val daoClzName = entityClzName + "Dao"

    val tableName = DaoUtil.dbName(entityClzName)


    val entityName: String = entityElement.qualifiedName.toString()

    val fullDaoPkg = daoPkg.run {
        if (isNotEmpty()) {
            "$this."
        } else ""
    }+daoClzName
}