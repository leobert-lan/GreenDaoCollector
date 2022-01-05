package osp.leobert.android.gdc.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p><b>Package:</b> osp.leobert.android.gdc.entity </p>
 * <p><b>Project:</b> GreenDaoCollector </p>
 * <p><b>Classname:</b> JavaDemoEntity </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2022/1/5.
 */
@Entity
public class JavaDemoEntityTemp {
    private String name;

    @Generated(hash = 1747827265)
    public JavaDemoEntityTemp(String name) {
        this.name = name;
    }

    @Generated(hash = 1853879543)
    public JavaDemoEntityTemp() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
