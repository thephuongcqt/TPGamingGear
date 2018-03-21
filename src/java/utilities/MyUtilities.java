/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author PhuongNT
 */
public class MyUtilities {
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
    
    public static String getStringDate(Date date){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(date);
    }
}
