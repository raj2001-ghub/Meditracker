package com.example.meditracker;


public class person {
    private  int agep;
    private String namep;
    private String sexp;
    private double heightp;
    private double weightp;
    private String bloodgroupp;



    public person (String name,int age,String sex,double height,double weight,String bloodgroup)
    {
        namep=name;
        agep=age;
        sexp=sex;
        heightp=height;
        weightp=weight;
        bloodgroupp=bloodgroup;
    }


    public int getAgep() {
        return agep;
    }

    public String getNamep() {
        return namep;
    }

    public String getSexp() {
        return sexp;
    }

    public double getHeightp() {
        return heightp;
    }

    public double getWeightp() {
        return weightp;
    }

    public String getBloodgroupp() {
        return bloodgroupp;
    }
}
