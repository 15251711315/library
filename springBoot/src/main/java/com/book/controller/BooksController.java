package com.book.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.book.DAO.RelationDAO;
import com.book.DAO.UserDAO;
import com.book.DTO.*;
import com.book.PO.BooksPO;
import com.book.PO.RelationPO;
import com.book.PO.UserPO;
import com.book.service.LibraryService;
import com.book.utils.DealExcel;
import com.book.utils.HttpGetUtil;
import org.apache.poi.ss.formula.functions.T;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/books")
public class BooksController {
DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Autowired
    private LibraryService libraryService;

    //查询书
    @ResponseBody
    @RequestMapping(value = "/queryBooks")
    private String queryBooks(String nameLike) {
        QueryBooksReq queryBooksReq = new QueryBooksReq();
        List<BooksDTO> booksDTOList = new ArrayList<>();
        if (nameLike == null) {
            nameLike = "";
        }
        queryBooksReq.setNameLike(nameLike);
        booksDTOList = libraryService.queryBooks(queryBooksReq);
        return JSON.toJSONString(booksDTOList);
    }

    //查询已借图书
    @ResponseBody
    @RequestMapping(value = "/querySelectedBooks")
    public String querySelectedBooks(String selectedBooksIds) {
        List<BooksDTO> booksDTOList = new ArrayList<>();
        List<Long> selectedBooksIdList = new ArrayList<>();
        selectedBooksIdList = JSON.parseArray(selectedBooksIds, Long.class);
        booksDTOList = libraryService.querySelectedBooks(selectedBooksIdList);
        return JSON.toJSONString(booksDTOList);
    }

    @RequestMapping(value = "/achieveOpenid")
    @ResponseBody
    public String achieveOpenid(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String openid = libraryService.achieveOpenid(request,response);
        return openid;
    }

    @RequestMapping(value="/addUser")
    @ResponseBody
    public String  addUser(UserDTO userDTO){
       Long id = libraryService.addUser(userDTO);
       if(id!=null)
       return "SUCCESS";
       return null;
    }

    @RequestMapping(value="queryUserInfo")
    @ResponseBody
    public String queryUserInfo(String openid){
        UserPO userDTO = libraryService.queryUserInfo(openid);
        return JSON.toJSONString(userDTO);
    }

