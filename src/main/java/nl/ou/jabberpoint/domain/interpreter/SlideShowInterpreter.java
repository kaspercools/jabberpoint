package nl.ou.jabberpoint.domain.interpreter;

import java.util.LinkedList;

/**
 * @author Kasper Cools (coolskasper@gmail.com
 */
public class SlideShowInterpreter implements InterpretableExpression {
    private final LinkedList<SlideShowExpression> slideShowExpressionList;

    public SlideShowInterpreter(LinkedList<SlideShowExpression> slideShowExpressions) {
        this.slideShowExpressionList = slideShowExpressions;
    }

    @Override
    public String interpret(String context) {
        for (SlideShowExpression expression : slideShowExpressionList) {
            context = expression.interpret(context);
        }
        return context;
    }
}

