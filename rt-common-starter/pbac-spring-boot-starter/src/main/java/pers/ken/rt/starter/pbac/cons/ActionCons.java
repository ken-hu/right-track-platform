package pers.ken.rt.starter.pbac.cons;

import lombok.Getter;

/**
 * @ClassName: ActionCons
 * @Created: 2024/10/31
 * @Author ken
 */
@Getter
public class ActionCons {
    private ActionCons() {
    }

    public static class OperationName {
        private OperationName() {
        }

        public static final String CREATE = "Create";
        public static final String UPDATE = "Update";
        public static final String DELETE = "Delete";
        public static final String LIST = "List";
        public static final String GET = "Get";
    }

    public static class HttpMethodName {
        private HttpMethodName() {
        }

        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";
    }
}
