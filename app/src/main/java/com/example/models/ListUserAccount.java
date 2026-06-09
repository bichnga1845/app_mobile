package com.example.models;

import java.util.ArrayList;

public class ListUserAccount {
    public static ArrayList<UserAccount>ListUserAccounts()
    {
        ArrayList<UserAccount> list = new ArrayList<>();
        list.add(new UserAccount("admin", "123", "ADMIN", "Trần Thụ Lung Linh", true));
        list.add(new UserAccount("user1", "123", "USER", "Nguyễn Thị tèo", true));
        list.add(new UserAccount("user2", "123", "TECHNICAL", "Nguyễn Thị tý", true));
        return list;

    }
    public static UserAccount login(String username, String password)
    {
        UserAccount uc=null;
        //get sample data
        ArrayList<UserAccount> list=ListUserAccounts();
        //check for login
        for (UserAccount u:list)
        {
            if (u.getUsername().equals(username)&&
            u.getPassword().equals(password))
            {
                uc=u;
                break;
            }
        }
        return uc;
    }
}
