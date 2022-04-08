// JScript File
        function FormatNumber(obj) {
        var strvalue;
        if (eval(obj))
		    strvalue = eval(obj).value;
	    else
		    strvalue = obj;	
        var num;
            num = strvalue.toString().replace(/\$|\,/g,'');

            if(isNaN(num))
            num = "";
            sign = (num == (num = Math.abs(num)));
            num = Math.floor(num*100+0.50000000001);
            num = Math.floor(num/100).toString();
            for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
            num = num.substring(0,num.length-(4*i+3))+','+
            num.substring(num.length-(4*i+3));
            //return (((sign)?'':'-') + num);
            eval(obj).value = (((sign)?'':'-') + num);
        }
    var ChuSo=new Array(" Không "," Một "," Hai "," Ba "," Bốn "," Năm "," Sáu "," Bảy "," Tám "," Chín ");
    var Tien=new Array( "", " Nghìn", " Triệu", " Tỷ", " Nghìn Tỷ", " Triệu Tỷ");
    function DocSo3ChuSo(baso)
    {
        var tram;
        var chuc;
        var donvi;
        var KetQua="";
        tram=parseInt(baso/100);
        chuc=parseInt((baso%100)/10);
        donvi=baso%10;
        if(tram==0 && chuc==0 && donvi==0) return "";
        if(tram!=0)
        {
            KetQua += ChuSo[tram] + " Trăm ";
            if ((chuc == 0) && (donvi != 0)) KetQua += " Linh ";
        }
        if ((chuc != 0) && (chuc != 1))
        {
                KetQua += ChuSo[chuc] + " Mươi";
                if ((chuc == 0) && (donvi != 0)) KetQua = KetQua + " Linh ";
        }
        if (chuc == 1) KetQua += " Mười ";
        switch (donvi)
        {
            case 1:
                if ((chuc != 0) && (chuc != 1))
                {
                    KetQua += " Mốt ";
                }
                else
                {
                    KetQua += ChuSo[donvi];
                }
                break;
            case 5:
                if (chuc == 0)
                {
                    KetQua += ChuSo[donvi];
                }
                else
                {
                    KetQua += " Lăm ";
                }
                break;
            default:
                if (donvi != 0)
                {
                    KetQua += ChuSo[donvi];
                }
                break;
            }
        return KetQua;
    }
    function DocTienBangChu(SoTien)
    {
        var lan=0;
        var i=0;
        var so=0;
        var KetQua="";
        var tmp="";
        var ViTri = new Array();
        //if(SoTien<0) return "Âm "+KetQua;
        if(SoTien==0) return "Không ";
        if(SoTien>0)
        {
            so=SoTien;
        }
        else
        {
            so = -SoTien;
        }
        if (SoTien > 8999999999999999)
        {
            //SoTien = 0;
            return "Số quá lớn!";
        }

        ViTri[5] = Math.floor(so / 1000000000000000);
        if(isNaN(ViTri[5]))
            ViTri[5] = "0";
        so = so - parseFloat(ViTri[5].toString()) * 1000000000000000;
        ViTri[4] = Math.floor(so / 1000000000000);
         if(isNaN(ViTri[4]))
            ViTri[4] = "0";
        so = so - parseFloat(ViTri[4].toString()) * 1000000000000;
        ViTri[3] = Math.floor(so / 1000000000);
         if(isNaN(ViTri[3]))
            ViTri[3] = "0";
        so = so - parseFloat(ViTri[3].toString()) * 1000000000;
        ViTri[2] = parseInt(so / 1000000);
         if(isNaN(ViTri[2]))
            ViTri[2] = "0";
        ViTri[1] = parseInt((so % 1000000) / 1000);
         if(isNaN(ViTri[1]))
            ViTri[1] = "0";
        ViTri[0] = parseInt(so % 1000);
      if(isNaN(ViTri[0]))
            ViTri[0] = "0";
        if (ViTri[5] > 0)
        {
            lan = 5;
        }
        else if (ViTri[4] > 0)
        {
            lan = 4;
        }
        else if (ViTri[3] > 0)
        {
            lan = 3;
        }
        else if (ViTri[2] > 0)
        {
            lan = 2;
        }
        else if (ViTri[1] > 0)
        {
            lan = 1;
        }
        else
        {
            lan = 0;
        }
//        
        for (i = lan; i >= 0; i--)
        {
           tmp = DocSo3ChuSo(ViTri[i]);
           KetQua += tmp;
           if (ViTri[i] > 0) KetQua += Tien[i];
           if ((i > 0) && (tmp.length > 0)) KetQua += ',';//&& (!string.IsNullOrEmpty(tmp))
        }
       if (KetQua.substring(KetQua.length - 1) == ',')
       {
            KetQua = KetQua.substring(0, KetQua.length - 1);
       }
       
       KetQua = KetQua.substring(1,2).toUpperCase()+ KetQua.substring(2);
       if(SoTien>0)
       return KetQua;//.substring(0, 1);//.toUpperCase();// + KetQua.substring(1);
       else if(SoTien<0)
       return "Âm "+KetQua;
    }
 
 function formatCurrency(num) 
 {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    num = Math.floor(num/100).toString();
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num);
}