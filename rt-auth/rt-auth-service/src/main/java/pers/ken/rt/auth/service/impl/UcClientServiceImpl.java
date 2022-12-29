//package pers.ken.rt.auth.service.impl;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import pers.ken.rt.auth.entity.Oauth2Client;
//import pers.ken.rt.auth.repository.UcClientRepository;
//import pers.ken.rt.auth.service.UcClientService;
//
///**
// * <name> ClientServiceImpl </name>
// * <desc> </desc>
// * Creation Time: 2021/9/28 0:02.
// *
// * @author _Ken.Hu
// */
//@Service
//@AllArgsConstructor
//public class UcClientServiceImpl implements UcClientService {
//    private final UcClientRepository ucClientRepository;
//
//    @Override
//    public Oauth2Client getClient(String clientId) {
//        return ucClientRepository.findByClientId(clientId);
//    }
//}
