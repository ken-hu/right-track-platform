package pers.ken.rt.auth.domain.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.auth.domain.repository.UserRepository;
import pers.ken.rt.auth.domain.service.UserDomainService;

/**
 * @author Ken
 * @className: UserDomainServiceImpl
 * @createdTime: 2023/3/8 1:58
 * @desc:
 */
@Service
@AllArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {
    private UserRepository userRepository;

}
