package pers.ken.rt.test.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Creation Time: 2022/11/15 11:12.
 *
 * @author Ken
 */
@Entity
@Table(name = "auth_policy")
@Data
public class AuthPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private String policyDocument;
}
