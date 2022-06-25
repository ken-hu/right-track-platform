package pers.ken.rt.common.iam.internal;

import com.fasterxml.jackson.databind.JsonNode;
import pers.ken.rt.common.utils.Jackson;
import pers.ken.rt.common.iam.Action;
import pers.ken.rt.common.iam.Policy;
import pers.ken.rt.common.iam.Resource;
import pers.ken.rt.common.iam.Statement;

import java.util.LinkedList;
import java.util.List;

/**
 * <code> JsonPolicyReader </code>
 * <desc> JsonPolicyReader </desc>
 * <b>Creation Time:</b> 2022/1/24 22:30.
 *
 * @author _Ken.Hu
 */
public class JsonPolicyReader {

    public Policy createPolicyFromJsonString(String jsonString) {
        if (jsonString == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }

        JsonNode policyNode;
        JsonNode idNode;
        JsonNode statementsNode;
        Policy policy = new Policy();
        List<Statement> statements = new LinkedList<>();

        try {
            policyNode = Jackson.jsonNodeOf(jsonString);

            idNode = policyNode.get(PolicyDocumentFields.POLICY_ID);
            if (isNotNull(idNode)) {
                policy.setId(idNode.asText());
            }

            statementsNode = policyNode.get(PolicyDocumentFields.STATEMENT);
            if (isNotNull(statementsNode)) {
                if (statementsNode.isObject()) {
                    statements.add(statementOf(statementsNode));
                } else if (statementsNode.isArray()) {
                    for (JsonNode statementNode : statementsNode) {
                        statements.add(statementOf(statementNode));
                    }
                }
            }

            JsonNode jsonNode = policyNode.get(PolicyDocumentFields.VERSION);
            if (isNotNull(jsonNode)) {
                policy.setVersion(jsonNode.asText());
            }

        } catch (Exception e) {
            String message = "Unable to generate policy object from JSON string "
                    + e.getMessage();
            throw new IllegalArgumentException(message, e);
        }
        policy.setStatements(statements);
        return policy;
    }

    private Statement statementOf(JsonNode jStatement) {

        JsonNode effectNode = jStatement.get(PolicyDocumentFields.STATEMENT_EFFECT);

        final Statement.Effect effect = isNotNull(effectNode)
                ? Statement.Effect.valueOf(effectNode.asText())
                : Statement.Effect.Deny;

        Statement statement = new Statement(effect);

        JsonNode id = jStatement.get(PolicyDocumentFields.STATEMENT_ID);
        if (isNotNull(id)) {
            statement.setId(id.asText());
        }

        JsonNode actionNodes = jStatement.get(PolicyDocumentFields.ACTION);
        if (isNotNull(actionNodes)) {
            statement.setActions(actionsOf(actionNodes));
        }

        List<Resource> resources = new LinkedList<>();
        JsonNode resourceNodes = jStatement.get(PolicyDocumentFields.RESOURCE);
        if (isNotNull(resourceNodes)) {
            resources.addAll(resourcesOf(resourceNodes));
        }

        if (!resources.isEmpty()) {
            statement.setResources(resources);
        }

        return statement;
    }

    private List<Action> actionsOf(JsonNode actionNodes) {
        List<Action> actions = new LinkedList<>();

        if (actionNodes.isArray()) {
            for (JsonNode action : actionNodes) {
                actions.add(new NamedAction(action.asText()));
            }
        } else {
            actions.add(new NamedAction(actionNodes.asText()));
        }
        return actions;
    }


    private List<Resource> resourcesOf(JsonNode resourceNodes) {
        List<Resource> resources = new LinkedList<>();

        if (resourceNodes.isArray()) {
            for (JsonNode resource : resourceNodes) {
                resources.add(new Resource(resource.asText()));
            }
        } else {
            resources.add(new Resource(resourceNodes.asText()));
        }
        return resources;
    }


    private static class NamedAction implements Action {

        private final String actionName;

        public NamedAction(String actionName) {
            this.actionName = actionName;
        }

        @Override
        public String getActionName() {
            return actionName;
        }

    }

    private boolean isNotNull(Object object) {
        return null != object;
    }
}
