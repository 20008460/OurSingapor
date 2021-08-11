package sg.edu.rp.c346.id20008460.oursingapor;

import java.io.Serializable;

public class Island implements Serializable {

    private int _id;
    private String name;
    private String des;
    private int km;
    private float stars;

    public Island(int _id, String name, String des, int km, float stars) {
        this._id = _id;
        this.name = name;
        this.des = des;
        this.km = km;
        this.stars = stars;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        String strStar = " ";
        this.stars = stars;

        if (stars == 1){
            strStar = "*";
        } else if (stars == 2) {
            strStar = "* *";
        } else if (stars == 3) {
            strStar = "* * *";
        } else if (stars == 4) {
            strStar = "* * * *";
        } else if (stars == 5 ) {
            strStar = "* * * * *";
        }
        return  strStar;
    }
}
