package geso.dms.center.servlets.donmuahang;
import java.io.*;
import java.text.*;
import java.awt.*;
import javax.swing.*;
import org.openswing.swing.message.receive.java.*;
import org.openswing.swing.pivottable.aggregators.java.*;
import org.openswing.swing.pivottable.client.*;
import org.openswing.swing.pivottable.functions.java.*;
import org.openswing.swing.pivottable.java.*;
import org.openswing.swing.pivottable.server.*;
import org.openswing.swing.pivottable.tablemodelreaders.server.*;
import org.openswing.swing.util.dataconverters.java.*;
import org.openswing.swing.client.*;
import java.awt.event.*;

public class GridFrame extends JFrame
{
	  private static final long serialVersionUID = 1L;
	  
	  PivotTable pivotTable = new PivotTable();
	  JPanel buttonsPanel = new JPanel();
	  FlowLayout flowLayout1 = new FlowLayout();
	  ReloadButton reloadButton1 = new ReloadButton();
	  ExportButton exportButton1 = new ExportButton();
	  FilterButton filterButton1 = new FilterButton();
	  JToggleButton expRowButton = new JToggleButton("Expand/collapse row");
	  JToggleButton expColButton = new JToggleButton("Expand/collapse column");	  

	  @SuppressWarnings({ "static-access", "unchecked" })
	  public GridFrame() 
	  {
		  try 
		  {
		      super.setDefaultCloseOperation(super.EXIT_ON_CLOSE);
		      jbInit();
		      setSize(1000,650);
		      
		      setTitle("Pivot Table for sales geso");
		      setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		     
		      CSVFileReader reader = new CSVFileReader(
			          new File("orders.txt"),
					  new String[]{"MaCode", "DienGiai", "Ngay", "Dat", "Giao", "ChenhLech", "Kho", "Vung", "KhuVuc", "NPP"},			          
			          new DataConverter[]{
			        	new DataConverter(),
			        	new DataConverter(),
			        	new StringToDateConverter(new SimpleDateFormat("yyyy-MM-dd")),
			            new StringToDoubleConverter(),
			            new StringToDoubleConverter(),
			            new StringToDoubleConverter(),			            			          
			            new DataConverter(),
			            new DataConverter(),
			            new DataConverter(),
			            new DataConverter(),
			          }
			      );
	      final PivotTableEngine engine = new PivotTableEngine(reader);

	      pivotTable.setController(new PivotTableController()
	      {
		    public Response getPivotTableModel(PivotTableParameters pars)
		    {
		      long t = System.currentTimeMillis();
		      Response res = engine.getPivotTableModel(pars);
		      System.out.println("Analysis time: "+(System.currentTimeMillis()-t)+"ms");
		      return res;
		    }
	      });

	      RowField rowFieldMacode = new RowField("MaCode",100);
	      RowField rowFieldDiengiai = new RowField("DienGiai",200);

	      RowField rowFieldNpp = new RowField("NPP",120);
	      RowField rowFieldKv = new RowField("KhuVuc",100);
	      RowField rowFieldVung = new RowField("Vung",100);
	      //RowField rowFieldRegion = new RowField("region",100);
	
	      //nhung row se hien trong Filter
	      pivotTable.getAllRowFields().add(rowFieldNpp);
	      pivotTable.getAllRowFields().add(rowFieldKv);
	      pivotTable.getAllRowFields().add(rowFieldVung);
	      pivotTable.getAllRowFields().add(rowFieldMacode);
	      pivotTable.getAllRowFields().add(rowFieldDiengiai);
	      //pivotTable.getAllRowFields().add(rowFieldRegion);
	      
	      //row mac dinh se hien
	      pivotTable.addRowField(rowFieldNpp);
	      pivotTable.addRowField(rowFieldKv);
	      pivotTable.addRowField(rowFieldVung);
	      pivotTable.addRowField(rowFieldDiengiai);
	
	      ColumnField columnFieldNd = new ColumnField("NgayDat","NgayDat",new YearAggregator());
	      ColumnField columnFieldKho = new ColumnField("Kho");
	      ColumnField columnFieldKv = new ColumnField("KhuVuc");
	      ColumnField columnFieldVung = new ColumnField("Vung");
	      //ColumnField columnFieldMacode = new ColumnField("MaCode");
	
	      pivotTable.getAllColumnFields().add(columnFieldKho);
	      pivotTable.getAllColumnFields().add(columnFieldNd);
	      pivotTable.getAllColumnFields().add(columnFieldKv);
	      pivotTable.getAllColumnFields().add(columnFieldVung);      
	      
	      pivotTable.addColumnField(columnFieldKho);
	      //pivotTable.addColumnField(columnFieldKv);
	           
	      //Field tinh toan
	      DataField dataFieldSellDat = new DataField("Dat",90,"Dat",new SumFunction()); // sum function...     
	      DataField dataFieldSellGiao = new DataField("Giao",90,"Giao",new SumFunction());  // sum function...
	      DataField dataFieldChenhLech = new DataField("ChenhLech",90,"ChenhLech",new SumFunction());
	
	      pivotTable.getAllDataFields().add(dataFieldSellDat);
	      pivotTable.getAllDataFields().add(dataFieldSellGiao);
	      pivotTable.getAllDataFields().add(dataFieldChenhLech);
	      
	     // pivotTable.getAllDataFields().add(dataFieldMax);
	          
	      pivotTable.addDataField(dataFieldSellDat);
	      pivotTable.addDataField(dataFieldSellGiao);
	      pivotTable.addDataField(dataFieldChenhLech);
      
	      pivotTable.setDataFieldRenderer(new DataFieldRenderer() 
	      {
	    	  
	        public Color getBackgroundColor(Color currentColor,GenericNodeKey rowPath,GenericNodeKey colPath,Object value,int row,int col) {
	          if (rowPath.getPath().length<pivotTable.getPivotTableParameters().getRowFields().size() ||
	              colPath.getPath().length<pivotTable.getPivotTableParameters().getColumnFields().size()+1) {
	            int c = 200+rowPath.getPath().length*colPath.getPath().length*5;
	            return new Color(c,c,c);
	          }
	          return currentColor;
	        }
	
	        public Color getForegroundColor(Color currentColor,GenericNodeKey rowPath,GenericNodeKey colPath,Object value,int row,int col) {
	          return currentColor;
	        }
	
	        public Font getFont(Font currentFont,GenericNodeKey rowPath,GenericNodeKey colPath,Object value,int row,int col) {
	          if (rowPath.getPath().length<pivotTable.getPivotTableParameters().getRowFields().size() ||
	              colPath.getPath().length<pivotTable.getPivotTableParameters().getColumnFields().size()+1)
	            return new Font(currentFont.getFontName(),Font.BOLD,currentFont.getSize());
	          return currentFont;
	        }	
	      });
	      
	      setVisible(true);
		  }
		  catch(Exception e) {e.printStackTrace();}
	  }

