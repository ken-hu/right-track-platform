package pers.ken.rt.uaa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.ken.rt.uaa.entity.PolicyDocument;

/**
 * <code> PolicyRepository </code>
 * <desc> PolicyRepository </desc>
 * <b>Creation Time:</b> 2021/12/3 13:49.
 *
 * @author _Ken.Hu
 */
@Repository
public interface PolicyDocumentRepository extends JpaRepository<PolicyDocument, Long> {

}