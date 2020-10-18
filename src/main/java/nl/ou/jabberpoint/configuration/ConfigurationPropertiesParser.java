package nl.ou.jabberpoint.configuration;

import nl.ou.jabberpoint.configuration.annotations.ConfigField;
import nl.ou.jabberpoint.configuration.annotations.Configuration;
import nl.ou.jabberpoint.configuration.exceptions.ConfigurationParseException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.Properties;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * Is responsible for parsing all variables form the .properties file
 */
class ConfigurationPropertiesParser<T> implements ConfigurationParser<T> {

    /**
     * Uses reflection to analyse the given object and tries to set the properties on which {@code Configuration}
     * and {@code ConfigField} annotations are found.
     * related to {@code Configuration} and {@code ConfigField}
     *
     * @param object that needs to be analysed
     * @return the object that was provided
     * @throws ConfigurationParseException when configuration file can not be read or a value can not be parsed
     */
    public T parse(T object) throws ConfigurationParseException {
        if (Optional.ofNullable(object).isEmpty()) throw new NullPointerException();

        Class<?> objectClass = object.getClass();
        if (objectClass.isAnnotationPresent(Configuration.class)) {
            Configuration c = objectClass.getAnnotation(Configuration.class);
            InputStream appFile = getClass().getResourceAsStream(String.format("/%s", c.value()));
            Properties propsConfig = new Properties();

            try {
                propsConfig.load(appFile);
                loadValues(objectClass, object, propsConfig);
            } catch (IllegalAccessException | IOException e) {
                throw new ConfigurationParseException(e.getMessage());
            }
        }

        return object;
    }

    /**
     * @param propertyValue the value to be converted
     * @param type          the type to which the values need to be converted
     * @return the converted value
     */
    private Object convertTo(String propertyValue, Type type) {
        Object newValue = null;
        if (type == boolean.class) {
            newValue = Boolean.valueOf(propertyValue);
        } else if (type == String.class) {
            newValue = String.valueOf(propertyValue);
        }

        return newValue;
    }

    /**
     * loadValues tries to find the field references found in the object through the {@code ConfigField}
     * and {@code Configuration} annotations and set the related properties in the object param
     *
     * @param objectClass the class on which properties need to be set
     * @param object      the object on which properties need to be set
     * @param propsConfig the properties in which the values reside
     * @throws IllegalAccessException when a property is not accessible
     */
    private void loadValues(Class<?> objectClass, T object, Properties propsConfig) throws IllegalAccessException {
        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ConfigField.class)) {
                String fieldPropertyName = field.getAnnotation(ConfigField.class).value();

                String propertyValue = propsConfig.getProperty(fieldPropertyName);
                field.set(object, convertTo(propertyValue, field.getType()));
            }
        }
    }
}
