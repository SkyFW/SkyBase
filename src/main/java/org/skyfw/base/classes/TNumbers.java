package org.skyfw.base.classes;

import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.exception.general.TIllegalArgumentMCode;

public class TNumbers {

    private TNumbers() {
    }

    public static boolean isDecimal(Number number) throws TIllegalArgumentException{

        Class clazz= number.getClass();

        if ( ! Number.class.isAssignableFrom(clazz))
            throw new TIllegalArgumentException(TIllegalArgumentMCode.ARGUMENT_MUST_BE_NUMERIC, "number");

        if ( (clazz.equals(Float.class)) || (clazz.equals(Double.class)) )
            return true;
        else
            return false;
    }



    // >>> double is represented in 64 bits
    // with 1 sign bit, 11 bits of exponent, and 52 bits of significand.

    // >>> float is represented in 32 bits, with 1 sign bit, 8 bits of exponent,
    // and 23 bits of the significand
    // (or what follows from a scientific-notation number: 2.33728*1012; 33728 is the significand).

    public static <T extends Number> T create(Object object, Class<T> clazz) throws TIllegalArgumentException {

        if ( ! Number.class.isAssignableFrom(clazz))
            throw new TIllegalArgumentException(TIllegalArgumentMCode.ARGUMENT_MUST_BE_A_NUMERIC_CLASS, "clazz");

        if (object.getClass().equals(String.class)){
            try {
                if (((String) object).indexOf(".") > 0)
                    object= new Double((String) object);
                else
                    object= new Long((String) object);
            } catch (Throwable e){
                throw new TIllegalArgumentException(TIllegalArgumentMCode.STRING_ARGUMENT_MUST_BE_CONVERTABLE_TO_NUMERIC, "object");
            }
        }

        if (isDecimal((Number) object)){

            double value= ((Number) object).doubleValue();

            if (clazz.equals(Double.class)) {
                return (T) new Double(value);
            }
            else if (clazz.equals(Float.class)) {
                return (T) new Float(value);
            }
            else if (clazz.equals(Long.class)) {
                return (T) new Long((long)value);
            }
            else if (clazz.equals(Integer.class)) {
                return (T) new Integer((int)value);
            }
            else if (clazz.equals(Byte.class)){
                return (T) new Byte((byte)value);
            }

        } else {

            long value= ((Number) object).longValue();

            if (clazz.equals(Double.class)) {
                return (T) new Double(value);
            }
            else if (clazz.equals(Float.class)) {
                return (T) new Float(value);
            }
            else if (clazz.equals(Long.class)) {
                return (T) new Long(value);
            }
            else if (clazz.equals(Integer.class)) {
                return (T) new Integer((int)value);
            }
            else if (clazz.equals(Byte.class)){
                return (T) new Byte((byte)value);
            }
        }

        throw new TIllegalArgumentException(TIllegalArgumentMCode.ARGUMENT_MUST_BE_NUMERIC, "object");
    }

}
