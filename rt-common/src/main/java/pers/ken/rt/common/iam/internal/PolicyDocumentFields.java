package pers.ken.rt.common.iam.internal;

/**
 * <code> JsonPolicyDocumentFields </code>
 * <desc> JsonPolicyDocumentFields </desc>
 * <b>Creation Time:</b> 2022/1/24 22:32.
 *
 * @author _Ken.Hu
 */
public class PolicyDocumentFields {
    private PolicyDocumentFields() {
    }

    public static final String VERSION = "version";
    public static final String POLICY_ID = "id";
    public static final String STATEMENT = "statement";
    public static final String STATEMENT_EFFECT = "effect";
    public static final String EFFECT_VALUE_ALLOW = "allow";
    public static final String STATEMENT_ID = "sid";
    public static final String PRINCIPAL = "Principal";
    public static final String ACTION = "action";
    public static final String RESOURCE = "resource";
    public static final String NOT_RESOURCE = "NotResource";
    public static final String CONDITION = "Condition";
}
