/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import java.util.ArrayList;

/**
 *
 * @author Iahhel
 */
public interface Storage<T> {
    boolean add(T item);
    T get(String id);
    boolean update(T item);
     public ArrayList<T> getArray();
}
