package com.github.gzougianos.packagraph2;

import java.util.*;
import java.util.regex.*;

import static java.util.Collections.unmodifiableMap;

public class Style {
    private final Map<String, String> values;


    Style(String style, List<Options.DefineConstant> constants) {
        if (isBlank(style)) {
            this.values = Collections.emptyMap();
        } else {
            values = resolve(style, constants);
        }
    }

    public Map<String, String> values() {
        return values;
    }

    private Map<String, String> resolve(String style, List<Options.DefineConstant> constants) {
        Map<String, String> result = new HashMap<>();
        String[] pairs = style.split(";");
        for (String pair : pairs) {
            pair = pair.trim();
            if (pair.isEmpty())
                continue;

            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = resolveConstants(keyValue[1].trim(), constants);
                result.put(key, value);
            }
        }
        return unmodifiableMap(result);
    }

    private String resolveConstants(String value, List<Options.DefineConstant> constants) {
        Pattern pattern = Pattern.compile("\\$\\{(\\w+)}");
        Matcher matcher = pattern.matcher(value);
        StringBuffer resolved = new StringBuffer();

        while (matcher.find()) {
            String constantName = matcher.group(1);
            String resolvedValue = findConstantValue(constantName, constants);
            matcher.appendReplacement(resolved, Matcher.quoteReplacement(resolvedValue));
        }
        matcher.appendTail(resolved);
        return resolved.toString();
    }

    private String findConstantValue(String constantName, List<Options.DefineConstant> constants) {
        for (Options.DefineConstant constant : constants) {
            if (constant.name().equals(constantName)) {
                return constant.value();
            }
        }
        return constantName;
    }

    private boolean isBlank(String style) {
        return style == null || style.trim().isEmpty();
    }

}
