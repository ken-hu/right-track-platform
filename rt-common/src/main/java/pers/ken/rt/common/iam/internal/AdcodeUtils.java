package pers.ken.rt.common.iam.internal;

/**
 * <code> AdcodeUtils </code>
 * <desc> AdcodeUtils </desc>
 * <b>Creation Time:</b> 2022/3/27 13:44.
 *
 * @author _Ken.Hu
 */
public class AdcodeUtils {
    /**
     * The type Adcode.
     */
    public static class Adcode {
        private String adcode;
        private AreaType type;
    }

    /**
     * The enum Area type.
     */
    public enum AreaType {
        /**
         * 国家
         */
        COUNTRY,
        /**
         * 省
         */
        PROVINCE,
        /**
         * 城市
         */
        CITY,
        /**
         * 区县
         */
        DISTRICTS,
        /**
         * 街道
         */
        STREET
    }

    /**
     * Gets adcode.
     *
     * @param adcode the adcode
     * @return the adcode
     */
    public static Adcode getAdcode(String adcode) {
        return null;
    }

    /**
     * Gets AreaType.
     *
     * @return the type
     */
    public static AreaType getAreaType() {
        return null;
    }
}
