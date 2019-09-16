package com.example.myapplication;

import java.util.ArrayList;

public class etkinlik {
    String olusturan;
    String tarih;
    String konum;
    String kontenjan;
    String detay;
    String ad;
    String sonraid;
    String type;

    public String getSonraid() {
        return sonraid;
    }

    public void setSonraid(String sonraid) {
        this.sonraid = sonraid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    ArrayList<String> katilimcilar;

    public String getOlusturan() {
        return olusturan;
    }

    public void setOlusturan(String olusturan) {
        this.olusturan = olusturan;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getKonum() {
        return konum;
    }

    public void setKonum(String konum) {
        this.konum = konum;
    }

    public String getKontenjan() {
        return kontenjan;
    }

    public void setKontenjan(String kontenjan) {
        this.kontenjan = kontenjan;
    }

    public String getDetay() {
        return detay;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public ArrayList<String> getKatilimcilar() {
        return katilimcilar;
    }

    public void setKatilimcilar(ArrayList<String> katilimcilar) {
        this.katilimcilar = katilimcilar;
    }
}
