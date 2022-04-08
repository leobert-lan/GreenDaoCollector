package osp.leobert.android.greendao

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.greenrobot.greendao.gradle.Greendao3GradlePlugin

/**
 * <p><b>Package:</b> osp.leobert.android.greendao </p>
 * <p><b>Classname:</b> GreenDaoPluginWrapper </p>
 * Created by leobert on 2022/4/7.
 */
class GreenDaoPluginWrapper : Plugin<Project> {
    private val wrapper: Plugin<Project> = Greendao3GradlePlugin()
    override fun apply(project: Project) {
        wrapper.apply(project)
    }
}