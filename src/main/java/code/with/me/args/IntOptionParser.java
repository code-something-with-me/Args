package code.with.me.args;

import java.util.List;

class IntOptionParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        Object value;
        int idx = arguments.indexOf(option.value());
        value = Integer.parseInt(arguments.get(idx + 1));
        return value;
    }
}
