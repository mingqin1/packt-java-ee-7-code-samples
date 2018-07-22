/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.wflydevelopment.chapter5.util;

import com.packtpub.wflydevelopment.chapter5.entity.Seat;
import com.packtpub.wflydevelopment.chapter5.entity.SeatType;

/**
 *
 * @author Ming
 */
public enum DataType {
    SEAT("Seat", Seat.class),
    SEATTYPE("SeatType", SeatType.class);
   
    private final String typeDesc;
    private final Class type;

    DataType(String typeDesc, Class type) {
        this.typeDesc = typeDesc;
        this.type = type;
    }

    public static DataType getEnumByVal(String code) {
        for ( DataType dt: DataType.values()){
            if ( code.equals(dt.typeDesc)){
                return dt;
            }        
        }
        //TODO: Invalid data type exception type should be thrown
        return null;
    }

    public Class<?> getClassByType() {
        Class<?> retVal = String.class;
        switch (this) {
            case SEAT: {
                retVal = Seat.class;
                break;
            }
            case SEATTYPE: {
                retVal = SeatType.class;
                break;
            }
          
        }
        return retVal;
    }
}
