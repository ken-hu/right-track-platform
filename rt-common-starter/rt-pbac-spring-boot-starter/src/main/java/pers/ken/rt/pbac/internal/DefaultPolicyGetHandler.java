package pers.ken.rt.pbac.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.ken.rt.common.model.PlatformResult;
import pers.ken.rt.pbac.AccessControlProperties;
import pers.ken.rt.pbac.exception.PoliciesGetException;
import pers.ken.rt.pbac.utils.Jackson;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <code> DefaultPolicyGetHandler </code>
 * <desc> DefaultPolicyGetHandler </desc>
 * <b>Creation Time:</b> 2022/8/24 10:06.
 *
 * @author Ken.Hu
 */
@AllArgsConstructor
@Slf4j
public class DefaultPolicyGetHandler implements PolicyGetHandler {

    private RestTemplate restTemplate;
    private AccessControlProperties accessControlProperties;

    @Override
    public List<Policy> loadPolicies() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "request can not be null");
        HttpServletRequest request = requestAttributes.getRequest();
        String token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .orElseThrow(() -> new PolicyGetException("Can not get policy without token")).trim();

        ResponseEntity<String> resp = makeRequest(token);
        return parsePolicies(resp).getData().stream().map(PolicyResp::getPolicyDocument).collect(Collectors.toList());
    }


    private ResponseEntity<String> makeRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return restTemplate.exchange(
                accessControlProperties.getPoliciesUri(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
    }

    private PlatformResult<List<PolicyResp>> parsePolicies(ResponseEntity<String> resp) {
        if (resp.getStatusCode() != HttpStatus.OK) {
            throw new PoliciesGetException("Policies endpoint responded with" + resp.getStatusCode());
        }
        log.info("Policy Resp Body:{}", resp.getBody());
        return Jackson.fromJsonString(resp.getBody(), new TypeReference<PlatformResult<List<PolicyResp>>>() {
        });
    }
}
