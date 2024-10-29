package com.github.gzougianos.packagraph;

import org.hjson.JsonArray;
import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class for reading and parsing HJSON files with support for dynamic constant replacement.
 * <p>
 * Allows defining constants in HJSON files which can be referenced by placeholders throughout the file.
 * Placeholders in the format of `${CONSTANT_NAME}` will be replaced by the corresponding value of the constant.
 */
final class HJsonWrapper {
    private HJsonWrapper() {
    }

    /**
     * Reads and parses an HJSON string, replacing any placeholders with defined constants.
     *
     * @param json the HJSON string with potential constants and placeholders
     * @return the HJSON string with all placeholders replaced by their corresponding constant values
     */
    static String readHJsonWithConstants(String json) {
        JsonObject humanJsonString = JsonValue.readHjson(json).asObject();

        replaceConstants(humanJsonString);
        return humanJsonString.toString();
    }

    /**
     * Searches for a "constants" object within the HJSON structure and replaces any placeholders in the
     * HJSON with their corresponding constant values.
     *
     * @param humanJsonString the HJSON root object to process for constants
     */
    private static void replaceConstants(JsonObject humanJsonString) {
        JsonValue constantsNode = humanJsonString.get("constants");
        if (constantsNode == null)
            return;

        HashMap<String, String> constantsMap = readConstants(constantsNode);

        replacePlaceholders(humanJsonString, constantsMap);
    }

    /**
     * Reads constant definitions from a "constants" JSON node and stores them in a map for easy reference.
     *
     * @param constantsNode the JSON node containing an array of constants
     * @return a map where each key-value pair represents a constant name and its associated value
     */
    private static HashMap<String, String> readConstants(JsonValue constantsNode) {
        HashMap<String, String> constantsMap = new HashMap<>();
        JsonArray constants = constantsNode.asArray();
        for (JsonValue constant : constants) {
            JsonObject constantObj = constant.asObject();
            String name = constantObj.getString("name", "").trim();
            String value = constantObj.getString("value", "").trim();
            constantsMap.put(name, value);
        }
        return constantsMap;
    }

    /**
     * Recursively traverses a JSON object or array, replacing any placeholders that match the constant
     * names in the provided map.
     *
     * @param jsonValue the JSON object or array to process
     * @param constants a map of constants to be replaced, where each key represents a placeholder name
     */
    private static void replacePlaceholders(JsonValue jsonValue, Map<String, String> constants) {
        if (jsonValue.isObject()) {
            JsonObject obj = jsonValue.asObject();
            for (String key : obj.names()) {
                JsonValue value = obj.get(key);
                if (value.isString()) {
                    String updatedValue = replacePlaceholdersInString(value.asString(), constants);
                    obj.set(key, updatedValue);
                } else {
                    replacePlaceholders(value, constants);
                }
            }
        } else if (jsonValue.isArray()) {
            JsonArray array = jsonValue.asArray();
            for (int i = 0; i < array.size(); i++) {
                JsonValue element = array.get(i);
                if (element.isString()) {
                    String updatedValue = replacePlaceholdersInString(element.asString(), constants);
                    array.set(i, updatedValue);
                } else {
                    replacePlaceholders(element, constants);
                }
            }
        }
    }

    /**
     * Replaces placeholders in a string with their values from the constants map.
     * Supports multiple placeholders within a single string.
     *
     * @param value     the string potentially containing placeholders in the format ${PLACEHOLDER}
     * @param constants a map of constants to be replaced, where each key represents a placeholder name
     * @return the string with placeholders replaced by their corresponding constant values
     */
    private static String replacePlaceholdersInString(String value, Map<String, String> constants) {
        Pattern pattern = Pattern.compile("\\$\\{(\\w+)}"); // Match placeholders like ${NAME}
        Matcher matcher = pattern.matcher(value);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String placeholder = matcher.group(1); // Extract the placeholder name
            String replacement = constants.getOrDefault(placeholder, matcher.group(0)); // Replace if constant exists
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
