package code.with.me.args;

import java.util.List;

class BooleanOptionParser implements OptionParser<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        int idx = arguments.indexOf(option.value());
        if (arguments.size() > idx + 1 && !arguments.get(idx + 1).startsWith("-")) {
            throw new TooManyArgumentsException(option);
        }
        return idx != -1;
    }
}
