package com.itheima.store.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.itheima.store.domain.Category;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.UUIDUtils;
import com.sun.org.apache.xerces.internal.impl.xs.util.StringListImpl;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

/**
 * Servlet implementation class AddProductServlet
 */
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//创建一个product的对象
			Product product = new Product();
			//创建磁盘文件项工厂
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			//获得核心解析类
			ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
			fileUpload.setHeaderEncoding("UTF-8");  //解决中文文件上传乱码
			//解析request, 返回list集合
			List<FileItem> list = fileUpload.parseRequest(request);
			//获得到每个部分
			//将遍历的值存入map中
			Map<String, String> map = new HashMap<String,String>();
			String fileName = null; //文件名
			for (FileItem fileItem : list) {
				//判断普通项和文件上传项  isFormField是表单域(表单域表示文本框,下拉列表)都是普通项
				if (fileItem.isFormField()) {
					//普通项
					String name = fileItem.getFieldName();
					String value = fileItem.getString("UTF-8"); //解决普通项的中文乱码问题
					System.out.println(name +"..."+ value);
					map.put(name, value);
				}else {
					//文件上传项  <input text="file"> 
					//获得文件名
					fileName = fileItem.getName();
					System.out.println("文件名"+fileName);
					//获得文件的输入流
					InputStream is = fileItem.getInputStream();
					//获得文件上传到路径
					String path = this.getServletContext().getRealPath("/jsp/products/1");
					OutputStream os = new FileOutputStream(path+"/"+fileName);
					int len = 0;
					byte[] b = new byte[1024];
					while((len = is.read(b))!=-1) {
						os.write(b, 0, len);
					}
					os.close();
					is.close();
				}
			}
			//封装数据
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getUUID());
			product.setPdate(new Date());
			product.setPflag(0);
			product.setPimage("products/1/"+fileName);
			Category category = new Category();
			category.setCid(map.get("cid"));
			product.setCategory(category);
			
			//存入到数据库
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			productService.save(product);
			
			//页面跳转
			response.sendRedirect(request.getContextPath()+"/AdminProductServlet?method=findByPage&currPage=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
