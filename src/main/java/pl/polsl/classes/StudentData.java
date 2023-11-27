package pl.polsl.classes;

/**
 *
 * @author nimo
 */

public class StudentData {
    
    private Boolean valid = false;
    
    private String school;
    private String sex;
    private int age;
    private String address;
    private String famsize;
    private String Pstatus;
    private int Medu;
    private int Fedu;
    private String Mjob;
    private String Fjob;
    private String reason;
    private String guardian;
    private int traveltime;
    private int studytime;
    private int failures;
    private String schoolsup;
    private String famsup;
    private String paid;
    private String activities;
    private String nursery;
    private String higher;
    private String internet;
    private String romantic;
    private int famrel;
    private int freetime;
    private int goout;
    private int Dalc;
    private int Walc;
    private int health;
    private int absences;
    private int G1;
    private int G2;
    private int G3;

    public StudentData() { this.valid = false; }
    
    public StudentData(String[] values)
    {
        if (values.length != 33) {
            throw new IllegalArgumentException("Invalid data format.");
        }
        
        school = values[0];
        sex = values[1];
        age = Integer.parseInt(values[2]);
        address = values[3];
        famsize = values[4];
        Pstatus = values[5];
        Medu = Integer.parseInt(values[6]);
        Fedu = Integer.parseInt(values[7]);
        Mjob = values[8];
        Fjob = values[9];
        reason = values[10];
        guardian = values[11];
        traveltime = Integer.parseInt(values[12]);
        studytime = Integer.parseInt(values[13]);
        failures = Integer.parseInt(values[14]);
        schoolsup = values[15];
        famsup = values[16];
        paid = values[17];
        activities = values[18];
        nursery = values[19];
        higher = values[20];
        internet = values[21];
        romantic = values[22];
        famrel = Integer.parseInt(values[23]);
        freetime = Integer.parseInt(values[24]);
        goout = Integer.parseInt(values[25]);
        Dalc = Integer.parseInt(values[26]);
        Walc = Integer.parseInt(values[27]);
        health = Integer.parseInt(values[28]);
        absences = Integer.parseInt(values[29]);
        G1 = Integer.parseInt(values[30]);
        G2 = Integer.parseInt(values[31]);
        G3 = Integer.parseInt(values[32]);
        
        validate();
    }

public StudentData(String school, String sex, int age, String address, String famsize, String Pstatus,
                   int Medu, int Fedu, String Mjob, String Fjob, String reason, String guardian,
                   int traveltime, int studytime, int failures, String schoolsup, String famsup,
                   String paid, String activities, String nursery, String higher, String internet,
                   String romantic, int famrel, int freetime, int goout, int Dalc, int Walc,
                   int health, int absences, int G1, int G2, int G3) {
    this.school = school;
    this.sex = sex;
    this.age = age;
    this.address = address;
    this.famsize = famsize;
    this.Pstatus = Pstatus;
    this.Medu = Medu;
    this.Fedu = Fedu;
    this.Mjob = Mjob;
    this.Fjob = Fjob;
    this.reason = reason;
    this.guardian = guardian;
    this.traveltime = traveltime;
    this.studytime = studytime;
    this.failures = failures;
    this.schoolsup = schoolsup;
    this.famsup = famsup;
    this.paid = paid;
    this.activities = activities;
    this.nursery = nursery;
    this.higher = higher;
    this.internet = internet;
    this.romantic = romantic;
    this.famrel = famrel;
    this.freetime = freetime;
    this.goout = goout;
    this.Dalc = Dalc;
    this.Walc = Walc;
    this.health = health;
    this.absences = absences;
    this.G1 = G1;
    this.G2 = G2;
    this.G3 = G3;

    validate();
}

    
    public String getSchool()               { return this.school; }
    public void setSchool(String school)    { this.school = school; }
    
    public String getSex()                  { return this.sex; }
    public void setSex(String sex)          { this.sex = sex; }
    
    public Boolean getValid()               { return this.valid; }
    
    public Integer getDalc()               { return this.Dalc; }
    public void setDalc(Integer  dalc)    { this.Dalc = dalc; }
    
    public Integer getWalc()               { return this.Walc; }
    public void setWalc(Integer  walc)    { this.Walc = walc; }
    
    public Integer getFamrel()               { return this.famrel; }
    public void setFamrel(Integer  famrel)    { this.famrel = famrel; }
    
    public Integer getAge()               { return this.age; }
    public void setAge(Integer  age)    { this.age = age; }
    
    public void validate()
    {
    valid = school != null && !school.isEmpty() && 
        sex != null && (sex.equals("M") || sex.equals("F")) && 
        age >= 0 && age <= 100 && 
        G1 >= 0 && G1 <= 20 && 
        G2 >= 0 && G2 <= 20 && 
        G3 >= 0 && G3 <= 20 ;
    }
}
