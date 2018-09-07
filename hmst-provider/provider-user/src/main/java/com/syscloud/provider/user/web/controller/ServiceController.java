//package com.syscloud.provider.user.web.controller;
//
//import com.syscloud.base.auth.pojo.ObjectRestResponse;
//import org.springframework.web.bind.annotation.*;
//
//import static com.sun.tools.corba.se.idl.toJavaPortable.Arguments.Client;
//
///**
// * @author ace
// * @create 2017/12/26.
// */
//@RestController
//@RequestMapping("service")
//public class ServiceController extends BaseController<ClientBiz,Client>{
//
//    @RequestMapping(value = "/{id}/client", method = RequestMethod.PUT)
//    @ResponseBody
//    public ObjectRestResponse modifyUsers(@PathVariable int id, String clients){
//        baseBiz.modifyClientServices(id, clients);
//        return new ObjectRestResponse().rel(true);
//    }
//
//    @RequestMapping(value = "/{id}/client", method = RequestMethod.GET)
//    @ResponseBody
//    public ObjectRestResponse<ClientService> getUsers(@PathVariable int id){
//        return new ObjectRestResponse<ClientService>().rel(true).data(baseBiz.getClientServices(id));
//    }
//}
