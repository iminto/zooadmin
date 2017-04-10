package com.baicai.controller.html;

import com.baicai.core.AuthInterceptor;
import com.baicai.core.BaseTool;
import com.baicai.core.ResultData;
import com.baicai.core.ZKPlugin;
import com.baicai.entity.ZkData;
import com.baicai.util.BeanMapUtil;
import com.baicai.util.StringUtil;
import com.github.zkclient.ZkClient;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
* @Description: 节点操作控制器
* @author 猪肉有毒 waitfox@qq.com  
* @date 2016年12月27日 下午10:07:10 
* @version V1.0  
* 我只为你回眸一笑，即使不够倾国倾城，我只为你付出此生，换来生再次相守
 */
@Before(AuthInterceptor.class)
public class NodeController extends Controller{
	public static Logger log = (Logger) LoggerFactory.getLogger(NodeController.class);
	
	@SuppressWarnings("unchecked")
	public void index(){
		String path="/";
		String queryString=getRequest().getQueryString();
		//此处不能直接 getPara,参数可能会被错误转义
		if(queryString!=null && !("").equals(queryString)){
			if(queryString.startsWith("path=")){
				path=getRequest().getQueryString().substring("path=".length());
			}
		}
		DecimalFormat df = new DecimalFormat("0.00") ;
		long totalMem= Runtime.getRuntime().totalMemory();
		long maxMem = Runtime.getRuntime().maxMemory();
		long freeMem = Runtime.getRuntime().freeMemory();
		ZkClient zkClient=getSessionAttr("zk-client");
		ZKPlugin zkPlugin=new ZKPlugin(zkClient);
		List<String> children=zkPlugin.getChildren(path);
		List<String> nodes=new ArrayList<>();
		for (String child : children) {
			if(!path.equals("/")) {
				child = path + "/" + child;
			}else{
				child = "/" + child;
			}
			nodes.add(child);
		}
		List<String> pathList = BaseTool.splitPath(path, '/');
		ZkData zkData=zkPlugin.readData(path);
		System.out.println("zkClient:"+zkClient);
		setAttr("totalMem", df.format(totalMem/1000000F) + " MB");
		setAttr("maxMem", df.format(maxMem/1000000F) + " MB");
		setAttr("freeMem", df.format(freeMem/1000000F) + " MB");
		setAttr("uptime", BaseTool.toDuration());
		setAttr("nodes", nodes);
		setAttr("data", zkData.getDataString());
		setAttr("path", path);
		setAttr("pathList", pathList);
		Map<String, Object> statMap;
		try {
			statMap = BeanMapUtil.bean2Map(zkData.getStat());
			statMap.remove("class");
			setAttr("stat", statMap);
		} catch (Exception e) {
			log.error("详情页错误",e);
			e.printStackTrace();
		}
		
		render("/admin-index.htm");
	}

	public void create(){
		ResultData result = new ResultData();
		String parentPath=getPara("parentPath");
		if(StrKit.isBlank(parentPath)){
			parentPath="/";
		}
		String nodeName=getPara("nodeName");
		nodeName= StringUtil.rtrim(nodeName,new char[]{'/'});
		String nodeData=getPara("nodeData");
		Integer mode=getParaToInt("nodeType");
		try{
		ZkClient zkClient=getSessionAttr("zk-client");
		ZKPlugin zkPlugin=new ZKPlugin(zkClient);
		if(!parentPath.equals("/")) {
			nodeName = parentPath + "/" + nodeName;
		}else{
			nodeName = "/" + nodeName;
		}
		zkPlugin.create(nodeName,nodeData.getBytes(),mode);
			result.setMessage("创建成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("创建失败");
		}
		renderJson(result);
	}

	public void delete(){
		ResultData result = new ResultData();
		String path=getPara("path");
		try{
			ZkClient zkClient=getSessionAttr("zk-client");
			ZKPlugin zkPlugin=new ZKPlugin(zkClient);
			if(path.startsWith("/zookeeper/") || path.equals("/zookeeper")){
				result.setMessage("系统保留节点，不允许删除");
			}else {
				zkPlugin.delete(path);
				result.setMessage("删除成功");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除失败,原因："+e.getMessage());
			log.error("删除失败,错误",e);
		}

		renderJson(result);
	}

	public void rdelete(){
		ResultData result = new ResultData();
		String path=getPara("path");
		try{
		ZkClient zkClient=getSessionAttr("zk-client");
		ZKPlugin zkPlugin=new ZKPlugin(zkClient);
			if(path.startsWith("/zookeeper/") || path.equals("/zookeeper")){
				result.setMessage("系统保留节点，不允许删除");
			}else {
				zkPlugin.deleteRecursive(path);
				result.setMessage("级联删除成功");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("级联删除失败，错误原因："+e.getMessage());
			log.error("级联删除失败,错误",e);
		}
		renderJson(result);
	}

	public void modify(){
		ResultData result = new ResultData();
		String parentPath=getPara("parentPath");
		if(StrKit.isBlank(parentPath)){
			parentPath="/";
		}
		String nodeData=getPara("nodeData");
		try{
			ZkClient zkClient=getSessionAttr("zk-client");
			ZKPlugin zkPlugin=new ZKPlugin(zkClient);
			if(zkClient.exists(parentPath)) {
				zkPlugin.edit(parentPath, nodeData.getBytes());
				result.setMessage("数据修改成功");
			}else{
				result.setSuccess(false);
				result.setMessage("节点路径不存在失败");
			}
		}catch (Exception e){
			result.setSuccess(false);
			result.setMessage("数据修改失败");
			log.error("数据修改失败,错误",e);
		}
		renderJson(result);
	}
}
