package model;

import androidx.annotation.NonNull;

public enum DataType {
    POST,
    USER,
    PLANT;

    @NonNull
    public String toString(){
        return name();
    }
}
