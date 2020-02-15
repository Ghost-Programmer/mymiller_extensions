package name.mymiller.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ModelManager {

    private final Function<String, String> classResolver;

    private final Function<String, String> daoResolver;

    private final Function<String, String> daoFindResolver;

    private final Function<String, String> daoSaveResolver;

    public ModelManager(Function<String, String> classResolver, Function<String, String> daoResolver,
                        Function<String, String> daoFindResolver, Function<String, String> daoSaveResolver) {
        super();
        this.classResolver = classResolver;
        this.daoResolver = daoResolver;
        this.daoFindResolver = daoFindResolver;
        this.daoSaveResolver = daoSaveResolver;
    }

    private String getDaoName(String className) {
        return this.daoResolver.apply(className);
    }

    public String getField(String className, Long id, String field)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IntrospectionException, JsonProcessingException {
        final Object data = this.getRow(className, id);

        if (data != null) {
            final Object value = new PropertyDescriptor(field, data.getClass()).getReadMethod().invoke(data);

            final ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(value);
        }
        return null;
    }

    private String getFindMethod(String className) {
        return this.daoFindResolver.apply(className);
    }

    private String getFullClassName(String className) {
        return this.classResolver.apply(className);
    }

    private Optional<Method> getMethodByName(Class<?> cls, String methodName) {
        final List<Method> methods = Arrays.asList(cls.getMethods());

        final Optional<Method> findFirst = methods.stream().filter(method -> method.getName().equals(methodName))
                .findFirst();

        return findFirst;
    }

    private Object getRow(String className, Long id) throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        final String daoClassName = this.getDaoName(className);
        final Object dao = Class.forName(daoClassName).getDeclaredConstructor().newInstance();

        final Optional<Method> findMethod = this.getMethodByName(dao.getClass(), this.getFindMethod(className));

        if (findMethod.isPresent()) {
            return findMethod.get().invoke(dao, id);
        }

        return null;
    }

    private String getSaveMethod(String className) {
        return this.daoSaveResolver.apply(className);
    }

    public String setField(String className, Long id, String field, String jsonValue) throws InstantiationException,
            IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            IllegalArgumentException, IntrospectionException, IOException {

        final Object data = this.getRow(className, id);
        if (data != null) {
            final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field, data.getClass());

            final Class<?> parameterType = propertyDescriptor.getWriteMethod().getParameterTypes()[0];

            final ObjectMapper mapper = new ObjectMapper();

            final Object obj = mapper.readValue(jsonValue, parameterType);

            propertyDescriptor.getWriteMethod().invoke(data, obj);

            return jsonValue;
        }
        return null;
    }
}
