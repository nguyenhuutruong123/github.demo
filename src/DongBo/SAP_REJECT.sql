


insert DMSTEST.dbo.ERP_Dondathang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, 
	nguoitao, ngaysua, nguoisua,NguonGoc_TaoDH,Revert_DonDatHang_FK)
select '2015-08-07','2015-08-07',0,N'Đơn hàng Reject (100090)' as GhiChu,0,
	a.DVKD_FK,a.KBH_FK,a.NPP_FK,a.Kho_FK,0 as ck,0 as VAT,'2015-08-07',NGUOITAO,'2015-08-07',NGUOISUA,N'Reject 100090',100090
from DMSTEST.dbo.ERP_Dondathang a
where PK_SEQ=100090

select * from 

insert DMSTEST.dbo.ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, 
	soluong, soluongttduyet, dvdl_fk,thueVAT,dongia,DonGiaGoc ) 
select (select SCOPE_IDENTITY()),
		(select pk_seq from DMSTEST.dbo.sanpham  where ma=dms.Product_ID) as SANPHAM_FK,
		(-1)*ISNULL(dms.Quantity,0)-ISNULL(hd.Quantity,0) as Quantity,
		(-1)*ISNULL(dms.Quantity,0)-ISNULL(hd.Quantity,0) as Quantity,
		(select dvdl_fk from DMSTEST.dbo.sanpham  where ma=dms.Product_ID) as dvdl_fk,
		(select ptThue from DMSTEST.dbo.sanpham  where ma=dms.Product_ID) as ptThue,
isnull( ( select GIAMUANPP from 
		DMSTEST.dbo.BGMUANPP_SANPHAM sp where SANPHAM_FK = dms.sanpham_fk and BGMUANPP_FK in 
(	select top(1) PK_SEQ from DMSTEST.dbo.BANGGIAMUANPP bg 
		inner join DMSTEST.dbo.BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK 
			where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = 
			(  select NPP_FK from  DMSTEST.dbo.ERP_DONDATHANG where PK_SEQ=100090) 
			
			
			and bg.DVKD_FK =(  select DVKD_FK from  DMSTEST.dbo.ERP_DONDATHANG where PK_SEQ=100090)  
			and bg.KENH_FK = (  select KBH_FK from  DMSTEST.dbo.ERP_DONDATHANG where PK_SEQ=100090) 
				 order by bg.TUNGAY desc ) 
),0 ) as giamua,
isnull( ( select GIAMUANPP from 
		DMSTEST.dbo.BGMUANPP_SANPHAM sp where SANPHAM_FK = dms.sanpham_fk and BGMUANPP_FK in 
(	select top(1) PK_SEQ from DMSTEST.dbo.BANGGIAMUANPP bg 
		inner join DMSTEST.dbo.BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK 
			where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = 
			(  select NPP_FK from  DMSTEST.dbo.ERP_DONDATHANG where PK_SEQ=100090) 
			
			
			and bg.DVKD_FK =(  select DVKD_FK from  DMSTEST.dbo.ERP_DONDATHANG where PK_SEQ=100090)  
			and bg.KENH_FK = (  select KBH_FK from  DMSTEST.dbo.ERP_DONDATHANG where PK_SEQ=100090) 
				 order by bg.TUNGAY desc ) 
),0 )
from SAP_SO_REJECT sap
inner join 
(
	select b.dondathang_fk as PO_Number , c.MA as Product_ID ,SUM(b.soluongttduyet) as Quantity,b.sanpham_fk 
	from DMSTEST.dbo.ERP_DONDATHANG a 
		inner join DMSTEST.dbo.ERP_DONDATHANG_SANPHAM b on b.dondathang_fk=a.PK_SEQ
		inner join DMSTEST.dbo.sanpham c on c.PK_SEQ=b.sanpham_fk
	where a.PK_SEQ=100090
	group by b.dondathang_fk,c.MA,b.sanpham_fk
)as dms on sap.DMS_PO_Number=dms.PO_Number
left join 
(
	select sum(InVoice_BVAT+Invoice_VAT) as Amount,PO_Number ,b.Product_ID,sum(b.Invoice_Quantity) as Quantity
	from SAP_Invoice_Header a inner join SAP_Invoice_Item b on b.InVoiceID=a.InvoiceId
	where a.PO_Number=100090 
		and a.InvoiceId not in (select Invoice_RevertFor from SAP_Invoice_Header where Invoice_RevertFor is not null)
	group by b.Product_ID,a.PO_Number
)as hd on hd.PO_Number=sap.DMS_PO_Number and dms.Product_ID=hd.Product_ID
where ISNULL(dms.Quantity,0)-ISNULL(hd.Quantity,0)>0


select * from DMSTEST.dbo.ERP_Dondathang 
alter table ERP_Dondathang add Revert_DonDatHang_FK numeric(18,0)
