package com.syscloud.provider.user.service.impl;

import com.syscloud.provider.user.service.AuthClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBAuthClientService implements AuthClientService {
    @Override
    public String apply(String clientId, String secret) throws Exception {
        return null;
    }

    @Override
    public List<String> getAllowedClient(String serviceId, String secret) {
        return null;
    }

    @Override
    public List<String> getAllowedClient(String serviceId) {
        return null;
    }

    @Override
    public void registryClient() {

    }

    @Override
    public void validate(String clientId, String secret) throws Exception {

    }
//    @Autowired
//    private ClientMapper clientMapper;
//    @Autowired
//    private ClientTokenUtil clientTokenUtil;
//    @Autowired
//    private DiscoveryClient discovery;
//    private ApplicationContext context;
//
//    @Autowired
//    public DBAuthClientService(ApplicationContext context) {
//        this.context = context;
//    }
//
//    @Override
//    public String apply(String clientId, String secret) throws Exception {
//        Client client = getClient(clientId, secret);
//        return clientTokenUtil.generateToken(new ClientInfo(client.getCode(),client.getName(),client.getId().toString()));
//    }
//
//    private Client getClient(String clientId, String secret) {
//        Client client = new Client();
//        client.setCode(clientId);
//        client = clientMapper.selectOne(client);
//        if(client==null||!client.getSecret().equals(secret)){
//            throw new ClientInvalidException("Client not found or Client secret is error!");
//        }
//        return client;
//    }
//
//    @Override
//    public void validate(String clientId, String secret) throws Exception {
//        Client client = new Client();
//        client.setCode(clientId);
//        client = clientMapper.selectOne(client);
//        if(client==null||!client.getSecret().equals(secret)){
//            throw new ClientInvalidException("Client not found or Client secret is error!");
//        }
//    }
//
//    @Override
//    public List<String> getAllowedClient(String clientId, String secret) {
//        Client info = this.getClient(clientId, secret);
//        List<String> clients = clientMapper.selectAllowedClient(info.getId() + "");
//        if(clients==null) {
//            new ArrayList<String>();
//        }
//        return clients;
//    }
//
//    @Override
//    public List<String> getAllowedClient(String serviceId) {
//        Client info = getClient(serviceId);
//        List<String> clients = clientMapper.selectAllowedClient(info.getId() + "");
//        if(clients==null) {
//            new ArrayList<String>();
//        }
//        return clients;
//    }
//
//    private Client getClient(String clientId) {
//        Client client = new Client();
//        client.setCode(clientId);
//        client = clientMapper.selectOne(client);
//        return client;
//    }
//
//    @Override
//    @Scheduled(cron = "0 0/1 * * * ?")
//    public void registryClient() {
//        // 自动注册节点
//        discovery.getServices().forEach((name) ->{
//            Client client = new Client();
//            client.setName(name);
//            client.setCode(name);
//            Client dbClient = clientMapper.selectOne(client);
//            if(dbClient==null) {
//                client.setSecret(UUIDUtils.generateShortUuid());
//                clientMapper.insert(client);
//            }
//        });
//    }
}
