package com.bsteele.bsteeleMusicApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonDiff {

    public static String diff(final String json1, final String json2) {
        if ( json1 == null || json2 == null ){
            return json2;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode rootNode1 = objectMapper.readTree(json1);
            if ( rootNode1 == null ) return json2;
            final JsonNode rootNode2 = objectMapper.readTree(json2);
            if ( rootNode2 == null ) return json2;
            //System.out.println("rootNode1: "+rootNode1);

            //  compare top level fields only, as JSON strings
            final Set<Map.Entry<String, JsonNode>> changedFields = new HashSet<>();
            final Iterator<Map.Entry<String, JsonNode>> iter = rootNode2.fields();
            while (iter.hasNext()) {
                final Map.Entry<String, JsonNode> field2 = iter.next();
                final String key = field2.getKey();
                //                System.out.println("key: " + key);
                final JsonNode value1 = rootNode1.findValue(key);
                final JsonNode value2 = rootNode2.findValue(key);
                if ( value1 == null || value2.toString().compareTo(rootNode1.findValue(key).toString()) != 0) {
                    //System.out.println("   \"" + key + "\": " + value2);
                    changedFields.add(field2);
                }
            }

            boolean first = true;
            final StringBuilder sb = new StringBuilder("{\n");
            for (final Map.Entry<String, JsonNode> field : changedFields) {
                if (first) {
                    first = false;
                } else {
                    sb.append(",\n");
                }
                sb.append("\"").append(field.getKey()).append("\": ").append(field.getValue());
            }
            sb.append("\n}");

            if (sb.length() > 200) {
                //  give up on a "adjustment" that is too long (and complex)
                return json2;
            }
            return sb.toString();
        } catch (final JsonProcessingException e) {
            return json2;
        }
    }
}
