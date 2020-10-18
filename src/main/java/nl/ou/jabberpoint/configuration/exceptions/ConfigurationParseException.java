package nl.ou.jabberpoint.configuration.exceptions;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * The {@code ConfigurationParseException} is thrown when the values defined in properties file cannot be parsed correctly
 * or when the provided types do not match the ones defined in the confguration class
 */
public class ConfigurationParseException extends Exception {
    /**
     * @param message the exception message
     */
    public ConfigurationParseException(String message) {
        super(message);
    }
}
