package pers.ken.rt.auth.infrastructure.converter;

import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.domain.entity.PolicyDocument;
import pers.ken.rt.auth.domain.entity.aggregate.Policy;
import pers.ken.rt.auth.infrastructure.repository.persistence.po.PolicyPO;
import pers.ken.rt.common.utils.Jackson;

import java.util.List;

/**
 * The interface User converter.
 *
 * @author Ken
 * @className: UserConverter
 * @createdTime: 2023 /3/8 11:53
 * @desc:
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PolicyConverter {
    /**
     * The constant INSTANCE.
     */
    PolicyConverter INSTANCE = Mappers.getMapper(PolicyConverter.class);

    /**
     * Convert user.
     *
     * @param policy the policy
     * @return the user
     */
    @Mappings(
            @Mapping(source = "policyDocument", target = "policyDocument", qualifiedByName = "convertToJsonNode")
    )
    PolicyPO convert(Policy policy);

    /**
     * Convert policy.
     *
     * @param policy the policy
     * @return the policy
     */
    @Mappings(
            @Mapping(source = "policyDocument", target = "policyDocument", qualifiedByName = "convertToPolicyDocument")
    )
    Policy convert(PolicyPO policy);

    /**
     * Convert set.
     *
     * @param policy the policy
     * @return the set
     */
    List<Policy> convert(List<PolicyPO> policy);


    /**
     * Convert to json node json node.
     *
     * @param document the document
     * @return the json node
     */
    @Named("convertToJsonNode")
    default JsonNode convertToJsonNode(PolicyDocument document) {
        return Jackson.jsonNodeParse(document);
    }

    /**
     * Convert to json node policy document.
     *
     * @param jsonNode the json node
     * @return the policy document
     */
    @Named("convertToPolicyDocument")
    default PolicyDocument convertToJsonNode(JsonNode jsonNode) {
        return Jackson.jsonNodeParseToObject(jsonNode, PolicyDocument.class);
    }
}
