package code.with.me.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;

public class ArgumentParser {
    public <T> T parse(Class<T> optionClass, String... args) {
        Constructor<?> declaredConstructor = optionClass.getDeclaredConstructors()[0];
        Parameter parameter = declaredConstructor.getParameters()[0];
        Option option = parameter.getAnnotation(Option.class);
        try {
            boolean contains = List.of(args).contains(option.value());
            return (T) declaredConstructor.newInstance(contains);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
