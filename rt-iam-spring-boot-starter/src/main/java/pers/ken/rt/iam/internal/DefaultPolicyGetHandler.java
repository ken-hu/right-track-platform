package pers.ken.rt.iam.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.ken.rt.iam.AccessManagementProperties;
import pers.ken.rt.iam.permission.access.PoliciesGetException;
import pers.ken.rt.iam.utils.Jackson;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * <code> DefaultPolicyGetHandler </code>
 * <desc> DefaultPolicyGetHandler </desc>
 * <b>Creation Time:</b> 2022/8/24 10:06.
 *
 * @author Ken.Hu
 */
@AllArgsConstructor
public class DefaultPolicyGetHandler implements PolicyGetHandler {

    private RestTemplate restTemplate;
    private AccessManagementProperties accessManagementProperties;

    @Override
    public List<Policy> userPolicies() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .orElseThrow(() -> new PolicyGetException("Can not get policy without token")).trim();

        ResponseEntity<String> resp = makeRequest(token);
        return parsePolicies(resp);
    }


    private ResponseEntity<String> makeRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return restTemplate.exchange(
                accessManagementProperties.getPoliciesUri(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
    }

    private List<Policy> parsePolicies(ResponseEntity<String> resp) {
        if (resp.getStatusCode() != HttpStatus.OK) {
            throw new PoliciesGetException("Policies endpoint responded with" + resp.getStatusCode());
        }
        return Jackson.fromJsonString(resp.getBody(), new TypeReference<List<Policy>>() {
        });
    }
}
