package nl.ou.jabberpoint.configuration;

import nl.ou.jabberpoint.configuration.exceptions.ConfigurationParseException;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
interface ConfigurationParser<T> {
    T parse(T object) throws ConfigurationParseException;
}
