package code.with.me.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class ArgumentParser {

    @SuppressWarnings("unchecked")
    public <T> T parse(Class<T> optionClass, String... args) {
        try {
            Constructor<?> declaredConstructor = optionClass.getDeclaredConstructors()[0];
            List<String> arguments = List.of(args);

            Object[] values = Arrays.stream(declaredConstructor.getParameters())
                    .map(parameter -> parseOption(arguments, parameter))
                    .toArray();

            return (T) declaredConstructor.newInstance(values);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object parseOption(List<String> arguments, Parameter parameter) {
        return getValue(arguments, parameter.getAnnotation(Option.class));
    }

    private Object getValue(List<String> arguments, Option option) {
        Object value = null;
        if ("-l".equals(option.value())) {
            value = arguments.contains(option.value());
        }

        if ("-p".equals(option.value())) {
            int idx = arguments.indexOf(option.value());
            value = Integer.parseInt(arguments.get(idx + 1));
        }

        if ("-d".equals(option.value())) {
            int idx = arguments.indexOf(option.value());
            value = arguments.get(idx + 1);
        }
        return value;
    }
}
