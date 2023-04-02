package code.with.me.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ArgumentParser {
    private static final Map<Class<?>, OptionParser<?>> PARSERS = Map.of(
            boolean.class, new BooleanOptionParser(),
            int.class, new SingleValueParser<>(0, Integer::parseInt),
            String.class, new SingleValueParser<>("", String::valueOf)
    );


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
        return PARSERS.get(parameter.getType())
                .parse(arguments, parameter.getAnnotation(Option.class));
    }


}


