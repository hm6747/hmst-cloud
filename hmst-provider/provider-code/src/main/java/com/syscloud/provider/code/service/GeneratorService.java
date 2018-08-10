package com.syscloud.provider.code.service;

import com.syscloud.pojo.query.PageResult;
import com.syscloud.provider.code.mapper.GeneratorMapper;
import com.syscloud.provider.code.utils.GeneratorUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service
public class GeneratorService {
	@Autowired
	private GeneratorMapper generatorMapper;

	public PageResult<Map<String, Object>> queryList(Map<String, Object> map) {
		int offset = Integer.parseInt(map.get("pageNo").toString());
		int limit = Integer.parseInt(map.get("pageSize").toString());
		map.put("offset", offset);
		map.put("limit", limit);
		int total = generatorMapper.queryTotal(map);
		if (total > 0) {
			List<Map<String, Object>> list = generatorMapper.queryList(map);
			return PageResult.<Map<String, Object>>builder().total(total).data(list).build();
		}
		return PageResult.<Map<String, Object>>builder().build();
	}

	public int queryTotal(Map<String, Object> map) {
		return generatorMapper.queryTotal(map);
	}

	public Map<String, String> queryTable(String tableName) {
		return generatorMapper.queryTable(tableName);
	}

	public List<Map<String, String>> queryColumns(String tableName) {
		return generatorMapper.queryColumns(tableName);
	}

	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			//生成代码
			GeneratorUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
}
