/******************************************************************************
 Copyright 2018 MyMiller Consulting LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License.  You may obtain a copy
 of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package name.mymiller.lang.reflect;

import name.mymiller.utils.ObjectUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Collection of Utility functions around Reflection to assist with creating,
 * setting and getting values on an object.
 *
 * @author jmiller
 */
public class ReflectionUtil {
    /**
     * Get the value on a field of a class. The field must have an accessor.
     *
     * @param object    Object containing the field to set.
     * @param fieldName String containing the name of the field to set.
     * @throws IntrospectionException    Unable to identify the proper method.
     * @throws IllegalAccessException    Lack the security access to set the field.
     * @throws IllegalArgumentException  Unable to find the field indicated.
     * @throws InvocationTargetException Object passed in the invocation is not of
     *                                   the right type.
     */
    static public Object getField(Object object, String fieldName)
            throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return ReflectionUtil.getPropertyDescriptor(object, fieldName).getReadMethod().invoke(object);
    }

    /**
     * Get the value on a field of a class. The field must have an accessor.
     *
     * @param object    Object containing the field to set.
     * @param fieldName String containing the name of the field to set.
     * @throws IntrospectionException Unable to identify the proper method.
     */
    static private PropertyDescriptor getPropertyDescriptor(Object object, String fieldName)
            throws IntrospectionException {
        return new PropertyDescriptor(fieldName, object.getClass());
    }

    /**
     * Instantiate a class via it's name. Must have a default constructor otherwise
     * it will fail.
     *
     * @param className Full package name plus Class Name for the Class to
     *                  instantiate.
     * @return Object of the type Class
     * @throws InstantiationException Failed to instantiate the class, most often
     *                                the class is missing the default constructor.
     * @throws IllegalAccessException Lack the security access to instantiate that
     *                                class.
     * @throws ClassNotFoundException Class not found.
     */
    static public Object instantiateClass(String className) throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        return Class.forName(className).getDeclaredConstructor().newInstance();
    }

    /**
     * Assign a value to all objects in a list, given the setter
     *
     * @param <T>    Type of Objects in list
     * @param <R>    Value Type to set on Objects
     * @param value  Value to set on Objects
     * @param list   List of objects to set value.
     * @param setter Reference to the Setter
     */
    static public <T, R> void reassignToValue(R value, List<T> list, BiConsumer<T, R> setter) {
        if ((list != null) && (value != null)) {
            list.forEach(item -> setter.accept(item, value));
        }
    }

    /**
     * Assign value between two objects of the same tiem by using the getter and
     * setter
     *
     * @param <T>    Type of Ojects
     * @param <R>    Value type to set on the objects
     * @param source Source to retreive with the Getter.
     * @param target Target to set with the Setter
     * @param getter Reference to the Getter
     * @param setter Reference to the setter
     */
    static public <T, R> void setElements(T source, T target, Function<? super T, ? extends R> getter,
                                          BiConsumer<T, R> setter) {
        final R A = getter.apply(source);
        final R B = getter.apply(target);

        if ((A != null) && (B == null)) {
            setter.accept(target, A);
        }
    }

    /**
     * Set the value on a field of a class. The field must have an mutator.
     *
     * @param object    Object containing the field to set.
     * @param fieldName String containing the name of the field to set.
     * @param value     Value to set the field to.
     * @throws IntrospectionException    Unable to identify the proper method.
     * @throws IllegalAccessException    Lack the security access to set the field.
     * @throws IllegalArgumentException  Unable to find the field indicated.
     * @throws InvocationTargetException Object passed in the invocation is not of
     *                                   the right type.
     */
    static public void setField(Object object, String fieldName, Object value)
            throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ReflectionUtil.getPropertyDescriptor(object, fieldName).getWriteMethod().invoke(object, value);
    }

    /**
     * Method to determine if two elements have equals valutes
     *
     * @param <T>     Type of Objects
     * @param <R>     Value type on objects
     * @param targetA First element
     * @param targetB Second element
     * @return boolean indicating if they are equals.
     */
    static public <T, R> boolean validateElements(T targetA, T targetB, Function<? super T, ? extends R> getter) {
        final R A = getter.apply(targetA);
        final R B = getter.apply(targetB);

        if ((A == null) || (B == null)) {
            return false;
        }

        // Return true if they are set but not equal
        return !A.equals(B);
    }

    /**
     * Performs a check between two lists to determine if the elements are equal
     * with a single check.
     *
     * @param <T>        Type of Objects
     * @param <R>        Value type on objects
     * @param source     List to check against
     * @param target     List to check
     * @param firstCheck Value to to compare on the equals
     * @param first      Getter to check the value.
     * @return boolean indicating value.
     */
    static public <T, R> boolean validateLists(List<T> source, List<T> target, boolean firstCheck,
                                               Function<? super T, ? extends R> first) {
        return ReflectionUtil.validateLists(source, target, firstCheck, first, false, null);
    }

    /**
     * Performs a check between two lists to determine if the elements are equal
     * with a double check.
     *
     * @param <T>         Type of Objects
     * @param <R>         Value type on objects
     * @param source      List to check against
     * @param target      List to check
     * @param firstCheck  Value to to compare on the equals
     * @param first       Getter to check the value.
     * @param secondCheck Value to compare on the equals for the second check.
     * @param second      Getter to check on the second value
     * @return boolean indicating value.
     */
    static public <T, R> boolean validateLists(List<T> source, List<T> target, boolean firstCheck,
                                               Function<? super T, ? extends R> first, boolean secondCheck, Function<? super T, ? extends R> second) {
        if (ObjectUtils.isNotNullOrEmpty(source, target)) {
            for (final T sItem : source) {
                for (final T tItem : target) {
                    if (first.apply(sItem).equals(first.apply(tItem)) == firstCheck) {
                        if (second != null) {
                            if (second.apply(sItem).equals(second.apply(tItem)) == secondCheck) {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
