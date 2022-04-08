package geso.dms.center.servlets.donmuahang;

import geso.dms.center.beans.donmuahang.IDonmuahangList;
import geso.dms.center.beans.donmuahang.IERP_DonDatHang;
import geso.dms.center.beans.erp_dontrahang.IERP_dontrahang;
import geso.dms.center.beans.erp_dontrahang.imp.Erp_dontrahang;
import geso.dms.center.beans.donmuahang.imp.DonmuahangList;
import geso.dms.center.beans.donmuahang.imp.ERP_DonDatHang;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Erp_KTDuyetDonHangSvl")

public class Erp_KTDuyetDonHangSvl extends HttpServlet 
{
    private static final long serialVersionUID = 1L;            
    public Erp_KTDuyetDonHangSvl() 
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	IDonmuahangList obj;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();       
        Utility util = new Utility();
        out = response.getWriter();
        
        String querystring = request.getQueryString();
        String action = util.getAction(querystring);
        out.println(action);
        String ddhId = util.getId(querystring);
        String userId = util.getUserId(querystring);
        String loai = util.getLoai(querystring);
   
        if (userId.length()==0)
        	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
        String msg="";	
        if (action.equals("duyet"))
        {
        	 msg=	KTDuyetDonHang(ddhId,loai);
        	
        }
        
        else if (action.equals("khongduyet"))
        {
        	 msg=	 	KTUnDuyetDonHang(ddhId,loai);
        	
        }
        else if(action.equals("display"))
        {
        	if(loai.equals("1"))
        	{        		
        		IERP_DonDatHang dhBean=new ERP_DonDatHang(ddhId);
   		 	  	dhBean.Init();
   		 	  	String nextJSP="";
   		 	  	if(dhBean.getISKM().equals("1"))
   		 	  	{   		 	  		
   		 	  		nextJSP = "/AHF/pages/Center/Erp_DonMuaHangKmDisplayDuyet.jsp";
   		 	  	}
   		 	  	else
   		 	  	{
   		 		nextJSP = "/AHF/pages/Center/Erp_DonDatHangDisplayDuyet.jsp";
   		 	  	}
   		 	  	
   		 	  	session.setAttribute("obj", dhBean);
   		 		response.sendRedirect(nextJSP);      	
        	}
        	else
        	{
        		IERP_dontrahang dhBean=new Erp_dontrahang(ddhId);
        		dhBean.Init();
        		
        		String  nextJSP = "/AHF/pages/Center/Erp_DonTraHangDisplayDuyet.jsp";
        		session.setAttribute("obj", dhBean);
        		response.sendRedirect(nextJSP);     	
        	}
        	return;
    	}

