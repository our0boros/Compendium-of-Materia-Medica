package model;

import androidx.annotation.NonNull;

/**
 * @author: Haochen Gong
 * @description: 储存的数据类型的枚举类
 **/
public enum DataType {
    POST,
    USER,
    PLANT;

    @NonNull
    public String toString(){
        return name();
    }
}
