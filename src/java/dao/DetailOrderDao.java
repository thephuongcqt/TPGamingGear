/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.TblDetailOrder;
import entities.TblDetailOrderPK;

/**
 *
 * @author PhuongNT
 */
public class DetailOrderDao extends BaseDao<TblDetailOrder, TblDetailOrderPK>{
    private DetailOrderDao() {
    }

    private static DetailOrderDao instance;
    private final static Object LOCK = new Object();

    public static DetailOrderDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new DetailOrderDao();
            }
        }
        return instance;
    }
}
