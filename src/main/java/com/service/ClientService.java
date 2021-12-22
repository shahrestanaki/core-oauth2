package com.service;

import com.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by m.shahrestanaki on 2021-12-21
 **/

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UserInfoService userInfoService;

    public boolean matchUserWithClient(String managerName, String clientId) {
        return clientRepository.findByManagerNameAndClientId(managerName, clientId).isPresent();
    }
}
