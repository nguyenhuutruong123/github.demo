package geso.dms.distributor.servlets.donhang;

import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class KTSaiKho
 */
@WebServlet("/KTSaiKho")
public class KTSaiKho extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KTSaiKho() {
        super();
        // TODO Auto-generated constructor stub
    }

	public static void main(String[] args) {
		// KT sai Kho
		dbutils db = new dbutils();
		
		
		String query = "select  npp_fk, (select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime) ), 102) , '.', '-') ) as ngaybd"
				+ "\n  from khoasongay  "
				+ "\n group by NPP_FK";
		System.out.println("Lay NPP: "+query);
		ResultSet rs = db.get(query);
		int dem = 0;
		if(rs != null)
		{
			try {
				while (rs.next())
				{
				query = "select a.npp_fk, a.TenNPP,a.ma as MASP,a.tensp,a.[11] as slam from "
						 + "\n ("
						 + "\n select npp.ten as TenNPP,sp.ma,sp.ten as tensp, kbh.PK_SEQ as kbh_fk,npp.PK_SEQ as NPP_FK,sp.pk_seq as sanpham_fk,kh.PK_SEQ as kho_fk ,isnull(dh.[1.Tồn đầu],0) as [1],isnull(dh.[2.Nhập hàng],0) as [2] ,isnull(dh.[3.Hàng trả về NPP],0) as [3],isnull(dh.[4.Xuất hàng bán],0) as [4],isnull(dh.[5.Xuất hàng KM],0) as [5],isnull(dh.[6.Điều chỉnh],0) as [6],isnull(dh.[7.1.KH Trả hàng bán],0) as [7.1],isnull(dh.[7.2.Ban Cho NPP],0) as [7.2],"
						 + "\n  isnull(dh.[7.3.Mua Hang NPP],0) as [7.3],isnull(dh.[7.4.KH Trả hàng KM],0) as [7.4],isnull(dh.[8.Chuyển kho(-)],0) as [8],isnull(dh.[9.Chuyển kho(+)],0) as [9],isnull(dh.[10.Đề nghị đổi hàng],0) as [10],isnull(dh.[11.Xuất hàng NPP cấp 1],0) as [12] "
						 + "\n  ,(isnull([1.Tồn đầu],0)+isnull([2.Nhập hàng],0)-isnull([3.Hàng trả về NPP],0)-isnull([4.Xuất hàng bán],0)-isnull([5.Xuất hàng KM],0)+ isnull([6.Điều chỉnh],0)+isnull([7.1.KH Trả hàng bán],0)-isnull([7.2.Ban Cho NPP],0)+isnull([7.3.Mua Hang NPP],0)+isnull([7.4.KH Trả hàng KM],0)-isnull([8.Chuyển kho(-)],0)+isnull([9.Chuyển kho(+)],0)-isnull([10.Đề nghị đổi hàng],0) - isnull(dh.[11.Xuất hàng NPP cấp 1],0)) as [11] from "
						 + "\n  ("
						 + "\n  select * from [dbo].[ufn_XNT_TuNgay_DenNgay_FULL]("+rs.getString("npp_fk")+",'"+rs.getString("ngaybd")+"','2017-05-30')"
						 + "\n  ) as dh"
						 + "\n  pivot ("
						 + "\n  sum(soluong)"
						 + "\n  for type in ([1.Tồn đầu],[2.Nhập hàng],[3.Hàng trả về NPP],[4.Xuất hàng bán],[5.Xuất hàng KM],[6.Điều chỉnh],[7.1.KH Trả hàng bán],[7.2.Ban Cho NPP],[7.3.Mua Hang NPP],[7.4.KH Trả hàng KM],[8.Chuyển kho(-)],[9.Chuyển kho(+)],[10.Đề nghị đổi hàng],[11.Xuất hàng NPP cấp 1])"
						 + "\n  )"
						 + "\n  as dh"
						 + "\n  inner join SANPHAM sp on sp.PK_SEQ = dh.sanpham_fk"
						 + "\n  inner join KHO kh on kh.PK_SEQ = dh.kho_fk"
						 + "\n  inner join KENHBANHANG kbh on kbh.PK_SEQ = dh.kbh_fk"
						 + "\n "
						 + "\n inner join NHAPHANPHOI npp on npp.PK_SEQ  = dh.npp_fk"
						 + "\n ) a left join NHAPP_KHO b on a.kho_fk = b.KHO_FK"
						 + "\n and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK"
						 + "\n and a.NPP_FK = b.NPP_FK"
							+ "\n where a.[11] <> b.SOLUONG   " ;

					
					/*query = " "
							+ "\n select sp.ma as sanpham_fk,kho.soluong,kho.AVAILABLE,npp.ten as npp_fk,kho.kho_fk,kho.kbh_fk from nhapp_kho kho inner join nhaphanphoi npp on npp.pk_seq = kho.npp_fk inner join sanpham sp on sp.pk_seq = kho.sanpham_fk "
							+ " where kho.soluong >0 and not exists (select * from "
							 + "\n ("
							 + "\n select npp.ten as TenNPP,sp.ma,sp.ten as tensp, kbh.PK_SEQ as kbh_fk,npp.PK_SEQ as NPP_FK,sp.pk_seq as sanpham_fk,kh.PK_SEQ as kho_fk ,isnull(dh.[1.Tồn đầu],0) as [1],isnull(dh.[2.Nhập hàng],0) as [2] ,isnull(dh.[3.Hàng trả về NPP],0) as [3],isnull(dh.[4.Xuất hàng bán],0) as [4],isnull(dh.[5.Xuất hàng KM],0) as [5],isnull(dh.[6.Điều chỉnh],0) as [6],isnull(dh.[7.1.KH Trả hàng bán],0) as [7.1],isnull(dh.[7.2.Ban Cho NPP],0) as [7.2],"
							 + "\n  isnull(dh.[7.3.Mua Hang NPP],0) as [7.3],isnull(dh.[7.4.KH Trả hàng KM],0) as [7.4],isnull(dh.[8.Chuyển kho(-)],0) as [8],isnull(dh.[9.Chuyển kho(+)],0) as [9],isnull(dh.[10.Đề nghị đổi hàng],0) as [10],isnull(dh.[11.Xuất hàng NPP cấp 1],0) as [12]"
							 + "\n  ,(isnull([1.Tồn đầu],0)+isnull([2.Nhập hàng],0)-isnull([3.Hàng trả về NPP],0)-isnull([4.Xuất hàng bán],0)-isnull([5.Xuất hàng KM],0)+ isnull([6.Điều chỉnh],0)+isnull([7.1.KH Trả hàng bán],0)-isnull([7.2.Ban Cho NPP],0)+isnull([7.3.Mua Hang NPP],0)+isnull([7.4.KH Trả hàng KM],0)-isnull([8.Chuyển kho(-)],0)+isnull([9.Chuyển kho(+)],0)-isnull([10.Đề nghị đổi hàng],0)-[11.Xuất hàng NPP cấp 1]) as [11] from "
							 + "\n  ("
							 + "\n  select * from [dbo].[ufn_XNT_TuNgay_DenNgay_FULL]("+rs.getString("npp_fk")+",'"+rs.getString("ngaybd")+"','2017-03-31')"
							 + "\n  ) as dh"
							 + "\n  pivot ("
							 + "\n  sum(soluong)"
							 + "\n  for type in ([1.Tồn đầu],[2.Nhập hàng],[3.Hàng trả về NPP],[4.Xuất hàng bán],[5.Xuất hàng KM],[6.Điều chỉnh],[7.1.KH Trả hàng bán],[7.2.Ban Cho NPP],[7.3.Mua Hang NPP],[7.4.KH Trả hàng KM],[8.Chuyển kho(-)],[9.Chuyển kho(+)],[10.Đề nghị đổi hàng],[11.Xuất hàng NPP cấp 1])"
							 + "\n  )"
							 + "\n  as dh"
							 + "\n  inner join SANPHAM sp on sp.PK_SEQ = dh.sanpham_fk"
							 + "\n  inner join KHO kh on kh.PK_SEQ = dh.kho_fk"
							 + "\n  inner join KENHBANHANG kbh on kbh.PK_SEQ = dh.kbh_fk"
							 + "\n inner join NHAPHANPHOI npp on npp.PK_SEQ  = dh.npp_fk"
							 
							 + "\n ) a  where a.npp_fk = kho.npp_fk and a.sanpham_fk = kho.sanpham_fk and kho.kbh_fk = a.kbh_fk and a.kho_fk = kho.kho_fk ) and kho.npp_fk = "+rs.getString("npp_fk") ;*/
					
				ResultSet rscheck = db.get(query);
				if(rscheck != null)
				{	
					if(rscheck.next())
					{
						dem ++;
					
						//System.out.println(""+rscheck.getString("tensp")+" - "+rscheck.getString("TenNPP")+" - "+rscheck.getString("MASP")+" - "+rscheck.getString("slam"));
						
						System.out.println("CKSaiKho: "+query);
					}
				}
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("So NPP Sai XNT: "+dem);
		db.shutDown();
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
