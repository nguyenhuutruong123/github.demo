package geso.dms.center.beans.menu;

public interface IMenu {
   public void setuserId(String userId);
   public String getuserId();
   public void setMang(int[] mang);
   public int[] getMang();
   public void init();
   public String  getNppNhanvien();
   public String getKenhnNhanvien();
   public String getSanphamNhanvien();
}
