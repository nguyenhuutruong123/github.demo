package geso.dms.center.beans.stockintransit;

import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface IStockintransit 
{
	public String getView() ;
	public void setView(String view) ;
	public String getTuyenbhId() ;

	public void setTuyenbhId(String tuyenbhId);
	public String getNhomskusId();

	public void setNhomskusId(String nhomskusId);

	public ResultSet getNhomskus();

	public void setNhomskus(ResultSet nhomskus);
	public void setDMQid(String dmqid);
	public String getDMQid();
	public void setTenDMQ(String tendmq);
	public String getTenDMQ();

	public void setuserId(String userId);

	public String getuserId();

	public void setuserTen(String userTen);

	public String getuserTen();

	public void setnppId(String nppId);

	public String getnppId();

	public void setnppTen(String nppTen);

	public String getnppTen();
	public String getUrl();
	public void setUrl(String url);
	public String getKey();
	public void setKey(String key);
	public void setkenhId(String kenhId);

	public String getkenhId();
	
	public String getTrangthaispId() ;

	public void setTrangthaispId(String trangthaispId) ;

	public void setdvkdId(String dvkdId);

	public String getdvkdId();

	public void setnhanhangId(String nhanhangId);

	public String getnhanhangId();

	public void setchungloaiId(String chungloaiId);

	public String getchungloaiId();
	public ResultSet GetDlnRs();
	public void setloainppId(String loainppId);

	public String getloainppId();
	public void setDlnId(String DlnId);
	public String getDlnId();

	public void settungay(String tungay);

	public String gettungay();

	public void setdenngay(String denngay);

	public String getdenngay();

	public void settumuc(String tumuc);

	public String gettumuc();

	public void setdenmuc(String denmuc);

	public String getdenmuc();

	public void setMsg(String msg);

	public String getMsg();

	public void setkenh(ResultSet kenh);

	public ResultSet getkenh();

	public void setdvkd(ResultSet dvkd);

	public ResultSet getdvkd();

	public ResultSet getcttbRs();	
	public void setcttbId(String cttbId);
	public String getcttbId();
	public void setMultiCttbId(String[] arr);
	public void setnhanhang(ResultSet nhanhang);
	public ResultSet getTieuchithuongTlRs();
	public ResultSet getnhanhang();

	public void setchungloai(ResultSet chungloai);

	public ResultSet getchungloai();

	public void setloainpp(ResultSet loainpp);

	public ResultSet getloainpp();

	public void setkhoId(String khoId);

	public String getkhoId();

	public void setkho(ResultSet kho);

	public ResultSet getkho();

	public void setbook(String book);

	public String getbook();

	public void setdiscount(String discount);

	public String getdiscount();

	public void setpromotion(String promotion);

	public String getpromotion();

	public void setvungId(String vungId);

	public String getvungId();

	public void setvung(ResultSet vung);

	public ResultSet getvung();

	public void setthuong(ResultSet thuong);

	public ResultSet getthuong();


	public void setthuongId(String thuongId);

	public String getthuongId();

	public void setkhuvucId(String khuvucId);

	public String getkhuvucId();

	public void setkhuvuc(ResultSet khuvuc);

	public ResultSet getkhuvuc();

	public void setNhomkhId(String nhomkhId);

	public String getNhomkhId();

	public void setNhomkh(ResultSet nhomkh);

	public ResultSet getNhomkh();

	public void setnpp(ResultSet npp);

	public ResultSet getnpp();

	public void setgsbhId(String gsbhId);

	public String getgsbhId();

	public void setnvgnId(String nvgnId);

	public String getnvgnId();

	public void setASMId(String asmId);

	public String getASMId();

	public void setBMId(String bmId);

	public String getBMId();

	public void setnvgn(ResultSet nvgn);

	public ResultSet getnvgn();

	public void setgsbh(ResultSet gsbh);

	public ResultSet getgsbh();

	public void setASM(ResultSet asm);

	public ResultSet getASM();

	public void setBM(ResultSet bm);

	public ResultSet getBM();	

	public void setsanphamId(String sanphamId);

	public String getsanphamId();

	public void setsanpham(ResultSet sanpham);

	public ResultSet getsanpham();

	public void setdvdlId(String dvdlId);

	public String getdvdlId();

	public void setdvdl(ResultSet dvdl);

	public ResultSet getdvdl();

	public void setFieldShow(String[] fieldShow);

	public String[] getFieldShow();

	public void setFieldHidden(String[] fieldHidden);

	public String[] getFieldHidden();


	public String getDateTime();
	public String getdate();

	public void init();

	public void DBclose();

	public void setngayton(String ngayton);

	public String getngayton();

	public void setvat(String vat);

	public String getvat();

	public void setlessday(String lessday);

	public String getlessday();

	public void setmoreday(String moreday);

	public String getmoreday();

	// Danh muc dai dien kinh doanh
	public void setDdkd(String ddkd);

	public String getDdkd();

	public void setRsddkd(ResultSet rs);

	public ResultSet getRsddkd();


	public void setUnit(String unit);

	public String getUnit();

	public void setGroupCus(String groupCus);

	public String getGroupCus();

	public void setPrograms(String programs);

	public String getPrograms();

	public void setRsPrograms(ResultSet RsPrograms);

	public ResultSet getRsPrograms();

	public void setDonViTinh(String donviTinh);

	public String getDonViTinh();
	public void setMonth(String month);

	public String getMonth();

	public void setYear(String year);
	public String getYear();

	public void setFromMonth(String month);
	public String getFromMonth();
	public void setToMonth(String month);
	public String getToMonth();

	public void setUnghang(String unghang);
	public String getUnghang();	

	public void setcovat(String covat);
	public String getcovat();	

	public void setDHchot(String dhchot);
	public String getDHchot();	

	public void settype(String unghang);
	public String gettype();

	public void setchon(String chon);
	public String getchon();

	public void setFromYear(String fromyear);
	public String getFromYear();

	public void setToYear(String toyear);
	public String getToYear();

	public void SetNhoSPId(String nhomspid);
	public String GetNhoSPId();

	public void SetQuyCachId(String QuyCachId);
	public String GetQuyCachId();

	public void setRsNhomSP(ResultSet rs);

	public void setCheck(String check);
	public String getCheck();

	public ResultSet GetRsNhomSP();

	public void setRsQuyCach(ResultSet QuyCach);

	public ResultSet GetRsQuyCach();

	public ResultSet getNhomspRs();
	public void setNhomspRs(ResultSet nspRs);
	public String getNspId();
	public void setNspId(String nspId);

	public String getLoaiMenu();
	public void setLoaiMenu(String loaiMenu) ;
	public void setPhanloai(String pl);

	public String getPhanloai();

	public void setDhChot(String dhchot);

	public String getDhChot();

	public void setxemtheo(String xemtheo);
	public String getxemtheo();

	public void setTask(String task);
	public String getTask();


	public String getTinhthanhid();

	public void setTinhthanhid(String tinhthanhid);
	public ResultSet GetTinhThanh();
	public void setSpId(String spid);
	public String getSpId();
	public void setkhId(String khId);
	public String getkhId();

	public void setLoaiNv(String LoaiNv) ;
	public String getLoaiNv() ;
	public ResultSet getAnhtrungbayRs();
	public void setAnhtrungbayRs(String query);
	public ResultSet getRstuyenbh();

	public void setRstuyenbh(ResultSet rstuyenbh);

	public String getTtId() ;
	public void setTtId(String ttId) ;
	public dbutils getDb() ;
	
	public ArrayList<String> getArr_text_baocaoSR() ;
	public void setArr_text_baocaoSR(ArrayList<String> arr_text_baocaoSR) ;
	public String getText_baocaoSR() ;
	public void setText_baocaoSR(String text_baocaoSR) ;
	public void initChuyenTuyen();
	public void initUploadMCP();
	public void init_user();

	public ResultSet vungRs_create();
	public ResultSet khuvucRs_create();
	public ResultSet nppRs_create();
	public ResultSet kbhRs_create();
	public ResultSet ddkdRs_create();
	public ResultSet bmRs_Create();
	public ResultSet asmRs_Create();
	public ResultSet gsbhRs_Create();
	
	
}

