package org.example.DTO;

public class TopProductsDTO {
    String maSP;
    String tenSP;
    int soLuong;

    public TopProductsDTO(){
        maSP = "";
        tenSP = "";
        soLuong = 0;
    }

    public TopProductsDTO(String maSP, String tenSP, int soLuong){
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
    }

    public String getMaSP(){
        return maSP;
    }

    public void setMaSP(String maSP){
        this.maSP = maSP;
    }

    public String getTenSP(){
        return maSP;
    }

    public void setTenSP(String tenSP){
        this.tenSP = tenSP;
    }

    public int getSoLuong(){
        return soLuong;
    }

    public void setSoLuong(int soLuong){
        this.soLuong = soLuong;
    }
}
