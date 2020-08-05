package com.example.meditracker;
                                                // this is the class containing the datamembers and memberfunctions..
public class medrecords {
    private String ans1d;
    private String ans2d;
    private String ans3d;
    private String ans4d;
    private int ans5d;
    private int ans6d;
    public medrecords(String ans1, String ans2, String ans3, String ans4, int ans5, int ans6) {
        ans1d = ans1;
        ans2d = ans2;
        ans3d = ans3;
        ans4d = ans4;
        ans5d = ans5;
        ans6d = ans6;
    }

    public String getAns1d() {
        return ans1d;
    }

    public String getAns2d() {
        return ans2d;
    }

    public String getAns3d() {
        return ans3d;
    }
    public String getAns4d() {
        return ans4d;
    }

    public int getAns5d() {
        return ans5d;
    }

    public int getAns6d() {
        return ans6d;
    }
}
