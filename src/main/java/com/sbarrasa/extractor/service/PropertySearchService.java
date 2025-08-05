package com.sbarrasa.extractor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PropertySearchService {
    
    private static final Logger log = LoggerFactory.getLogger(PropertySearchService.class);
    
    /**
     * Finds property values in extracted text based on property keys
     * @param propertyKeys list of property keys to search for
     * @param text the text to search in
     * @return a map of property keys and their found values
     */
    public Map<String, Object> findProperties(Set<String> propertyKeys, String text) {
        log.info("Buscando las propiedades {} del producto", propertyKeys);
        
        var result = new HashMap<String, Object>();
        
        // Add the full extracted text as a property
        result.put("extractedText", text);

        // For each property key, try to find a value in the extracted text
        for (String key : propertyKeys) {
            String value = findPropertyValue(key, text);
            if (value != null) {
                result.put(key, value);
                log.debug("Found property '{}' with value '{}'", key, value);
            } else {
                log.debug("Property '{}' not found in text", key);
            }
        }
        
        log.info("Found {} properties out of {} keys", result.size() - 1, propertyKeys.size());
        return result;
    }
    
    /**
     * Simple implementation to find a property value in text
     * This is a basic implementation that looks for patterns like "key: value" or "key = value"
     */
    private String findPropertyValue(String key, String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        
        // Create patterns to match common property formats
        String[] patterns = {
            key + "\\s*:\\s*([^\\n,;]+)",  // key: value
            key + "\\s*=\\s*([^\\n,;]+)",  // key = value
            key + "\\s+-\\s+([^\\n,;]+)"   // key - value
        };
        
        for (String patternStr : patterns) {
            var pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            var matcher = pattern.matcher(text);
            
            if (matcher.find()) {
                return matcher.group(1).trim();
            }
        }
        
        return null;
    }
}