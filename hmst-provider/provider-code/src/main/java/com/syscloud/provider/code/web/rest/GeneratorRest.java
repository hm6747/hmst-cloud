package com.syscloud.provider.code.web.rest;

import com.alibaba.fastjson.JSON;
import com.syscloud.pojo.BaseResponse;
import com.syscloud.pojo.JsonData;
import com.syscloud.provider.code.service.GeneratorService;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by ace on 2017/8/25.
 */
@Controller
@RequestMapping("/sys/code")
public class GeneratorRest {

    @Autowired
    private GeneratorService generatorService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public JsonData list(@RequestParam Map<String, Object> params) {
        return JsonData.success(generatorService.queryList(params));
    }

    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] tableNames = new String[]{};
        String tables = request.getParameter("tables");
        tableNames = JSON.parseArray(tables).toArray(tableNames);

        byte[] data = generatorService.generatorCode(tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"ag-admin-code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

    @RequestMapping("/upload")
    @ResponseBody
    public JsonData uploadFile(HttpServletRequest request, String parama) {
        BaseResponse baseResponse = new BaseResponse();
        List<FileUpload> fileUploadList = null;
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = null;
        FileUpload fileUpload = null;
        //遍历上传的文件
        int i = 0;
        fileUpload = new FileUpload();
        file = mRequest.getFile("file" + i);
        String name = file.getOriginalFilename();
        int index = name.indexOf(".");
        //拿到文件后缀
        boolean flag = false;
        String suffix = "";
        if (name.indexOf(".") != -1) {
            suffix = name.split("\\.")[name.split("\\.").length - 1];
            //格式检验
        }
        if (!flag) {
            JsonData.fail("不支持:'" + suffix + "'格式文件上传!");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            JsonData.fail("所上传的:'" + name + "'超出文件大小限制");
        }
        return JsonData.success();
    }
}
