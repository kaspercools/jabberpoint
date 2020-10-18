package nl.ou.jabberpoint.domain.interpreter;

import java.util.regex.Pattern;

/**
 * @author Kasper Cools (coolskasper@gmail.com
 */
public class SlideShowExpression implements InterpretableExpression {

    private final String expression;
    private final ExpressionInterpretation expressionInterpretation;

    public SlideShowExpression(String expression, ExpressionInterpretation expressionInterpretation) {
        this.expressionInterpretation = expressionInterpretation;
        this.expression = expression;
    }

    public String interpret(String context) {
        if (context.contains(expression)) {
            context = context.replaceAll(Pattern.quote(expression), expressionInterpretation.getExpressionInterpretation());
        }

        return context;
    }
}
