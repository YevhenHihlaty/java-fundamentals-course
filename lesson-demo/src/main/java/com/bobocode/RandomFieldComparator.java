package com.bobocode;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * A generic comparator that is comparing a random field of the given class. The field is either primitive or
 * {@link Comparable}. It is chosen during comparator instance creation and is used for all comparisons.
 * <p>
 * If no field is available to compare, the constructor throws {@link IllegalArgumentException}
 *
 * @param <T> the type of the objects that may be compared by this comparator
 */
public class RandomFieldComparator<T> implements Comparator<T> {

    Class<T> targetType;

    Field comparableField;

    public RandomFieldComparator(Class<T> targetType) {
        this.targetType = targetType;
        setComparableField();
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value grater than a non-null value.
     *
     * @param o1
     * @param o2
     * @return
     */
    @SneakyThrows
    @Override
    public int compare(T o1, T o2) {
        Comparable fieldO1 = (Comparable)comparableField.get(o1);
        Comparable fieldO2 = (Comparable)comparableField.get(o1);

        return fieldO1.compareTo(fieldO2);
    }


    private void setComparableField(){
        Field[] fields = Arrays
                .stream(targetType.getDeclaredFields())
                .filter(field -> field.getType().isPrimitive() || Comparable.class.isAssignableFrom(field.getType()))
                .toArray(Field[]::new);
        comparableField = fields[new Random().nextInt(fields.length)];
        comparableField.setAccessible(true);

    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        return String.format("Random field comparator of class '%s' is comparing '%s'", targetType.getSimpleName(), comparableField.getName());
    }
}