	  private void jbInit() throws Exception 
	  {
	    buttonsPanel.setLayout(flowLayout1);
	    flowLayout1.setAlignment(FlowLayout.LEFT);
	    pivotTable.setExportButton(exportButton1);
	    pivotTable.setFilterButton(filterButton1);
	    pivotTable.setReloadButton(reloadButton1);
	    
	    expRowButton.addActionListener(new GridFrame_expRowButton_actionAdapter(this));
	    expColButton.addActionListener(new GridFrame_expColButton_actionAdapter(this));
	    this.getContentPane().add(pivotTable, BorderLayout.CENTER);
	    this.getContentPane().add(buttonsPanel, BorderLayout.NORTH);
	    buttonsPanel.add(reloadButton1, null);
	    buttonsPanel.add(exportButton1, null);
	    buttonsPanel.add(filterButton1, null);
	    buttonsPanel.add(expRowButton, null);
	    buttonsPanel.add(expColButton, null);
	    
	    pivotTable.setBackground(Color.GREEN.brighter());
	    pivotTable.setAutoscrolls(true);
	    pivotTable.setAlignmentX(CENTER_ALIGNMENT);
	  }

	  void expRowButton_actionPerformed(ActionEvent e) 
	  {
	    if (pivotTable.getSelectedRow()==-1)
	      return;
	    if (expRowButton.isSelected())
	      pivotTable.expandRow(pivotTable.getSelectedRow());
	    else
	      pivotTable.collapseRow(pivotTable.getSelectedRow());
	  }

	  void expColButton_actionPerformed(ActionEvent e)
	  {
	    if (pivotTable.getSelectedColumn()== -1 )
	      return;
	    if (expColButton.isSelected())
	      pivotTable.expandColumn(pivotTable.getSelectedColumn());
	    else
	      pivotTable.collapseColumn(pivotTable.getSelectedColumn());
	  }
	  
}

class GridFrame_expRowButton_actionAdapter implements java.awt.event.ActionListener 
{
	  GridFrame adaptee;
	
	  GridFrame_expRowButton_actionAdapter(GridFrame adaptee) 
	  {
	    this.adaptee = adaptee;
	  }
	  public void actionPerformed(ActionEvent e)
	  {
	    adaptee.expRowButton_actionPerformed(e);
	  }
}

class GridFrame_expColButton_actionAdapter implements java.awt.event.ActionListener 
{
	  GridFrame adaptee;
	
	  GridFrame_expColButton_actionAdapter(GridFrame adaptee)
	  {
	    this.adaptee = adaptee;
	  }
	  public void actionPerformed(ActionEvent e) 
	  {
	    adaptee.expColButton_actionPerformed(e);
	  }
}

