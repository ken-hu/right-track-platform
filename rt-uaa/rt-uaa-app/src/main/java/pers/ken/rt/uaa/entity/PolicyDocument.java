package pers.ken.rt.uaa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * <code> PolicyDocument </code>
 * <desc> PolicyDocument </desc>
 * <b>Creation Time:</b> 2022/2/24 22:43.
 *
 * @author _Ken.Hu
 */
@Entity
@Table(name = "uc_authority_policy")
@Data
public class PolicyDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String policyDocument;
}
