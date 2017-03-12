package util;

import model.User;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by mac on 2017. 3. 12..
 */
public class MatchModel {
    public static User matchModel(Map<String, String> mapUser){
        Set key = mapUser.keySet();
        String userId="", password="", name="", email="";

        for(Iterator iterator = key.iterator(); iterator.hasNext();){
            String keyName = (String) iterator.next();
            String valueName = (String) mapUser.get(keyName);
            if(keyName.equals("userId"))
                userId = valueName;
            else if(keyName.equals("password"))
                password = valueName;
            else if(keyName.equals("name"))
                name = valueName;
            else if(keyName.equals("email"))
                email = valueName;
        }
        return new User(userId, password, name, email);
    }
}
