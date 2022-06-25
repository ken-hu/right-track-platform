package pers.ken.rt.common.iam.rn;

/**
 * <code> KrnConverter </code>
 * <desc> KrnConverter </desc>
 * <b>Creation Time:</b> 2022/3/9 22:49.
 *
 * @param <T> the type parameter
 * @author _Ken.Hu
 */
public interface RnConverter<T extends ResourceId> {
    /**
     * Convert rn t.
     *
     * @param rn the krn
     * @return the t
     */
    T convertRn(Rn rn);
}
