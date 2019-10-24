package com.mander.mahfuz.bloodbank;

public class user
{
    String firstname;
    String lastname;
    String cell;
    String e_mail;
    String pass_word;
    String blood_group;
    String location;

    public user()
    {
        //just cuz
    }
    public user(user u)
    {
        this.equals(u);
    }



    public void setFirstname(String name)
    {
        firstname = name;
    }
    public void setLastname(String name)
    {
        lastname = name;
    }
    public void setCell(String name)
    {
        cell = name;
    }
    public void setE_mail(String name)
    {
        e_mail = name;
    }
    public void setPass_word(String name)
    {
        pass_word = name;
    }
    public void setBlood_group(String name)
    {
        blood_group = name;
    }
    public void setLocation(String name)
    {
        location = name;
    }

    public String getfname()
    {
        return firstname;
    }
    public String getlname()
    {
        return lastname;
    }
    public String getcell()
    {
        return cell;
    }
    public String getemail()
    {
        return e_mail;
    }
    public String getpassword()
    {
        return pass_word;
    }
    public String getbloodgroup()
    {
        return blood_group;
    }
}
