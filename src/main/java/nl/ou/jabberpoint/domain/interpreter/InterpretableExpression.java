package nl.ou.jabberpoint.domain.interpreter;

/**
 * @author Kasper Cools (coolskasper@gmail.com
 */
interface InterpretableExpression {
    String interpret(String context);
}
