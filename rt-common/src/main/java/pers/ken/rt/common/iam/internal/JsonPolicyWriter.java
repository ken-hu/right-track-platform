package pers.ken.rt.common.iam.internal;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import pers.ken.rt.common.utils.Jackson;
import pers.ken.rt.common.iam.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * <code> JsonPolicyWriter </code>
 * <desc> JsonPolicyWriter </desc>
 * <b>Creation Time:</b> 2022/1/24 23:01.
 *
 * @author _Ken.Hu
 */
public class JsonPolicyWriter {

    private JsonGenerator generator = null;

    private final Writer writer;


    /**
     * Constructs a new instance of JSONPolicyWriter.
     */
    public JsonPolicyWriter() {
        writer = new StringWriter();
        try {
            generator = Jackson.jsonGeneratorOf(writer);
        } catch (IOException ioe) {
            throw new IllegalArgumentException(
                    "Unable to instantiate JsonGenerator.", ioe);
        }

    }


    public String writePolicyToString(Policy policy) {

        if (!isNotNull(policy)) {
            throw new IllegalArgumentException("Policy cannot be null");
        }

        try {
            return jsonStringOf(policy);
        } catch (Exception e) {
            String message = "Unable to serialize policy to JSON string: "
                    + e.getMessage();
            throw new IllegalArgumentException(message, e);
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
    }


    private String jsonStringOf(Policy policy) throws JsonGenerationException,
            IOException {
        generator.writeStartObject();

        writeJsonKeyValue(PolicyDocumentFields.VERSION, policy.getVersion());

        if (isNotNull(policy.getId())) {
            writeJsonKeyValue(PolicyDocumentFields.POLICY_ID, policy.getId());
        }

        writeJsonArrayStart(PolicyDocumentFields.STATEMENT);

        for (Statement statement : policy.getStatements()) {
            generator.writeStartObject();

            if (isNotNull(statement.getId())) {
                writeJsonKeyValue(PolicyDocumentFields.STATEMENT_ID, statement.getId());
            }
            writeJsonKeyValue(PolicyDocumentFields.STATEMENT_EFFECT, statement
                    .getEffect().toString());

            List<Action> actions = statement.getActions();
            if (isNotNull(actions) && !actions.isEmpty()) {
                writeActions(actions);
            }

            List<Resource> resources = statement.getResources();
            if (isNotNull(resources) && !resources.isEmpty()) {
                writeResources(resources);
            }

            List<Condition> conditions = statement.getConditions();
            if (isNotNull(conditions) && !conditions.isEmpty()) {
                writeConditions(conditions);
            }

            generator.writeEndObject();
        }

        writeJsonArrayEnd();

        generator.writeEndObject();

        generator.flush();

        return writer.toString();

    }

    /**
     * Writes the list of conditions to the JSONGenerator.
     *
     * @param conditions the conditions to be written.
     */
    private void writeConditions(List<Condition> conditions)
            throws JsonGenerationException, IOException {
        Map<String, ConditionsByKey> conditionsByType = groupConditionsByTypeAndKey(conditions);

        writeJsonObjectStart(PolicyDocumentFields.CONDITION);

        ConditionsByKey conditionsByKey;
        for (Map.Entry<String, ConditionsByKey> entry : conditionsByType
                .entrySet()) {
            conditionsByKey = conditionsByType.get(entry.getKey());

            writeJsonObjectStart(entry.getKey());
            for (String key : conditionsByKey.keySet()) {
                writeJsonArray(key, conditionsByKey.getConditionsByKey(key));
            }
            writeJsonObjectEnd();
        }
        writeJsonObjectEnd();
    }

    /**
     * Writes the list of <code>Resource</code>s to the JSONGenerator.
     *
     * @param resources the list of resources to be written.
     */
    private void writeResources(List<Resource> resources)
            throws JsonGenerationException, IOException {

        List<String> resourceStrings = new ArrayList<String>();

        for (Resource resource : resources) {
            resourceStrings.add(resource.getResource());
        }

        // all resources are validated to be of the same type, so it is safe to take the type of the first one
        writeJsonArray(PolicyDocumentFields.RESOURCE, resourceStrings);
    }

    /**
     * Writes the list of <code>Action</code>s to the JSONGenerator.
     *
     * @param actions the list of the actions to be written.
     */
    private void writeActions(List<Action> actions)
            throws JsonGenerationException, IOException {
        List<String> actionStrings = new ArrayList<String>();

        for (Action action : actions) {
            actionStrings.add(action.getActionName());
        }
        writeJsonArray(PolicyDocumentFields.ACTION, actionStrings);
    }

    /**
     * Inner class to hold condition values for each key under a condition type.
     */
    static class ConditionsByKey {
        private Map<String, List<String>> conditionsByKey;

        public ConditionsByKey() {
            conditionsByKey = new LinkedHashMap<String, List<String>>();
        }

        public Map<String, List<String>> getConditionsByKey() {
            return conditionsByKey;
        }

        public void setConditionsByKey(Map<String, List<String>> conditionsByKey) {
            this.conditionsByKey = conditionsByKey;
        }

        public boolean containsKey(String key) {
            return conditionsByKey.containsKey(key);
        }

        public List<String> getConditionsByKey(String key) {
            return conditionsByKey.get(key);
        }

        public Set<String> keySet() {
            return conditionsByKey.keySet();
        }

        public void addValuesToKey(String key, List<String> values) {

            List<String> conditionValues = getConditionsByKey(key);
            if (conditionValues == null) {
                conditionsByKey.put(key, new ArrayList<String>(values));
            } else {
                conditionValues.addAll(values);
            }
        }
    }

    /**
     * Groups the list of <code>Condition</code>s by the condition type and
     * condition key.
     *
     * @param conditions the list of conditions to be grouped
     * @return a map of conditions grouped by type and then key.
     */
    private Map<String, ConditionsByKey> groupConditionsByTypeAndKey(
            List<Condition> conditions) {
        Map<String, ConditionsByKey> conditionsByType = new LinkedHashMap<String, ConditionsByKey>();

        String type;
        String key;
        ConditionsByKey conditionsByKey;
        for (Condition condition : conditions) {
            type = condition.getType();
            key = condition.getConditionKey();

            if (!(conditionsByType.containsKey(type))) {
                conditionsByType.put(type, new ConditionsByKey());
            }

            conditionsByKey = conditionsByType.get(type);
            conditionsByKey.addValuesToKey(key, condition.getValues());
        }
        return conditionsByType;
    }

    /**
     * Writes an array along with its values to the JSONGenerator.
     *
     * @param arrayName name of the JSON array.
     * @param values    values of the JSON array.
     */
    private void writeJsonArray(String arrayName, List<String> values)
            throws JsonGenerationException, IOException {
        writeJsonArrayStart(arrayName);
        for (String value : values) {
            generator.writeString(value);
        }
        writeJsonArrayEnd();
    }

    /**
     * Writes the Start of Object String to the JSONGenerator along with Object
     * Name.
     *
     * @param fieldName name of the JSON Object.
     */
    private void writeJsonObjectStart(String fieldName)
            throws JsonGenerationException, IOException {
        generator.writeObjectFieldStart(fieldName);
    }

    /**
     * Writes the End of Object String to the JSONGenerator.
     */
    private void writeJsonObjectEnd() throws JsonGenerationException, IOException {
        generator.writeEndObject();
    }

    /**
     * Writes the Start of Array String to the JSONGenerator along with Array
     * Name.
     *
     * @param fieldName name of the JSON array
     */
    private void writeJsonArrayStart(String fieldName)
            throws JsonGenerationException, IOException {
        generator.writeArrayFieldStart(fieldName);
    }

    /**
     * Writes the End of Array String to the JSONGenerator.
     */
    private void writeJsonArrayEnd() throws JsonGenerationException, IOException {
        generator.writeEndArray();
    }

    /**
     * Writes the given field and the value to the JsonGenerator
     *
     * @param fieldName the JSON field name
     * @param value     value for the field
     */
    private void writeJsonKeyValue(String fieldName, String value)
            throws JsonGenerationException, IOException {
        generator.writeStringField(fieldName, value);
    }

    /**
     * Checks if the given object is not null.
     *
     * @param object the object compared to null.
     * @return true if the object is not null else false
     */
    private boolean isNotNull(Object object) {
        return null != object;
    }
}
