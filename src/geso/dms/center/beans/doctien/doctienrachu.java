package geso.dms.center.beans.doctien;

public class doctienrachu {
	//Trả về tên gọi của một số ở hàng đơn vị, hàng trăm.
	private String numberToTextA(int number) {
	String sR = "";
	switch (number) {
	case 0: sR = "không"; break;
	case 1: sR = "một"; break;
	case 2: sR = "hai"; break;
	case 3: sR = "ba"; break;
	case 4: sR = "bốn"; break;
	case 5: sR = "năm"; break;
	case 6: sR = "sáu"; break;
	case 7: sR = "bảy"; break;
	case 8: sR = "tám"; break;
	case 9: sR = "chín"; break;
	default: sR = "";
	}
	return sR;
	}

	// Tên gọi của các chữ số
	static private String[] chuSo = {"không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
	// Tên gọi đơn vị của các nhóm số (tính từ phải sang trái)
	static private String[] donViNhom = {"", "nghìn", "triệu ", "tỉ"};


	private String ChuyenDV(String Number){
	String sNumber = "";
	int len = Number.length();
	if(len==1){
	int iNu = Integer.parseInt("" + Number.charAt(0));
	sNumber += numberToTextA(iNu);
	}else if(len==2){
	int iChuc = Integer.parseInt("" + Number.charAt(0));
	int iDV = Integer.parseInt("" + Number.charAt(1));
	if(iChuc==1){
	if(iDV > 0){
	sNumber += "Mười " + numberToTextA(iDV);
	}else{
	sNumber += "Mười ";
	}
	}else{
	sNumber += numberToTextA(iChuc) + " mươi " + numberToTextA(iDV);
	}
	}else{
	int iTram = Integer.parseInt("" + Number.charAt(0));
	int iChuc = Integer.parseInt("" + Number.charAt(1));
	int iDV = Integer.parseInt("" + Number.charAt(2));

	if(iChuc==0){
	if(iDV >0){
	sNumber += numberToTextA(iTram) + " trăm linh " + numberToTextA(iDV);
	}else{
	sNumber += numberToTextA(iTram) + " trăm";
	}
	}else if(iChuc==1){
	if(iDV > 0){
	sNumber += numberToTextA(iTram) + " trăm mười " + numberToTextA(iDV);
	}else{
	sNumber += numberToTextA(iTram) + " trăm mười ";
	}
	}else{
	if(iDV > 0){
	sNumber += numberToTextA(iTram) + " trăm " + numberToTextA(iChuc) + " mươi " + numberToTextA(iDV) ;
	}else{
	sNumber += numberToTextA(iTram) + " trăm " + numberToTextA(iChuc) + " mươi " ;
	}
	}
	}
	return sNumber;
	}
	//Phương thức chuyển đổi
	public String tranlate(String sNumber) {


	String sR = "";
	String sR1 = "";
	String sR2 = "";
	String sR3 = "";
	String sR4 = "";
	// sR = ChuyenDV(sNumber);

	int seq=0;
	int k = 1;
	for(int i = sNumber.length() ; i >= 0 ; i--){
	if(seq == 3){
	String subStr = sNumber.substring(i, i + seq);
	if(k==1){
	sR = ChuyenDV(subStr) + " đồng";
	}else if(k==2){
	sR1 = ChuyenDV(subStr) + " nghìn ";
	}else if(k==3){
	sR2 = ChuyenDV(subStr) + " triệu ";
	}else{
	sR3 = ChuyenDV(subStr) + " tỷ ";
	}
	seq = 0;
	k++;
	}
	seq++;
	}
	if(seq > 1){
	String subStr = sNumber.substring(0, seq - 1);
	if(k==1){
	sR = ChuyenDV(subStr) + " đồng";
	}else if(k==2){
	sR1 = ChuyenDV(subStr) + " nghìn ";
	}else if(k==3){
	sR2 = ChuyenDV(subStr) + " triệu ";
	}else{
	sR3 = ChuyenDV(subStr) + " tỷ ";
	}
	}
	//seq
	sR4 = sR3 + sR2 + sR1 + sR;

	return sR4;
	}
	static public String docTien(long soTienCanDoc){
		String bangChu = ""; // chứa kết quả đọc số
		
		// duyệt từng nhóm số (mỗi nhóm 3 chữ số)
		for(int i=0; soTienCanDoc > 0; i++){
			// tách lấy 3 chữ số cuối
			int hangDonVi = (int)(soTienCanDoc % 10);
			int hangChuc = (int)((soTienCanDoc / 10) % 10);
			int hangTram = (int)((soTienCanDoc / 100) % 10);
			
			// đọc hàng đơn vị
			String nhomDocLa = chuSo[hangDonVi]; // đọc số 1 chữ số
			
			// đọc hàng chục nếu có
			if(soTienCanDoc > 9){
				nhomDocLa = chuSo[hangChuc] + " mươi " + nhomDocLa; // đọc số 2 chữ số
				// hiệu chỉnh kết quả đọc số có 2 chữ số
				nhomDocLa = nhomDocLa
						.replace("không mươi không", "")
						.replace("không mươi", "lẻ")
						.replace("mươi không", "mươi")
						.replace("mươi một", "mươi mốt")
						.replace("mươi năm", "mươi lăm")
						.replace("một mươi", "mười")
						.replace("mười mốt", "mười một")
						.replace("lẻ mốt", "lẻ một")
						.replace("lẻ lăm", "lẻ năm");
			}
			
			// đọc hàng trăm nếu có
			if(soTienCanDoc > 99){
				nhomDocLa = chuSo[hangTram] + " trăm " + nhomDocLa; // đọc số 3 chữ số

				// hiệu chỉnh kết quả đọc số có 3 chữ số
				if(nhomDocLa.trim().equals("không trăm")){
					nhomDocLa = "";
				}
			}
			
			// hiệu chỉnh và bổ sung đơn vị nhóm
			i = (i == 4) ? 1 : i;
			nhomDocLa = nhomDocLa + " " + donViNhom[i];
			
			// bổ sung đọc nhóm vào kết quả
			bangChu = nhomDocLa + " " + bangChu;
			
			// phần còn lại (loại 3 số cuối) của số cần đọc
			soTienCanDoc = soTienCanDoc / 1000;
		}
		
		// hiệu chỉnh kết quả đọc được lần cuối
		bangChu = bangChu
				.replaceAll("\\s+", " ")
				.replaceAll("tỉ triệu nghìn", "tỉ")
				.replaceAll("tỉ triệu", "tỉ")
				.replaceAll("triệu nghìn", "triệu");
		
		if(bangChu.trim().length() == 0) {
			bangChu = "không ";
		}
		bangChu = bangChu.substring(0, 1).toUpperCase() + bangChu.substring(1, bangChu.length());
		
		return bangChu + "đồng";
	}

}
