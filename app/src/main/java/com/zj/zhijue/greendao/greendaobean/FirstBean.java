package com.zj.zhijue.greendao.greendaobean;

import androidx.annotation.Keep;
import android.text.TextUtils;

import com.google.gson.Gson;


import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;

@Entity(nameInDb = "firstbean_tab")
public class FirstBean {

    @Id(autoincrement = true)
    @Property(nameInDb = "localid")
    private Long localid;

    @Property(nameInDb = "testProperty")
    private String testProperty;

    //@Unique
    @Property(nameInDb = "uniqueBean")
    @Convert(converter = UniqueBeanConverter.class, columnType = String.class)
    private UniqueBean uniqueBean;

    @Generated(hash = 1931828558)
    public FirstBean(Long localid, String testProperty, UniqueBean uniqueBean) {
        this.localid = localid;
        this.testProperty = testProperty;
        this.uniqueBean = uniqueBean;
    }

    @Generated(hash = 100291509)
    public FirstBean() {
    }

    public Long getLocalid() {
        return this.localid;
    }

    public void setLocalid(Long localid) {
        this.localid = localid;
    }

    public String getTestProperty() {
        return this.testProperty;
    }

    public void setTestProperty(String testProperty) {
        this.testProperty = testProperty;
    }

    public UniqueBean getUniqueBean() {
        return this.uniqueBean;
    }

    public void setUniqueBean(UniqueBean uniqueBean) {
        this.uniqueBean = uniqueBean;
    }



    public static class UniqueBeanConverter implements PropertyConverter<UniqueBean, String> {
        @Override
        public UniqueBean convertToEntityProperty(String databaseValue) {
            if (TextUtils.isEmpty(databaseValue)) {
                return  null;
            }
            return new Gson().fromJson(databaseValue, UniqueBean.class);
        }

        @Override
        public String convertToDatabaseValue(UniqueBean entityProperty) {
            if (null == entityProperty) {
                return  null;
            }
            return new Gson().toJson(entityProperty);
        }
    }

    @Keep
    public static class UniqueBean {
        private String Name;
        private String age;

        public UniqueBean() {
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "UniqueBean{" +
                    "Name='" + Name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }

}
