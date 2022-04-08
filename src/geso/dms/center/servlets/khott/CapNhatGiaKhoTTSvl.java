package geso.dms.center.servlets.khott;
import geso.dms.center.beans.khott.ICapNhatGiaKhoTT;
import geso.dms.center.beans.khott.imp.CapNhatGiaKhoTT;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class CapNhatGiaKhoTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CapNhatGiaKhoTTSvl() {
        super();
        // TODO Auto-generated constructor stub
    }
    private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");		    		 
		    HttpSession session = request.getSession();
		    String querystring = request.getQueryString();
		    
		    Utility util=new Utility();
		    
		    String userId = util.getUserId(querystring);
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    //
		    String khott_=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khott")));
			session.setAttribute("khottid",khott_);
		    session.setAttribute("userTen", gettenuser(userId));    
		    String sql_khott="select pk_seq,ten from khott where trangthai!='2'";
         	dbutils db=new dbutils();
         	ResultSet rs_khott=db.get(sql_khott);
         	session.setAttribute("rs_khott", rs_khott); 
         	ICapNhatGiaKhoTT obj = new CapNhatGiaKhoTT();	     
		    obj.setListCapNhatGiaBySql("");
	    	//Data object is saved into session
	    	session.setAttribute("obj", obj);		
	    	// userId is saved into session
	    	session.setAttribute("userId", userId);
	    	String nextJSP = "/AHF/pages/Center/CapNhatGiaKhoTT.jsp";
	    	response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Utility util = new Utility();
		
		HttpSession session =request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		//tu ten dang nhap,lay ra nha phan phoi	
		session.setAttribute("userId", userId);
		String khott_=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khott")));
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		session.setAttribute("khottid",khott_);
		 String sql_khott="select pk_seq,ten from khott where trangthai!='2'";
         dbutils db=new dbutils();
         ResultSet rs_khott=db.get(sql_khott);
         session.setAttribute("rs_khott", rs_khott);       	
		  	
		  ICapNhatGiaKhoTT obj=new CapNhatGiaKhoTT();
		  	if(action.equals("loadbanggia")){
			//cau lenh truyen vao de tim kiem
			String sql1="select a.khott_fk,a.sanpham_fk,s.ten,s.ma,k.ten,a.giagoc,a.giagoc as giamoi  from khott_sp a inner join sanpham "+
			" s on s.pk_seq=a.sanpham_fk inner join khott k on k.pk_seq=khott_fk  where k.trangthai!='2'";
		    if(!khott_.equals("0")){
		    	sql1=sql1+" and k.pk_seq= "+khott_;
		    	obj.setListCapNhatGiaBySql(sql1);	
		    }
		    session.setAttribute("obj", obj);
		    String nextJSP = "/AHF/pages/Center/CapNhatGiaKhoTT.jsp";
			session.setAttribute("userId", userId);
			response.sendRedirect(nextJSP);
			}else if(action.equals("save")){
			obj.setKhoTTId(khott_);
			List<ICapNhatGiaKhoTT> listSanPhamCapNhat=new ArrayList<ICapNhatGiaKhoTT>();
			    session.setAttribute("obj", obj);
		    	String[] idsp = request.getParameterValues("idsp");//
		    	String[] masp=request.getParameterValues("masp");	    	
		    	String[] tensp =request.getParameterValues("tensp");
				String[] giagoc = request.getParameterValues("giagoc");
				String[] giamoi=request.getParameterValues("giamoi");
				
				  if(idsp != null){					
					int m = 0;
					while(m < idsp.length){
						ICapNhatGiaKhoTT sp = new CapNhatGiaKhoTT();
						if(masp[m] != ""){
							if(masp[m] != ""){ //chi them nhung san pham co so luong > 0
							   sp.setSanPhamId(idsp[m]);
							   sp.setMaSanPham(masp[m]);
							   sp.setTenSanPham(tensp[m]);
							
							   try{
								   sp.setGiaGoc(Double.parseDouble(giagoc[m]));

							   }catch(Exception er){
							   }
							   try{
							  sp.setGiaMoi(Double.parseDouble(giamoi[m])); 
							   }catch(Exception er){}
							   
							   listSanPhamCapNhat.add(sp);
							}
						}
						m++;
					}	
				  }
			    obj.setListTonKhoTT(listSanPhamCapNhat);
			    if(obj.SaveBangGia()){
			    	obj.setMessage("Chuc Mung Ban Da Cap Nhat Bang Gia Thanh Cong!");
			    	obj.setListCapNhatGiaBySql("");
			    
			    }
			    String nextJSP = "/AHF/pages/Center/CapNhatGiaKhoTT.jsp";
				session.setAttribute("userId", userId);
				response.sendRedirect(nextJSP);
		}

			
			
	}

}
