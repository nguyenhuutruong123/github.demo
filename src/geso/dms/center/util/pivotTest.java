package geso.dms.center.util;
import com.smartxls.*;
import com.smartxls.BookPivotRange;
import com.smartxls.BookPivotRangeModel;

public class pivotTest {

	void pivotTest() {
		 WorkBook workBook = new WorkBook();
	       try
	        {
	            workBook.read("d:\\pivotdatatest.xls");
	            BookPivotRangeModel model = workBook.getPivotModel();
				model.setList("A1:J16");
//				model.setLocation(0, 7, 5);

	            BookPivotRange pivotRange = model.getActivePivotRange();
	            BookPivotArea rowArea = pivotRange.getArea(BookPivotRange.row);
	            BookPivotArea columnArea = pivotRange.getArea(BookPivotRange.column);
	            BookPivotArea dataArea = pivotRange.getArea(BookPivotRange.data);
	            BookPivotArea pageArea = pivotRange.getArea(BookPivotRange.page);

	            BookPivotField macode = pivotRange.getField("Macode");
	            BookPivotField diengiai = pivotRange.getField("Diengiai");
	            BookPivotField ngay = pivotRange.getField("Ngay");
	            BookPivotField dat = pivotRange.getField("Dat");
	            BookPivotField giao = pivotRange.getField("Giao");
	            BookPivotField chenhlech = pivotRange.getField("ChenhLech");
	            BookPivotField kho = pivotRange.getField("Kho");
	            BookPivotField vung = pivotRange.getField("Vung");
	            BookPivotField khuvuc = pivotRange.getField("Khuvuc");
	            BookPivotField npp = pivotRange.getField("NPP");
	            
//	            pageArea.addField(macode);
//	            pageArea.addField(diengiai);
//	            pageArea.addField(ngay);
//	            pageArea.addField(dat);
//	            pageArea.addField(giao);
//	            pageArea.addField(chenhlech);
//	            pageArea.addField(kho);
	            pageArea.addField(vung);
	            pageArea.addField(khuvuc);
	            pageArea.addField(npp);
	            
	            rowArea.addField(kho);
	            rowArea.addField(macode);
	            rowArea.addField(diengiai);
	            rowArea.addField(ngay);	            
	            rowArea.addField(dat);
	            rowArea.addField(giao);
	            rowArea.addField(chenhlech);
	            rowArea.addField(vung);
	            rowArea.addField(khuvuc);
	            rowArea.addField(npp);
	            	            
//	            columnArea.addField(kho);
	            	                        
	            workBook.write("d:\\PivotTable.xls");
	            System.out.print("hoan thanh");
	        }
	        catch (Exception e)
	        {
	        	System.out.print(e.toString());
	            e.printStackTrace();
	        }

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// thuc hien pi vot
		pivotTest r = new pivotTest() ;
			r.pivotTest();
		
	}

}
