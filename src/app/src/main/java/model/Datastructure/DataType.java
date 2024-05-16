package model.Datastructure;

import androidx.annotation.NonNull;

/**
 * @author Haochen Gong
 * Enumerated classes for stored data types
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
