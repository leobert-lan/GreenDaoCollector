package osp.leobert.android.gdc.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class JavaDemoEntity2 {
    private String name;

    @Generated(hash = 6134989)
    public JavaDemoEntity2(String name) {
        this.name = name;
    }

    @Generated(hash = 1423272683)
    public JavaDemoEntity2() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
