package pers.ken.rt.starter.pbac.core;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The interface Dynamic expressions provider.
 *
 * @ClassName: DynamicDataProvider
 * @Created: 2024 /7/23
 * @Author ken
 */
public interface DynamicExpressionsProvider {
    /**
     * The constant VARIABLE_PATTERN.
     */
    Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{(.+?)\\}");

    /**
     * Gets values by variable name.
     *
     * @param variableName the variable name
     * @return the values by variable name
     */
    List<Map<String, String>> getValuesByVariableName(List<String> variableName);

    /**
     * Dynamic parser expressions policy document . statement.
     *
     * @param statement the statement
     * @return the policy document . statement
     */
    default PolicyDocument.Statement dynamicParserExpressions(PolicyDocument.Statement statement) {
        List<String> resources = statement
                .getResources()
                .stream()
                .map(this::parsedExpressions)
                .flatMap(Collection::stream)
                .toList();
        PolicyDocument.Statement parsedStatement = new PolicyDocument.Statement();
        parsedStatement.setActions(statement.getActions());
        parsedStatement.setId(statement.getId());
        parsedStatement.setEffect(statement.getEffect());
        parsedStatement.setResources(resources);
        parsedStatement.setConditions(statement.getConditions());
        return parsedStatement;
    }

    private List<String> parsedExpressions(String expression) {
        List<String> variableNames = extractVariableNames(expression);
        if (CollectionUtils.isEmpty(variableNames)) {
            return Collections.singletonList(expression);
        }
        List<Map<String, String>> contexts = getValuesByVariableName(variableNames);
        ArrayList<String> expressions = Lists.newArrayList();
        for (Map<String, String> context : contexts) {
            String parsedExpression = applyContextsToExpression(expression, context, variableNames);
            expressions.add(parsedExpression);
        }
        return expressions;
    }

    private String applyContextsToExpression(String expression, Map<String, String> context, List<String> variableNames) {
        if (variableNames.isEmpty()) {
            return expression;
        }
        for (String variableName : variableNames) {
            String newExpression = applyValuesToTemplate(expression, variableName, context);
            expression = applyContextsToExpression(newExpression, context, variableNames.subList(1, variableNames.size()));
        }
        return expression;
    }

    private String applyValuesToTemplate(String expression, String variableName, Map<String, String> context) {
        return expression.replaceAll("\\$\\{" + variableName + "\\}", context.get(variableName));
    }

    private static List<String> extractVariableNames(String input) {
        List<String> variables = new ArrayList<>();
        Matcher matcher = VARIABLE_PATTERN.matcher(input);
        while (matcher.find()) {
            // group(1) 第一个括号内的内容
            String variableName = matcher.group(1);
            variables.add(variableName);
        }
        return variables;
    }
}