    @RequestMapping(value="queryReadBooks")
    @ResponseBody
    public String queryReadBooks(Long userId){
       Map<String,List<RelationDTO>> result = new HashMap<>();
        result = libraryService.queryReadBooks(userId);
        return JSON.toJSONString(result);
    }



//网页
    @RequestMapping(value = "/doAddBooks")
    public String addBooks1(BooksPO booksPO,HttpServletRequest request) {
        booksPO.setImages("https://www.baohaiya.top/bookPics/no.jpg");
                            booksPO.setAuthor("");
                            booksPO.setNum(1);
                            booksPO.setCreateTime(new Date());
                            booksPO.setDes("");
                            long id = libraryService.addBooks(booksPO);
                            return "addBooks";
//        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//        try {
//        if(isMultipart){
//            String realPath = request.getSession().getServletContext().getRealPath("/");
//            System.out.println(realPath);
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            //2、创建一个文件上传解析器
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            //解决上传文件名的中文乱码
//            upload.setHeaderEncoding("UTF-8");
//            //3、判断提交上来的数据是否是上传表单的数据
//            if(!ServletFileUpload.isMultipartContent(request)){
//                //按照传统方式获取数据
//                System.out.println("没有文件上传");
//                return "index.html";
//            }
//            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
//            List<FileItem> list = upload.parseRequest(request);
//            for(FileItem item : list){
//                //如果fileitem中封装的是普通输入项的数据
//                if(item.isFormField()){
//                    String name = item.getFieldName();
//                    //解决普通输入项的数据的中文乱码问题
//                    String value = item.getString("UTF-8");
//                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
//                    System.out.println(name + "=" + value);
//                }else{//如果fileitem中封装的是上传文件
//                    //得到上传的文件名称，
//                    String filename = item.getName();
//                    System.out.println(filename);
//                    if(filename==null || filename.trim().equals("")){
//                        continue;
//                    }
//                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
//                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
//                    filename = filename.substring(filename.lastIndexOf("\\")+1);
//                    //获取item中的上传文件的输入流
//                    InputStream in = item.getInputStream();
//                    //创建一个文件输出流
//                    FileOutputStream out = new FileOutputStream(realPath + "\\" + filename);
//                    //创建一个缓冲区
//                    byte buffer[] = new byte[1024];
//                    //判断输入流中的数据是否已经读完的标识
//                    int len = 0;
//                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
//                    while((len=in.read(buffer))>0){
//                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
//                        out.write(buffer, 0, len);
//                    }
//                    //关闭输入流
//                    in.close();
//                    //关闭输出流
//                    out.close();
//                    //删除处理文件上传时生成的临时文件
//                    item.delete();
//                }
//            }
//
////            File file = new File(realPath+"\\"+fileName);
////            if(!file.exists()){
////                file.mkdirs();
////            }
////            FileOutputStream outputStream = new FileOutputStream(realPath+"\\"+fileName);
////            outputStream.write(filedata.getBytes());
////            outputStream.close();
////            DealExcel d =  new DealExcel();
////            InputStream io = null;
//
////                io = new FileInputStream("C:\\Users\\战神包\\Desktop\\1.xlsx");
////            List<Object> list =  d.importDataFromExcel(new Excel(),io,"1.xlsx");
////            for(Object o:list){
////                System.out.println(o);
////            }
//
//            return null;
//        }else{
//                    booksPO.setImages("https://www.baohaiya.top/bookPics/no.jpg");
//                    booksPO.setAuthor("");
//                    booksPO.setNum(1);
//                    booksPO.setCreateTime(new Date());
//                    booksPO.setDes("");
//                    long id = libraryService.addBooks(booksPO);
//                    return ""+id;
//        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @RequestMapping(value = "/addBooks")
    public String addBooks() {
        return "addBooks";
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }@RequestMapping(value = "/approval")
    public String approval(){
        return "approval";
    }@RequestMapping(value = "/addRelation")
    public String addRelation(){
        return "addRelation";
    }@RequestMapping(value = "/returnBooks")
    public String returnBooks(){
        return "returnBooks";
    }

    @RequestMapping(value = "submitBooks")
    @ResponseBody
    public String submitBooks(String listStr,Long userId,String formId,String openid,HttpServletRequest request,HttpServletResponse response){
        try{
            List<BooksPO> booksPOList =  JSON.parseArray(listStr,BooksPO.class);
            if(booksPOList.size()==0){
                return null;
            }
            List<RelationDTO> relationDTOList = new ArrayList<>();
            String name = "";
            for(BooksPO booksPO:booksPOList){
                RelationDTO relationDTO =new RelationDTO();
                relationDTO.setUserId(userId);
                relationDTO.setBooksId(booksPO.getId());
                relationDTOList.add(relationDTO);
                name+=booksPO.getName()+",";
            }
            name.substring(0,name.length()-1);
            String result =  libraryService.submitBooks(relationDTOList);
            if("OK".equals(result)){
                response.setContentType("text/html");
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                Map params = new HashMap();
                params.put("grant_type", "client_credential");
                params.put("appid", "wx97bb89632b1624c8");
                params.put("secret", "308fcf3739300a17f7ced736b0b2e978");
                String r = HttpGetUtil.httpRequestToString(
                    "https://api.weixin.qq.com/cgi-bin/token","GET" ,params);

                AccessTokenDTO accessTokenDTO = JSON.parseObject(r, AccessTokenDTO.class);
                String access = accessTokenDTO.getAccess_token();
                System.out.println("access:"+access);
                System.out.println("formId:"+formId);
                SendTemplateMessage sendTemplateMessage = new SendTemplateMessage();
                /*//拼接数据
                Map<String,TemplateData> map = new HashMap<>();
                map.put("keyword1",new TemplateData(String.valueOf(booksPOList.size())));
                map.put("keyword2",new TemplateData(name));
                map.put("keyword3",new TemplateData(df.format(new Date())));
                sendTemplateMessage.setTouser(openid);
                sendTemplateMessage.setTemplate_id("ZPbSl2GmWzTGhxbZPzbgl8IaCF3-e0FsKkQ04gAm5ao");
                sendTemplateMessage.setPage("");
                sendTemplateMessage.setForm_id(formId);
                sendTemplateMessage.setData(map);
                sendTemplateMessage.setEmphasis_keyword("");
                String json =  JSONObject.toJSONString(sendTemplateMessage);*/
                Map<String,TemplateData> m = new HashMap<>();
               Map params2 = new HashMap();
//                m.put("keyword1",String.valueOf(booksPOList.size()));
//                m.put("keyword2",name);
//                m.put("keyword3",df.format(new Date()));
                m.put("keyword1",new TemplateData(String.valueOf(booksPOList.size())));
                m.put("keyword2",new TemplateData(name));
                m.put("keyword3",new TemplateData(df.format(new Date())));
//                m.put("keyword1",new TemplateData(String.valueOf(booksPOList.size())));
//                m.put("keyword2",new TemplateData(name));
//                m.put("keyword3",new TemplateData(df.format(new Date())));
                //拼接数据
                sendTemplateMessage.setTouser(openid);
                sendTemplateMessage.setTemplate_id("ZPbSl2GmWzTGhxbZPzbgl8IaCF3-e0FsKkQ04gAm5ao");
                sendTemplateMessage.setForm_id(formId);
                sendTemplateMessage.setData(m);
                sendTemplateMessage.setPage("/pages/my/my");
                String json =  JSONObject.toJSONString(sendTemplateMessage);

//                params2.put("touser", openid);
//                params2.put("template_id", "ZPbSl2GmWzTGhxbZPzbgl8IaCF3-e0FsKkQ04gAm5ao");
//                params2.put("form_id", formId);
//                params2.put("data", JSON.toJSONString(m));
//                String params2 = "touser="+openid+"&template_id=ZPbSl2GmWzTGhxbZPzbgl8IaCF3-e0FsKkQ04gAm5ao&form_id=63490a380aed455d874cbc19c10e6eee&data=''";
                String r2 = HttpGetUtil.sendPost(
                    "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+access,json);
                System.out.println(r2);
                return result;
            }else{
                return null;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    @RequestMapping(value = "queryRelations")
    @ResponseBody
    public String queryRelations(){
        List<RelationDTO> list = libraryService.queryRelations();
        String result = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
        return result;
    }

    @RequestMapping(value = "doReturnBooks")
    @ResponseBody
    public  String doReturnBooks(Long relationId,Long bookId){
       String result = libraryService.doReturnBooks(relationId,bookId);
        return result;
    }
    @RequestMapping(value = "test")
    public  String test(HttpServletRequest request){
        String a="";
        if (request.getHeader("x-forwarded-for") == null) {
            a = request.getRemoteAddr();
        }
         a = request.getRemoteAddr();
        System.out.println(a);
        return "test";
    }
}
