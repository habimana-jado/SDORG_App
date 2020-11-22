
package common;

import dao.MovementDao;
import dao.UserDao;
import domain.EMovementStatus;
import domain.EStatus;
import domain.EUserType;
import domain.Movement;
import domain.User;
import java.text.SimpleDateFormat;
import model.AdminModel;

public class Test {
    private int counter1 = 0;
    private int counter2 = 0;
    private int counter3 = 0;
    private int counter4 = 0;
    private int counter5 = 0;
    private int counter6 = 0;
    private int counter7 = 0;
    private int counter8 = 0;
    private int counter9 = 0;
    private int counter10 = 0;
    private int counter11 = 0;
    private int counter12 = 0;
    private int counter13 = 0;
    private int counter14 = 0;
    private int counter15 = 0;
    private int counter16 = 0;
    private int counter17 = 0;
    private int counter18 = 0;
    private int counter19 = 0;
    private int counter20 = 0;

    private int counter21 = 0;
    private int counter22 = 0;
    private int counter23 = 0;
    private int counter24 = 0;
    private int counter25 = 0;
    private int counter26 = 0;
    private int counter27 = 0;
    private int counter28 = 0;
    private int counter29 = 0;
    private int counter30 = 0;
    private int counter31 = 0;
    
    public static void main(String[] args) throws Exception {
//        User u = new User();
//        u.setPassword(new PassCode().encrypt("hec"));
//        u.setStatus(EStatus.ACTIVE);
//        u.setUserType(EUserType.SUPERADMIN);
//        u.setUsername("hec");
//        new UserDao().register(u);

        new Test().setCounterValues("2020", "11");
    }
    public void setCounterValues(String year, String month) {
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");

        for (Movement m : new MovementDao().findAll(Movement.class)) {
            if (sdfYear.format(m.getEntranceTime()).matches(year) && sdfMonth.format(m.getEntranceTime()).matches(month)) {
                if (sdfDay.format(m.getEntranceTime()).matches("01")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter1++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("02")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter2++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("03")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter3++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("04")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter4++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("05")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter5++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("06")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter6++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("07")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter7++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("08")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter8++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("09")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter9++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("10")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter10++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("11")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter11++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("12")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter12++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("13")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter13++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("14")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter14++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("15")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter15++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("16")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter16++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("17")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter17++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("18")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter18++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("19")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter19++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("20")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter20++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("21")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter21++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("22")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter22++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("23")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter23++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("24")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter24++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("25")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter25++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("26")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter26++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("27")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter27++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("28")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter28++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("29")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter29++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("30")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter30++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("31")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter31++;
                }
            }
        }
    }

}
