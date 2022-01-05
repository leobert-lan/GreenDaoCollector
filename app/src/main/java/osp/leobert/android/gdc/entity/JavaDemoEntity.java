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
public class JavaDemoEntity {
    private String name;

    @Generated(hash = 1789041840)
    public JavaDemoEntity(String name) {
        this.name = name;
    }

    @Generated(hash = 1500590374)
    public JavaDemoEntity() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
