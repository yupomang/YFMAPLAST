package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.RSP.bean.BaseBean;
import com.yondervision.yfmap.RSP.util.PersistentTool;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.security.Base64;
import com.yondervision.yfmap.form.AppApi01050Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author LFX
 * 图片上传
 */
public class Handle01050_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");

	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi01050Form form = (AppApi01050Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01050 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);
		
		 //实例号
		 //String instance = form.getInstance();
		String instance = form.getSeqno();
	
		 //身份证号
	     String bodyCardNumber = form.getBodyCardNumber();
	     
	     //材料类型
	     String materCode = form.getMaterCode();
	     
	     String flag = form.getActionFlag()+form.getCenterId();
	     String stepid = PropertiesReader.getProperty("yingshe.properties","datapool_stepid"+flag);
	     String flowid = PropertiesReader.getProperty("yingshe.properties","datapool_flowid"+flag);
	     PersistentTool.conDB();
	     int maxnumber = 0;
	     String sql1 = "select * from av054 where instanceid = '-" +
	    		 form.getSeqno() +
	     		"' and scankey_pat= '" +
	     		form.getBodyCardNumber() +
	     		"' and matercode = '" +
	     		form.getMaterCode() +
	     		"' order by seqno desc ";
	     String filenames = "";
	     ResultSet rs = PersistentTool.excuteQuery(sql1);			
	    if(rs!=null)
	    {
	    	//maxnumber = Integer.parseInt(rs.getString("seqno"));
	    	do{
	    		
	    		filenames += rs.getString("filename")+",";
	    		
	    	}while(rs.next());
	    	String sql2 = "delete from av054 where instanceid = '-" +
		    		 form.getSeqno() +
		     		"' and scankey_pat= '" +
		     		form.getBodyCardNumber() +
		     		"' and matercode = '" +
		     		form.getMaterCode()+"'" ;
			    PersistentTool.excuteDel(sql2);
	    }else
	    {
	    	//maxnumber = 0;
	    }
		
		try
        {
		//TODO 本地生成路径,具体取值需配置在配置文件中
        String localPath=PropertiesReader.getProperty("ftp.properties","bsp_local_path");
        //文件名称 00000000/2017-04/15/-5363/108/108_2017041516401069648.png
        Calendar tranTime = Calendar.getInstance();
	     int year =  tranTime.get(Calendar.YEAR);
		 String month = (tranTime.get(Calendar.MONTH)+1)+"";
		 String day = tranTime.get(Calendar.DAY_OF_MONTH)+"";
		 month = month.length()==1?"0"+month:month;
		 day = day.length()==1?"0"+day:day;
		 
        //path
		 String routePath= "00000000"+File.separator+year+"-"+month+File.separator+day+File.separator+"-"+instance+File.separator+materCode+File.separator;
		 String uploadPath = PropertiesReader.getProperty("ftp.properties", "ftp"+form.getCenterId()+"_batch_upfile_path");
		 //文件上传
		 //String filenames = form.getFilenames();
			String files = form.getFiless();
//			String strfilenames[] = new String[0];
			String strfiles[] = new String[0];
//			if(!CommonUtil.isEmpty(filenames)){
//				strfilenames = filenames.split("~");
//			}
			if(!CommonUtil.isEmpty(files)){
				strfiles = files.split("~");
			}
//			
//			if(strfilenames.length != strfiles.length){
//				modelMap.clear();
//				modelMap.put("recode", "000002");
//				modelMap.put("msg", "文件上传异常");
//				log.error("中心返回报文 状态recode :000002,描述msg : 文件上传异常。" );
//				return modelMap;
//			}
//			String realpath = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "filerealpath"+form.getCenterId());
//	    	if(!realpath.endsWith(File.separator)){
//	    		realpath = realpath + File.separator;
//	    	}
	    	// 本地存文件夹路径
//	    	localPath = "D:\\";
//	    	File f = new File(localPath);
//			if(!f.exists()){
//				f.mkdirs();
//			}
			for(int i =0; i<strfiles.length; i++){
				Date date = new Date();
				int local = strfiles[i].indexOf(".");
				String format = strfiles[i].substring(local);
				String tempFileName = materCode+"_"+getSystemDate(date, "yyyyMMddHHmmssSSS")+getRandomNum(2)+format;
				log.debug("fileName---------->" + strfiles[i]); 
				if(strfiles[i].contains("@"))
				{
					if(filenames.contains(strfiles[i].split("@")[1]))
					{
						tempFileName = strfiles[i].split("@")[1];
					}
				}
				String filename = localPath + tempFileName;
				
				log.debug("fileName---------->" + filename);   
				//String file = strfiles[i];
				//byte bfile[] = Base64.decode(file);
				
				
				File oldf = new File(strfiles[i]);
				File newf = new File(filename);
				oldf.renameTo(newf);
				// 拿到输出流，同时重命名上传的文件   
//				FileOutputStream os = new FileOutputStream(filename);
//				os.write(f);
//				os.flush();
//				os.close();
				//将上传的文件存到ftp上
	            //TODO  上传 ftp---具体路径，用户名密码需确定，后续在配置文件中配置
	        	FtpUtil ftp=new FtpUtil("bsp"+form.getCenterId(),routePath);
	        	//选择ftp路径
	        	ftp.setUploadPath(uploadPath+routePath);
	    		ftp.putFileToServerName(tempFileName);
	    		//删除本地文件
	    		//File tempFile = new File(filename);
	    		oldf.delete();
	    		newf.delete();
	    		
	    		//存数据库
	    		BaseBean bean = new BaseBean();
	    		bean.setTableName("av054");
	    		bean.put("instanceid","-"+instance);
	    		bean.put("scankey_pat",bodyCardNumber);
	    		bean.put("matercode",materCode);
    			bean.put("seqno",maxnumber+1);
	    		bean.put("filename",routePath+tempFileName);
	    		bean.put("transdate",getSystemDate(date, "yyyy-MM-dd"));
	    		bean.put("timestamp",getSystemDate(date, "yyyy-MM-dd HH:mm:ss"));
	    		String sql = PersistentTool.getAddSQL(bean);
	    		System.out.println("sql>>>:"+sql);
	    		// 将信息插入数据表中
	    		
	    		PersistentTool.executeUpdatePre(sql, bean);
	    		maxnumber++;
			}
		 
        }
        catch(Exception e){
        	modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "上传图片异常");
        	log.error("上传图片到ftp异常:"+e.getMessage());
        	return modelMap;
        }finally{
        	if(PersistentTool.con!=null){
        		PersistentTool.closeDB();
        	}
        }
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "上传图片成功，请继续上传材料；若无材料继续上传，请返回上一级继续办理业务。");
		modelMap.put("maxnumber", maxnumber+"");
		return modelMap;
	} 
	
	 /**
	  * 获取随机数
	  * @param num
	  */
	private String getRandomNum(int num){
		String result = "";
		 for (int i=0;i<num;i++)
			 result+=(int)(Math.random()*10);
		 return result;
	}
	/**
	 * 将指定时间转化成固定格式时间表示
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getSystemDate(Date date,String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	} 
	
	
	public static void main(String[] args){
//		AppApi01050Form test = new AppApi01050Form();
//		test.setBodyCardNumber("67676767676767");
//		test.setInstance("4543");
//		test.setMaterCode("46");
//		
//		String filenames = "1.png~2.png~";
//		test.setFilenames(filenames);
//		
//		String filess = "";
//		
//		File file = new File("d:\\1.jpg");
//		File file2 = new File("d:\\2.jpg");
//		List<File> files = new ArrayList<File>();
//		files.add(file);
//		files.add(file2);
//		for(int i=0;i<files.size();i++){
//	     ByteArrayOutputStream output = new ByteArrayOutputStream();
//	     byte[] buffer = new byte[1024 * 1024];
//	     byte[] img = null; 
//			int byteread = 0;
//			 // 拿到上传文件的输入流   
//			InputStream stream;
//			try {
//				stream = new FileInputStream(file);
//				while ((byteread = stream.read(buffer)) != -1) {
//					output.write(buffer, 0, byteread);
//				}
//				img = output.toByteArray();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			String aa = Base64.encode(img);
//			filess+=aa+"~";
//		}
//		
//		test.setFiless(filess);
//		
//		System.out.println(filenames);
//		System.out.println(filess);
//		
//		Handle01050_00089800 temp = new Handle01050_00089800();
//		ModelMap modelMap = new ModelMap();
//		try {
//			temp.action(test, modelMap);
//		} catch (CenterRuntimeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		 Calendar tranTime = Calendar.getInstance();
	     int year =  tranTime.get(Calendar.YEAR);
		 String month = (tranTime.get(Calendar.MONTH)+1)+"";
		 String day = tranTime.get(Calendar.DAY_OF_MONTH)+"";
		 month = month.length()==1?"0"+month:month;
		 day = day.length()==1?"0"+day:day;
		 
		 System.out.println(day);
	}
}
