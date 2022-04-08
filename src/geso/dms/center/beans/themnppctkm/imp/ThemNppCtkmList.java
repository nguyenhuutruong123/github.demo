package geso.dms.center.beans.themnppctkm.imp;

import geso.dms.center.beans.themnppctkm.IThemNppCtkmList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThemNppCtkmList extends Phan_Trang implements IThemNppCtkmList
{
	
	 private static final long serialVersionUID = -2531965686472271660L;

		String userId;

		String Tungay;
		String Denngay;
		String nguoitao;
		String nguoisua;
		String trangthai;

		String msg;		
		String chanhoadonId;

		dbutils db;
		
		List<Object> dataSearch = new ArrayList<Object>(); 

		public ThemNppCtkmList()
		{
			this.userId = "";
			this.msg= "";

			this.Tungay = "";
			this.Denngay = "";
			this.nguoitao = "";
			this.nguoisua = "";
			this.trangthai = "";

			this.chanhoadonId = "";
			this.db = new dbutils();
		}

		public String getUserId()
		{
			return this.userId;
		}

		public void setUserId(String userId)
		{
			this.userId = userId;
		}

		public String getTungay()
		{
			return this.Tungay;
		}

		public void setTungay(String Tungay) 
		{
			this.Tungay = Tungay;
		}

		public String getDenngay()
		{
			return this.Denngay;
		}

		public void setDenngay(String Denngay) 
		{
			this.Denngay = Denngay;
		}

		public String getNguoitao()
		{
			return this.nguoitao;
		}

		public void setNguoitao(String nguoitao) 
		{
			this.nguoitao = nguoitao;
		}

		public String getNguoisua()
		{
			return this.nguoisua;
		}

		public void setNguoisua(String nguoisua) 
		{
			this.nguoisua = nguoisua;
		}

		public String getTrangthai()
		{
			return this.trangthai;
		}

		public void setTrangthai(String trangthai) 
		{
			this.trangthai = trangthai;
		}

		public String getMsg()
		{
			return this.msg;
		}

		public void setMsg(String msg)
		{
			this.msg = msg;
		}

		public void init(String query)
		{
			String sql =""; 
			if(query.length() > 0)
				sql = query;
			else
			{
				sql = 
			"		select npp.ma as nppMa,npp.ten as nppTEN, a.pk_Seq ,a.GhiChu,nt.TEN as NguoiTao,ns.TEN as NguoiSua,a.NgayTao,a.NgaySua,a.TrangThai "+    
			"			, STUFF   "+      
			"		(        "+
			"			(      "+
			"				select DISTINCT TOP 100 PERCENT ' , ' + chd.Scheme  "+     
			"				from ThemNppCtkm_Npp  chd   "+
			"				where chd.ThemNppCtkm_FK=a.pk_Seq  "+  
			"				ORDER BY ' , ' + chd.Scheme  "+      
			"				FOR XML PATH('')    "+    
			"			 ), 1, 2, ''       "+
			"		) + ' '  AS HoaDon    "+
			"		from ThemNppCtkm a inner join NHANVIEN nt on nt.PK_SEQ=a.NguoiTao " +
			"     inner join nhaphanphoi npp on npp.pk_Seq=a.npp_fk    "+   
			"		inner join NHANVIEN ns on ns.PK_SEQ=a.NguoiSua  "+    
			"		where 1=1  "  ;
			}
//			this.dataRs = super.createSplittingData(super.getItems(), super.getSplittings(), "pk_seq desc", sql);
			this.dataRs = super.createSplittingData_v2(db,super.getItems(), super.getSplittings(), "pk_seq desc", sql, dataSearch, "");
		}

		public void DbClose()
		{
			try
			{
				if(this.dataRs != null)
					this.dataRs.close();
				this.db.shutDown();
			}
			catch (SQLException e) {}
		}

		public ResultSet getdataRs()
		{
			return this.dataRs;
		}

		public void setdataRs(ResultSet dataRs)
		{
			this.dataRs = dataRs;
		}

		public String getChanhoadonId()
		{
			return this.chanhoadonId;
		}

		public void setChanhoadonId(String chanhoadonId)
		{
			this.chanhoadonId = chanhoadonId;
		}
		
		ResultSet dataRs;

		@Override
    public ResultSet getDataRs()
    {
	    return dataRs;
    }

		@Override
    public void setDataRs(ResultSet dataRs)
    {
	    this.dataRs=dataRs;
    }

		public List<Object> getDataSearch() {
			return dataSearch;
		}

		public void setDataSearch(List<Object> dataSearch) {
			this.dataSearch = dataSearch;
		}

	
}
