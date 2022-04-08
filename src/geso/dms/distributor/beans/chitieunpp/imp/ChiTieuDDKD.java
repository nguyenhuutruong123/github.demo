package geso.dms.distributor.beans.chitieunpp.imp;

import geso.dms.distributor.beans.chitieunpp.IChiTieuDDKD;

public class ChiTieuDDKD implements IChiTieuDDKD{
	 String Id;
		String DDKDId;
		String DDKDTen;
		double ChiTieu;
		String SoDonHang="";
		String SoSku="";
		@Override
		public void setID(String id) {
			// TODO Auto-generated method stub
		  this.Id=id;
		}

		@Override
		public String setID() {
			// TODO Auto-generated method stub
			return this.Id;
		}

		@Override
		public void setDDKDId(String ddkdId) {
			// TODO Auto-generated method stub
			this.DDKDId=ddkdId;
		}

		@Override
		public String getDDKDId() {
			// TODO Auto-generated method stub
			return this.DDKDId;
		}

		@Override
		public void setDDKDTen(String ddkdTen) {
			// TODO Auto-generated method stub
			this.DDKDTen=ddkdTen;
		}

		@Override
		public String getDDKDTen() {
			// TODO Auto-generated method stub
			return this.DDKDTen;
		}

		@Override
		public void setChiTieu(double chitieu) {
			// TODO Auto-generated method stub
			this.ChiTieu=chitieu;
		}


		public double getChiTieu() {
			// TODO Auto-generated method stub
			return this.ChiTieu;
		}

		@Override
		public void setSoDonHang(String sodonhang) {
			// TODO Auto-generated method stub
			this.SoDonHang=sodonhang;
		}

		@Override
		public String getSoDonHang() {
			// TODO Auto-generated method stub
			return this.SoDonHang;
		}

		@Override
		public void setSoSku(String sosku) {
			// TODO Auto-generated method stub
			this.SoSku=sosku;
		}

		@Override
		public String getSoSku() {
			// TODO Auto-generated method stub
			return this.SoSku;
		}
}
