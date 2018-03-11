/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.UUID;

/**
 *
 * @author PhuongNT
 */
public class MyUtilities {
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
