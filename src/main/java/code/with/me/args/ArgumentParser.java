package code.with.me.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ArgumentParser {
    private static final Map<Class<?>, OptionParser<?>> PARSERS = Map.of(
            boolean.class, OptionParsers.bool(),
            int.class, OptionParsers.unary(Integer::parseInt, 0),
            String.class, OptionParsers.unary(String::valueOf, ""),
            String[].class, OptionParsers.list(String[]::new, String::valueOf),
            Integer[].class, OptionParsers.list(Integer[]::new, Integer::parseInt),
            int[].class, OptionParsers.list(Integer[]::new, Integer::parseInt)
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
        Option option = parameter.getAnnotation(Option.class);
        if (option == null) {
            throw new IllegalOptionException(parameter.getName());
        }
        if (!PARSERS.containsKey(parameter.getType())) {
            throw new UnsupportedOptionTypeException(option, parameter.getType());
        }
        return PARSERS.get(parameter.getType()).parse(arguments, option);
    }


}


