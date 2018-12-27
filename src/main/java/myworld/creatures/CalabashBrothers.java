package myworld.creatures;

public enum CalabashBrothers {
    /*
    RED("红色", "大娃", 1), ORANGE("橙色", "二娃", 2), YELLOW("黄色", "三娃", 3),
        GREEN("绿色", "四娃", 4), CYAN("青色", "五娃", 5),
        BLUE("蓝色", "六娃", 6), PURPLE("紫色", "七娃", 7);
    public final String color;   //颜色
    public final String rank;    //排行
    public final int index;      //用于排序

    private CalabashBrothers(String str1, String str2, int r) {
        this.color = str1;
        this.rank = str2;
        this.index = r;
    }
    */
    RED("大娃",350,50), ORANGE("二娃",200,60), YELLOW("三娃",400,40),
    GREEN("四娃",300,55), CYAN("五娃",300,55),
    BLUE("六娃",350,45), PURPLE("七娃",100,80);

    public final String name;
    public final int initHp;
    public final int initAtk;

    private CalabashBrothers(String name,int initHp,int initAtk){
        this.name=name;
        this.initHp=initHp;
        this.initAtk=initAtk;
    }
}
