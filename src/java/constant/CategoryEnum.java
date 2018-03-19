/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constant;

import jaxb.Name;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PhuongNT
 */
public class CategoryEnum {
    public static String getRealCategoryName(String value){
        value = value.trim();
        if (value.equalsIgnoreCase("Gaming Keyboards")){
            return Name.BÀN_PHÍM_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Mice") ){
            return Name.CHUỘT_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Headsets") ){
            return Name.TAI_NGHE_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Mouse Pads")){
            return Name.BÀN_DI_CHUỘT_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming Chair/Desks") ){
            return Name.GHẾ_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Gaming controllers")){
            return Name.TAY_CẦM_CHƠI_GAME.value();
        } else if (value.equalsIgnoreCase("Phụ kiện Gaming Gear")){
            return Name.PHỤ_KIỆN_GEAR_GAMING.value();
        } else if(value.equalsIgnoreCase("Máy Game Console")){
            return Name.MÁY_GAME_CONSOLE.value();
        }
        return value;
    }
    
//    private static Map<String, String> categoriesMap;
//    static{
//        categoriesMap = new HashMap<String, String>();
//        categoriesMap.put(Name.BÀN_PHÍM_CHƠI_GAME.value(), "CTGR001");
//        categoriesMap.put(Name.CHUỘT_CHƠI_GAME.value(), "CTGR002");
//        categoriesMap.put(Name.TAI_NGHE_CHƠI_GAME.value(), "CTGR003");
//        categoriesMap.put(Name.BÀN_DI_CHUỘT_CHƠI_GAME.value(), "CTGR004");
//        categoriesMap.put(Name.GHẾ_CHƠI_GAME.value(), "CTGR005");
//        categoriesMap.put(Name.TAY_CẦM_CHƠI_GAME.value(), "CTGR006");
//        categoriesMap.put(Name.PHỤ_KIỆN_GEAR_GAMING.value(), "CTGR007");
//        categoriesMap.put(Name.MÁY_GAME_CONSOLE.value(), "CTGR008");
//        categoriesMap.put(Name.LOA_GAMING.value(), "CTGR009");
//    }
//    
//    public static String getCategoryID(String name){
//        return categoriesMap.get(name);
//    }
}