		        out.println(userId);
		        obj = new DonmuahangList();
		        obj.setUserId(userId);
		        obj.createDdhKTlist("");
		        obj.SetMsg(msg);
		        session.setAttribute("obj", obj);
		        String nextJSP = "/AHF/pages/Center/Erp_KTDuyetDonHang.jsp";
		        response.sendRedirect(nextJSP);
    		
        
    }	

    private String KTUnDuyetDonHang(String ddhId,String loai) 
    {   
    	dbutils db=new dbutils();     
    	String sql="";
    	if(loai.equals("1")){
    	 sql="update dondathang set trangthai='2' where trangthai=3 and  pk_Seq="+ ddhId; 
    	 if(!db.update(sql)){
    		 geso.dms.center.util.Utility.rollback_throw_exception(db);
    		 return "Error : "+sql;
    		 
    	 }	
    	}else{
    		try{
    		db.getConnection().setAutoCommit(false);
    		//kiem tra dontrahang nay npp  da khoa so chua?
    		sql="select npp_fk from dontrahang inner join nhaphanphoi npp on npp.pk_seq=dontrahang.npp_fk where npp.priandsecond='1'  and  dontrahang.pk_seq= "+ddhId;
    		ResultSet rs1=db.get(sql);
    		if(rs1.next()){
    			//khong quan ly kho
    			sql="update dontrahang  set trangthai='2' where trangthai=3 and pk_Seq="+ ddhId;
    			if(!db.update(sql)){
      				 db.getConnection().rollback();
      				 return "Error :"+ sql;
      			 }
    		}else{
    			//co quan ly kho thi kiem tra dontrahang nay da vuot qua khoa so chua
    			sql="select npp_fk,kbh_fk,kho_fk,sanpham_fk,soluong  from dontrahang dth inner join dontrahang_sp dth_sp on dth.pk_seq=dth_sp.dontrahang_fk "+
	       		 " inner join nhaphanphoi npp on npp.pk_seq=dth.npp_fk where npp.priandsecond='0' and soluong>0 and dth.trangthai=3 and ngaytra >(select max(ngayks) from khoasongay where khoasongay.npp_fk=dth.npp_fk)  and dth.pk_Seq=" + ddhId;
      		 	////System.out.println("Kenh ban hang :"+sql);
      		 	
    			ResultSet rs=db.get(sql);
	      		 	boolean flag=false;
		       		 while (rs.next()){
		       			 sql="update nhapp_kho set soluong=soluong+"+rs.getString("soluong")+",booked=booked+  "+rs.getString("soluong") + " where  npp_fk='"+rs.getString("npp_fk")+"' and sanpham_fk='"+rs.getString("sanpham_fk")+"' and kbh_fk='"+rs.getString("kbh_fk")+"' and kho_fk='"+rs.getString("kho_fk")+"'" ;
		       			 //System.out.println("Cap nhat Kho :"+sql);
		       			 if(!db.update(sql)){
		       				 db.getConnection().rollback();
		       				 return "Error :"+ sql;
		       			 }
		       			 flag=true;
		       		 }
	       		 
	       		 if(flag==true){
	       			 // da duoc cap nhat lai kho va thoa dieu kien npp chua khoa so ngay tiep theo
	       			sql="update dontrahang  set trangthai='2' where trangthai=3 and pk_Seq="+ ddhId;
	       			
	       			if(!db.update(sql)){
	       				 db.getConnection().rollback();
	       				 return "Error :"+ sql;
	       			 }
	       		 }else{
	       			 db.getConnection().rollback();
	       			 return "Khong The Bo Duyet Don Hang ,Vi Nha Phan Phoi Da Khoa So Lon Hon Ngay Duyet Don Tra Hang Nay";
	       			
	       			 
	       		 }
	       		 if(rs!=null){
	       			 rs.close();
	       		 }
	       		
    		}
    		if(rs1!=null){
    		rs1.close();
    		}
    		
    			
    		db.getConnection().commit();	
    		db.getConnection().setAutoCommit(true);
    			
    		}catch(Exception er){
    			geso.dms.center.util.Utility.rollback_throw_exception(db);	
    			return "Error : "+ er.toString();
    		}
    		
    	}
        
               
        db.shutDown();
        return "";
    }

    private String KTDuyetDonHang(String ddhId,String loai) 
    {
    	dbutils db=new dbutils();
    	String sql="";
    	if(loai.equals("1")){
       	 sql="update dondathang set trangthai='3' where trangthai=2 and pk_Seq="+ ddhId;
       	 if(!db.update(sql)){
       		 return "Error :"+sql;
       	 }
       	}else{
       		//		thuc hien tru kho khi update don trang
       		try{
       		db.getConnection().setAutoCommit(false);
       		
       		
	       		//chi cap nhat kho cho nha co priandsecond=0,co quan ly kho npp
	       		 sql="select npp_fk,kbh_fk,kho_fk,sanpham_fk,soluong  from dontrahang dth inner join dontrahang_sp dth_sp on dth.pk_seq=dth_sp.dontrahang_fk "+
	       		 " inner join nhaphanphoi npp on npp.pk_seq=dth.npp_fk where npp.priandsecond='0' and soluong>0 and dth.trangthai=2 and dth.pk_Seq=" + ddhId;
       		 	ResultSet rs=db.get(sql);
       		 	boolean flag=false;
	       		 while (rs.next()){
	       			 //duyet thi tru soluong kho,dong thoi tru di booked
	       			 sql="update nhapp_kho set soluong=soluong-"+rs.getString("soluong")+",booked=booked-  "+rs.getString("soluong") + " where  npp_fk='"+rs.getString("npp_fk")+"' and sanpham_fk='"+rs.getString("sanpham_fk")+"' and kbh_fk='"+rs.getString("kbh_fk")+"' and kho_fk='"+rs.getString("kho_fk")+"'" ;
	       			 //System.out.println("Cap nhat Kho :"+sql);
	       			 if(!db.update(sql)){
	       				 db.getConnection().rollback();
	       				 return "Error :"+ sql;
	       			 }
	       			 flag=true;
	       		 }
		       		 if(rs!=null){
		       			 rs.close();
		       		 }
		       		 
		       		 //Cap Nhat Ngay Tra Ve Bang Ngay KS + 1 cua Nha phan phoi
		       		if(flag){
		       		 sql="update dontrahang set trangthai='3',ngaytra=(select Replace(convert(char(10), DATEADD(day, 1, cast( max(ngayks)  as datetime)) , 102) , '.', '-' )  from khoasongay where khoasongay.npp_fk=dontrahang.npp_fk ) where trangthai=2 and  pk_Seq="+ ddhId;
		       		}else{
		         		 sql="update dontrahang set trangthai='3' where trangthai=2 and  pk_Seq="+ ddhId;

		       		}
		       		 //System.out.println("Cap nhat Ngay KS"+sql);
		       		
		       		if(!db.update(sql)){
	       				 db.getConnection().rollback();
	       				 return "Error :"+ sql;
	       			 }
		       		db.getConnection().commit();
		       		db.getConnection().setAutoCommit(true);
		       		
		       	
       		}catch(Exception er){
       			geso.dms.center.util.Utility.rollback_throw_exception(db);
       			return "Error"+ er.toString();
       			
       		}
       	}
    	
    	
    	db.shutDown();
    	return "";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        IDonmuahangList obj;
        String userId;
        Utility util = new Utility();                                
        obj = new DonmuahangList();                                
        HttpSession session = request.getSession();
        userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
        String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

        if (action == null)
        {
        	action = "";
        }    
        out.println(action);         

        if(action.equals("view") || action.equals("next") || action.equals("prev"))
        {
        	//obj = new DonmuahangList();
        	obj.setUserId(userId);
        	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
        	obj.createDdhKTlist("");
        	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
        	session.setAttribute("obj", obj);
        	String nextJSP = "/AHF/pages/Center/Erp_KTDuyetDonHang.jsp";                  
        	response.sendRedirect(nextJSP);
        }
        else
        {
        	String search = getSearchQuery(request, obj, userId);                   
            obj.setUserId(userId);
            obj.createDdhKTlist(search);
            session.setAttribute("obj", obj);
            String nextJSP = "/AHF/pages/Center/Erp_KTDuyetDonHang.jsp";                  
            out.println(search);
            response.sendRedirect(nextJSP);
        }
        
        
       
    }

    private String getSearchQuery(HttpServletRequest request, IDonmuahangList obj, String userId)
    {        
            Utility util = new Utility();
            
            String sku = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sku")));            
            if (sku == null)
              	sku = "";               
                sku = sku.split("-")[0].trim();
                obj.setSKU(sku);                
            
            String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
            if (tungay == null)
                tungay = "";        
                obj.setTungay(tungay);
                
            String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
            if (denngay == null)
                denngay = "";                    
                obj.setDenngay(denngay);                
                
            String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   
            if (trangthai == null)
                trangthai = "";                    
                
            if (trangthai.equals("0"))
                trangthai = "";
                obj.setTrangthai(trangthai);
                
            String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));       
            if (kvId == null)
                kvId = "";
                
            if (kvId.equals("0"))
                kvId = "";
                obj.setKvId(kvId);
                
            String loaidonhang=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
            //System.out.println("Loaidonhang : " +loaidonhang);

         /*   String query1 =" select distinct a.ngaydat, a.PREFIX, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,  "+
                           " a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, isnull (nh.ngaychungtu,'0')as ngayhd, "+
                           " isnull(nh.sotienbvat,'0') as tienhd , 1 as loai from dondathang a inner join "+ 
                           " nhanvien b on  a.nguoitao = b.pk_seq   "+
                           " inner join  nhanvien c on a.nguoisua = c.pk_seq  "+
                           " inner join dondathang_sp d on a.pk_seq = d.dondathang_fk "+ 
                           " inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+
                           " inner join  nhaphanphoi  f  on a.npp_fk = f.pk_seq  "+ 
                           " left join nhaphang nh on nh.dathang_fk=a.pk_seq " +
                           " where ( a.trangthai > 1 and a.trangthai <=3) "; 
                           //" where ( a.trangthai > 1 and a.trangthai <=3)  and  f.pk_seq in "+ util.quyen_npp(userId) ; 

            String query2 =" select distinct dth.ngaytra as ngaydat, dth.PREFIX, dth.pk_seq as chungtu,npp.ten as  nppTen,dvkd.donvikinhdoanh,dth.sotienavat, "+
                           "  nt.ten as nguoitao,ns.ten as nguoisua,dth.trangthai,'' as soid,'' as ngayhd,0 as tienhd, 2 as loai  from dontrahang dth inner join nhaphanphoi npp on  dth.npp_fk=npp.pk_seq "+
                           " inner join nhanvien nt on nt.pk_seq=dth.nguoitao "+
                           " inner join nhanvien ns on ns.pk_seq=dth.nguoisua "+
                           " inner join donvikinhdoanh dvkd on dvkd.pk_Seq=dth.dvkd_fk " +
                           " inner join dontrahang_sp dth_sp on dth.pk_seq=dth_sp.dontrahang_fk"+
                           " where dth.trangthai >=2 and dth.trangthai <=3 "; 
            			   //" where dth.trangthai >=2 and dth.trangthai <=3 and  npp.pk_seq in "+ util.quyen_npp(userId) ; 
*/            
            String query1 = " select distinct a.ngaydat, a.PREFIX, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh, "+
            		" cast(a.sotienavat as float) as sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, "+ 
            		" isnull (nh.ngaychungtu,'0')as ngayhd,  cast(isnull(nh.sotienbvat,'0') as float) as tienhd ,1 as loai from dondathang a  "+
            		" inner join  nhanvien b on  a.nguoitao = b.pk_seq    inner join  nhanvien c on a.nguoisua = c.pk_seq   "+
            		" inner join dondathang_sp d on a.pk_seq = d.dondathang_fk  inner join donvikinhdoanh e on  "+
            		" a.dvkd_fk = e.pk_seq   inner join  nhaphanphoi  f  on a.npp_fk = f.pk_seq "+
            		" left join nhaphang nh on nh.dathang_fk=a.pk_seq   where ( a.trangthai  > 1  and a.trangthai <=3)";
            
            String query2 = " select distinct dth.ngaytra as ngaydat, dth.PREFIX, dth.pk_seq as chungtu,npp.ten as "+ 
            		" nppTen,dvkd.donvikinhdoanh,cast(  dth.sotienavat as float) as sotienavat,  nt.ten as nguoitao,ns.ten as nguoisua,dth.trangthai,'' "+ 
            		" as soid,'' as ngayhd, 0 as tienhd, 2 as loai  from dontrahang dth inner join nhaphanphoi npp on  "+
            		" dth.npp_fk=npp.pk_seq  inner join nhanvien nt on nt.pk_seq=dth.nguoitao  inner join nhanvien ns "+
            		" on ns.pk_seq=dth.nguoisua  inner join donvikinhdoanh dvkd on dvkd.pk_Seq=dth.dvkd_fk  "+
            		" inner join dontrahang_sp dth_sp on dth.pk_seq=dth_sp.dontrahang_fk where dth.trangthai >=2  "+
            		" and dth.trangthai <=3 ";     

            if (sku.length()>0)
            {
                query1 = query1 + "and d.sanpham_fk in (select pk_seq from sanpham where ma= '" + sku + "') and d.soluong > 0";
                query2 = query2 + "and dth_sp.sanpham_fk in (select pk_seq from sanpham where ma= '" + sku + "') and d.soluong > 0";
            }

            if (tungay.length()>0)
            {
                query1 = query1 + " and a.ngaydat >= '" + tungay+ "'";
                query2 = query2 + " and dth.ngaytra >= '" + tungay+ "'";
            }
                
            if (denngay.length()>0)
            {
                query1 = query1 + " and a.ngaydat <= '" + denngay+ "'";
                query2 = query2 + " and dth.ngaytra <= '" + denngay+ "'";                                
            }
                
            if(trangthai.length() > 0)
            {
                query1 = query1 + " and a.trangthai = '" + trangthai + "'";
                query2 = query2 + " and dth.trangthai = '" + trangthai + "'";
            }
                
            if(kvId.length() > 0)
            {
                query1 = query1 + " and f.khuvuc_fk = '" + kvId + "'";
                query2 = query2 + " and npp.khuvuc_fk= '" + kvId + "'";
            }       

            String chuoi=query1 +"union " + query2;
            //System.out.println(chuoi);
            return chuoi;
    }              
}
