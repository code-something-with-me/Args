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
        Option option = parameter.getAnnotation(Option.class);
        Object value = null;
        if (parameter.getType() == boolean.class) {
            value = arguments.contains(option.value());
        }

        if (parameter.getType() == int.class) {
            int idx = arguments.indexOf(option.value());
            value = Integer.parseInt(arguments.get(idx + 1));
        }

        if (parameter.getType() == String.class) {
            int idx = arguments.indexOf(option.value());
            value = arguments.get(idx + 1);
        }
        return value;
    }
}
