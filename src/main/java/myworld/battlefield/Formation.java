package myworld.battlefield;

public enum Formation {
    CRANEWING("鹤翼阵"),WILDGOOSE("雁形阵"),YOKE("衡轭阵"),SNAKE("长蛇阵"),
        FISHSCALE("鱼鳞阵"), SQUAREARRAY("方円阵"),MENISCUS("偃月阵"),FRONTAL("锋矢阵");

    private String name;

    private Formation(String str){name=str;}

}
