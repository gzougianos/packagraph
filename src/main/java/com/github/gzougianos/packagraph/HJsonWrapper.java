package com.github.gzougianos.packagraph;

import org.hjson.JsonArray;
import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.util.HashMap;
import java.util.Map;

final class HJsonWrapper {
    private HJsonWrapper() {
    }

    static String readHJsonWithConstants(String json) {
        JsonObject humanJsonString = JsonValue.readHjson(json).asObject();

        replaceConstants(humanJsonString);
        return humanJsonString.toString();
    }

    private static void replaceConstants(JsonObject humanJsonString) {


        JsonValue constantsNode = humanJsonString.get("constants");
        if (constantsNode == null)
            return;

        HashMap<String, String> constantsMap = readConstants(constantsNode);

        replacePlaceholders(humanJsonString, constantsMap);
    }

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

    private static void replacePlaceholders(JsonValue jsonValue, Map<String, String> constants) {
        if (jsonValue.isObject()) {
            JsonObject obj = jsonValue.asObject();
            for (String key : obj.names()) {
                JsonValue value = obj.get(key);
                if (value.isString()) {
                    var valueTrimmed = value.asString().trim();
                    if (valueTrimmed.startsWith("${") && valueTrimmed.endsWith("}")) {
                        String placeholder = valueTrimmed.substring(2, valueTrimmed.length() - 1);
                        if (constants.get(placeholder) != null) {
                            obj.set(key, constants.get(placeholder));
                        }
                    }
                } else {
                    replacePlaceholders(value, constants);
                }
            }
        } else if (jsonValue.isArray()) {
            for (JsonValue element : jsonValue.asArray()) {
                replacePlaceholders(element, constants);
            }
        }
    }

}
